package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.Signal.Connection;

@SuppressWarnings("rawtypes")
public class Bullet {
    static double maxDistance = 1000;


    Box2D model = new Box2D();
    private Connection tickConnection;
    private JGame game;

    public Vector2 dir;

    public Bullet(JGame game, Vector2 size, CFrame cframe){

        model.Size = size;
        model.CFrame = cframe;
        model.FillColor = new Color(255, 255, 255);

        this.game = game;
    }

    public void Fire(Vector2 target, double Velocity){
        game.addInstance(model);
        dir = target.subtract(model.CFrame.Position).Normalized().multiply(Velocity);

        double origMag = target.Magnitude();

        tickConnection = game.OnTick.Connect(dt->{
            model.CFrame.Position.X += dir.X;
            model.CFrame.Position.Y += dir.Y;

            //collision

            if (Math.abs(model.CFrame.Position.Magnitude()-origMag) > maxDistance){
                tickConnection.Disconnect();
                game.removeInstance(model);
            }
        });
    }
}
