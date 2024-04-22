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

        controlDisplay.Instantiate.Connect((N,A)->{
            Box2D test = new Box2D();
            test.Size = new Vector2(300,300);
            test.Position = new Vector2(0, 0);
            test.FillColor = Color.red;
            game.addInstance(test);

            controlDisplay.setCurrentSelected(test);
        });

        controlDisplay.updateProperties.Connect((instance, props)->{
            instance.Position = (Vector2) props.get("Position");
            instance.Size = (Vector2) props.get("Size");
            instance.FillColor = (Color) props.get("Color");
        });

        controlDisplay.RequestDestroy.Connect((inst, __)->{
            if (inst == null) return;
            inst.Destroy(); 
            controlDisplay.setCurrentSelected(null);
        });

        game.onMouseClick(()->{
            controlDisplay.setCurrentSelected(game.getMouseTarget());
        });
    }
}