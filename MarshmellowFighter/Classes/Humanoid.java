package MarshmellowFighter.Classes;

public class Humanoid {
    public double Health = 100;

    public Humanoid() {}

    public void TakeDamage(double Damage){
        Health -= Damage;
    }
}
