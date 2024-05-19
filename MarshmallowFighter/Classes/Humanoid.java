package MarshmallowFighter.Classes;

public class Humanoid {
    private double Health = 100;

    public Humanoid() {}

    public double GetHealth(){
        return Health;
    }

    public void HealBy(double amount){
        Health += amount;
    }

    public void TakeDamage(double Damage){
        Health -= Damage;
    }
}
