package JGamePackage.JGame.Types;

public class PhysicsOptions {
    //max speed
    public double AirResistance = 9;
    public double GlobalGravity = 5.2;
    public boolean doesMassAffectGravity = false;

    public PhysicsOptions(double airRes, double grav){
        AirResistance = airRes;
        GlobalGravity = grav;
    }

    public PhysicsOptions(){}
}
