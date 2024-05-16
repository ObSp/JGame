package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.task;

public class FeatureTesting {
    static JGame game = new JGame();
    

    static double LERP_SPEED = 0.1;

    private static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }

    public static void main(String[] args) {
        game.setBackground(new Color(38, 30, 61));

        Box2D b = new Box2D();
        b.CFrame.Position = new Vector2(500);
        b.AnchorPoint = new Vector2(100, 50);
        b.FillColor = Color.red;
        b.Size = new Vector2(15);
        game.addInstance(b);

        Box2D obstace = new Box2D();
        obstace.FillColor = Color.black;
        game.addInstance(obstace);

        Camera c = game.Camera;

        game.OnTick.Connect(dt->{
            b.CFrame.Position.X += game.Services.InputService.GetInputHorizontal()*5;
            b.CFrame.Position.Y -= game.Services.InputService.GetInputVertical()*5;
            
            c.Position.X = (int) lerp(c.Position.X, b.CFrame.Position.X, LERP_SPEED);
            c.Position.Y = (int) lerp(c.Position.Y, b.CFrame.Position.Y, LERP_SPEED);
        });
    }
}
