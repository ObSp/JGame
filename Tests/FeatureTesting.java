package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.task;

public class FeatureTesting {
    static JGame game = new JGame();
    

    public static void main(String[] args) {
        Box2D b = new Box2D();
        b.CFrame.Position = new Vector2(200);
        b.FillColor = Color.red;
        b.Size = new Vector2(100);
        b.CFrame.Rotation = Math.toRadians(45);
        game.addInstance(b);

        game.OnTick.Connect(dt->{
            b.CFrame.LookAt(game.Services.InputService.GetMouseLocation());
        });
    }
}
