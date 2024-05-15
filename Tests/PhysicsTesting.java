package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.RaycastResult;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {


        Box2D other = new Box2D();
        other.Name = "H";
        other.CFrame.Position = new Vector2(500);
        other.FillColor = Color.black;
        other.AnchorPoint = new Vector2(0);
        other.Size = new Vector2(200);
        game.addInstance(other);

        RaycastResult result = game.Services.ShapecastService.RectangleCast(new Vector2(0), new Vector2(700), null, null);

        if (result == null) return;

        System.out.println(result.HitInstance);

        Box2D visualizer = new Box2D();
        visualizer.FillColor = Color.green;
        visualizer.CFrame.Position = result.FinalPosition;
        visualizer.AnchorPoint = new Vector2(50);
        game.addInstance(visualizer);
        
    }
}
