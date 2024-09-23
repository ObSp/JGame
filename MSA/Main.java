package MSA;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;

public class Main {

    static JGame game = new JGame();

    static Box2D paperPlane = new Box2D();

    public static void main(String[] args) {
        //MAP
        ArrayList<Instance> instances = game.Services.ParserService.ParseJSONToInstances(new File("MSA\\Saves\\Map.json"));
        for (Instance v : instances){
            game.addInstance(v);
            if (v.Name.equals("Ground"))
                v.Solid = true;
        }
        game.BackgroundColor = new Color(73, 207, 255);

        paperPlane.FillColor = Color.white;
        paperPlane.Size = new Vector2(40, 15);
        paperPlane.CFrame.Position = new Vector2(1600, 327);
        paperPlane.Solid = true;
        paperPlane.WeightPercentage = .1;
        paperPlane.AnchorPoint = new Vector2(50);
        game.addInstance(paperPlane);

        game.Services.TimeService.waitSeconds(3);

        game.OnTick.Connect(dt->{
            paperPlane.CFrame.LookAt(new Vector2(paperPlane.CFrame.Position.X + paperPlane.Velocity.X, paperPlane.CFrame.Position.Y + paperPlane.Velocity.Y + (game.Services.PhysicsService.PhysicsSettings.GlobalGravity * paperPlane.timeInAir)));
        });

        paperPlane.Velocity = new Vector2(-20, -1);
        paperPlane.Anchored = false;
    }
}