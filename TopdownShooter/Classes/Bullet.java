package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.CollisionOptions;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.Signal.Connection;

@SuppressWarnings("rawtypes")
public class Bullet {
    static double maxDistance = 2000;


    Box2D model = new Box2D();
    private Connection tickConnection;
    private JGame game;

    public Vector2 dir;

    public double Damage;

    public Bullet(JGame game, Vector2 size, CFrame cframe, double dmg){

        model.Size = size;
        model.CFrame = cframe;
        model.FillColor = new Color(255, 255, 255);
        Damage = dmg;

        this.game = game;
    }

    public void Fire(Vector2 target, double Velocity){
        game.addInstance(model);
        dir = target.subtract(model.CFrame.Position).Normalized().multiply(Velocity);

        double origMag = model.CFrame.Position.Magnitude();

        Player plr = (Player) game.Globals.get("Player");
        if (plr == null) return;

        tickConnection = game.OnTick.Connect(dt->{
            model.CFrame.Position.X += dir.X;
            model.CFrame.Position.Y += dir.Y;

            //collision
            if (Math.abs(model.CFrame.Position.Magnitude()-origMag) > maxDistance){
                tickConnection.Disconnect();
                game.removeInstance(model);
            }

            Instance[] colliding = game.Services.CollisionService.GetInstancesInBox(
                model.CFrame.Position, 
                model.Size, 
                new CollisionOptions(new Instance[]{model, plr.model}, true)
            );

            if (colliding.length == 0) return;

            Enemy hit = colliding[0].Associate instanceof Enemy ? (Enemy) colliding[0].Associate : null;

            if (hit != null){ //enemy has been hit
                hit.Humanoid.TakeDamage(Damage);
            }

            tickConnection.Disconnect();
            game.removeInstance(model);
        });
    }
}
