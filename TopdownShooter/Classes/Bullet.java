package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.Signal.Connection;

@SuppressWarnings("rawtypes")
public class Bullet {
    Box2D model = new Box2D();
    private Connection tickConnection;
    private JGame game;

    public Bullet(JGame game, Vector2 size, CFrame cframe){
        model.Size = size;
        model.CFrame = cframe;
        model.FillColor = new Color(245, 191, 66);

        this.game = game;
    }

    public void Fire(Vector2 target){
        tickConnection = game.OnTick.Connect(dt->{

        });
    }

    private static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
}
