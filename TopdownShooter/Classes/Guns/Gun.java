package TopdownShooter.Classes.Guns;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.Vector2;
import TopdownShooter.Classes.Player;
import TopdownShooter.Classes.Utils;

public abstract class Gun {
    protected final JGame game;
    protected final Player plr;

    public Box2D model = new Box2D();

    public double BulletVelocity;
    public double BulletDamage;
    public double CritBonus;
    public double CritChance;

    public Gun(JGame game, Player player){
        this.game = game;
        this.plr = player;
    }

    public void SetCFrame(CFrame cf){
        model.CFrame = cf;
    }

    public void SetPosition(Vector2 pos){
        model.CFrame.Position = pos;
    }

    public void SetRotation(double rad){
        model.CFrame.Rotation = rad;
    }

    public void Shoot(){
        this.Shoot(game.Services.InputService.GetMouseLocation());
    }

    public void Shoot(Vector2 destination){
        this.Shoot(destination, this.GetDamageWithCrit());
    }

    public void Shoot(double damage){
        this.Shoot(game.Services.InputService.GetMouseLocation(), damage);
    }

    public abstract void Shoot(Vector2 destination, double damage);

    public void UpdateAndLookAtMouse(){
        Vector2 mouseLoc = game.Services.InputService.GetMouseLocation();

        model.CFrame.Position = plr.model.CFrame.Position.clone().add(7, 5);

        model.CFrame.LookAt(mouseLoc);
    }

    /**Returns {@code this.BulletDamage} if a crit doesn't occur and {@code this.BulletDamage + this.CritBonus} if a crit does occur.
     * 
     * @return
     */
    protected double GetDamageWithCrit(){
        return Utils.booleanFromChance(CritChance) ? this.BulletDamage + this.CritBonus : this.BulletDamage;
    }
}
