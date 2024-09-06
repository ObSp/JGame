package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.PhysicsOptions;
import JGamePackage.JGame.Types.Vector2;

public class PhysicsService extends Service {

    public PhysicsOptions PhysicsSettings = new PhysicsOptions();

    public PhysicsService(JGame parent) {
        super(parent);
    }

    public void runPhysics(double dt){
        Instance[] instances = Parent.getInstances();

        for (int i = 0; i < instances.length; i++){
            Instance inst = instances[i];

            if (inst==null || inst.Anchored) continue;

            if (!inst.collidingBottom()){
                inst.inAir = true;
                inst.timeInAir += dt;
                
            } else {
                inst.inAir = false;
                inst.timeInAir = 0;
            }

            //For x: -1 = right, 1 = left
            //For y: -1 = bottom, 1 = top
            Vector2 colDir = inst.getCollideDirection();

            //making sure inst isnt inside of anything
            if (!colDir.isZero()){
                for (int j = 0; j < instances.length; j++){
                    Instance ji = instances[j];
                    if (ji == inst || ji==null) continue;
    
                    if (ji.overlaps(inst) && ji.Solid){
                        inst.CFrame.Position = inst.CFrame.Position.add(colDir.multiply(5));
                    }
                }
            }

            

            Vector2 vel = inst.Velocity;

            //directions
            int xDir = vel.X > -1 ? 1 : -1;
            //                      up  down
            int yDir = vel.Y > -1 ? -1 : 1;

            double posShift = getPositionShift(inst)*(dt*50);

            if ((xDir == -1 && inst.collidingLeft()) || (xDir==1 && inst.collidingRight())) vel.X = 0;
            if ((yDir == 1 && inst.collidingTop()) || (yDir==-1 && inst.collidingBottom())) {posShift = 0; vel.Y = 0;} 

            //touching ground
            if (!inst.inAir && vel.Y > 0){
                vel.Y = 0;
                posShift = 0;
            }

            vel.Y += posShift;

            inst.CFrame.Position.X += vel.X;
            inst.CFrame.Position.Y += vel.Y;
        }

    }

    //formula: 2pixels/secondsInAir^2

    private double clamp(double m, double min, double max){
        if (m < min) return min;
        if (m > max) return max;
        return m;
    }

    private double getPositionShift(Instance inst){
        double grav = inst.timeInAir*(PhysicsSettings.GlobalGravity*inst.WeightPercentage);
        return clamp(grav, 0, PhysicsSettings.AirResistance);
    }
    
}