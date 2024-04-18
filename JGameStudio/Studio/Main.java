package Studio;


import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;
import Classes.*;

import java.awt.Color;


public class Main {
    static Display controlDisplay = new Display("Control");

    static JGame game = new JGame();
    public static void main(String[] args) {
        Promise.await(game.start());
        
        controlDisplay.init(300,500);

        controlDisplay.NewBox.Connect((___,__)->{
            Box2D test = new Box2D();
            test.Size = new Vector2(300,300);
            test.Position = new Vector2(0, 0);
            test.FillColor = Color.red;
            test.Anchored = false;
            game.addInstance(test);
        });
    }
}