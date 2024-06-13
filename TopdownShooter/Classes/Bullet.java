package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.JGame.Types.Vector2Double;
import JGamePackage.lib.Signal.Connection;

@SuppressWarnings("rawtypes")
public class Bullet {
    static double maxDistance = 1000;


    Box2D model = new Box2D();
    private Connection tickConnection;
    private JGame game;

    public Vector2Double dir;

    public Bullet(JGame game, Vector2 size, CFrame cframe){
        model.Size = size;
        model.CFrame = cframe;
        model.FillColor = new Color(245, 191, 66);

        this.game = game;
    }

    public void Fire(Vector2 target, double Velocity){
        game.addInstance(model);
        dir = target.subtract(model.CFrame.Position).Normalized().multiply(Velocity);

        double origMag = target.Magnitude();

        Vector2Double pos = model.CFrame.Position.ToVector2Double();

        tickConnection = game.OnTick.Connect(dt->{
            pos.X += dir.X;
            pos.Y += dir.Y;
            model.CFrame.Position.X = (int) pos.X;
            model.CFrame.Position.Y = (int) pos.Y;
            if (Math.abs(pos.Magnitude()-origMag) > maxDistance){
                tickConnection.Disconnect();
                game.removeInstance(model);
            }
        });
    }

    private double abs(double a){
        return a >= 0 ? a : -a;
    }
}
