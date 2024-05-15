package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {

        Promise.await(game.start());

        Box2D box = new Box2D();
        box.FillColor = Color.red;
        game.addInstance(box);

        task.wait(2);
        double ct = ((double)System.currentTimeMillis())/1000.0;
        box.TweenPosition(new Vector2(300), new TweenInfo(2));
        System.out.println(((double)System.currentTimeMillis())/1000.0 - ct);
    }
}
