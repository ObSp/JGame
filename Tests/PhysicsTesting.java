package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {
        Promise.await(game.start());

        Box2D ground = new Box2D();
        ground.FillColor = new Color(103, 245, 117);
        ground.Size = new Vector2(game.getScreenWidth(), 500);
        ground.Position = new Vector2(0, 850);
        ground.Solid = true;

        Oval2D test = new Oval2D();
        test.FillColor = Color.red;
        test.Position = new Vector2(300, 0);
        test.Solid = true;

        game.addInstance(ground);
        game.addInstance(test);

        task.wait(2);

        game.onTick(dt->{
            test.Velocity.X = (int) (game.getInputHorizontal()*(dt*100));
        });
        test.Anchored = false;

    }
}
