package TopdownShooter.Classes;

import JGamePackage.lib.Signal;
import JGamePackage.lib.VoidSignal;

public class Humanoid {
    private double MaxHealth = 100;
    private double Health = MaxHealth;

    public final VoidSignal Died = new VoidSignal();
    public final Signal<Double> Hurt = new Signal<>();

    public Humanoid(){}

    public Humanoid(double max){
        MaxHealth = max;
        Health = MaxHealth;
    }

    public double GetHealth(){
        return Health;
    }

    public void TakeDamage(double dmg){
        if (IsDead()) return;
        double oldHealth = Health;
        Health -= dmg;
        Hurt.Fire(Math.abs(Health-oldHealth));
        CheckForDeath();
        ClampHealth();
    }

    public void Heal(double heal){
        Health += heal;
        ClampHealth();
    }

    public double GetMaxHealth(){
        return MaxHealth;
    }

    public void SetMaxHealth(double num){
        MaxHealth = num;
    }

    public boolean IsDead(){
        return Health <= 0;
    }

    private void ClampHealth(){
        Health = Utils.clamp(Health, 0, MaxHealth);
    }

    private void CheckForDeath(){
        if (IsDead()){
            Died.Fire();
        }
    }
}
