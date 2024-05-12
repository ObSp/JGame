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
                if (inst.Velocity.Y <= 0){
                    inst.timeInAir += dt;
                }
                
            } else {
                inst.inAir = false;
                inst.timeInAir = 0;
            }

            if (!inst.getCollideDirection().isZero()){
                for (int j = 0; j < instances.length; j++){
                    Instance ji = instances[j];
                    if (ji == inst) continue;
    
                    if (ji.overlaps(inst) && ji.Solid){
                        inst.Position.add(inst.getCollideDirection().multiply(3));
                        break;
                    }
                }
            }

            Vector2 vel = inst.Velocity.clone();
            vel.X *= dt*Parent.tickMult;    
            vel.Y *= dt*Parent.tickMult*-1;
            vel.Y += getPositionShift(inst);//*(dt*Parent.tickMult);

            //                   Right  Left
            int Xdir = vel.X > -1 ? 1 : -1;
            //                    Down  Up
            int Ydir = vel.Y > -1 ? 1 : -1;

            if ((Xdir == -1 && inst.collidingLeft()) || (Xdir == 1 && inst.collidingRight())) vel.X = 0;

            if ((Ydir == -1 && inst.collidingTop()) || (Ydir == 1 && inst.collidingBottom())) vel.Y = 0;

            inst.setPosition(vel.add(inst.Position));

            System.err.println(vel.Y);
        }

    }

    //formula: 2pixels/secondsInAir^2

    private int getPositionShift(Instance inst){
        double grav = inst.timeInAir*PhysicsSettings.GlobalGravity;
        return (int) (Math.clamp(grav, 0, PhysicsSettings.AirResistance));
    }
    
}