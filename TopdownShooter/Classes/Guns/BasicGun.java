package TopdownShooter.Classes.Guns;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.Vector2;
import TopdownShooter.Classes.Bullet;
import TopdownShooter.Classes.Player;

public class BasicGun extends Gun {

    public BasicGun(JGame game, Player plr){
        super(game, plr);

        BulletDamage = 15;
        BulletVelocity = 11;
        CritBonus = 85;
        CritChance = .09;



        model.FillColor = Color.white;
        model.AnchorPoint = new Vector2(50);
        model.Size = new Vector2(10, 5);
        model.ZIndex = plr.model.ZIndex + 1;
        game.addInstance(model);
    }

    @Override
    public void Shoot(Vector2 destination, double damage) {
        Bullet bullet = new Bullet(game, model.Size.divide(2, 2), model.CFrame.clone(), damage);
        bullet.Fire(destination, BulletVelocity);
    }
    
}
