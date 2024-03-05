package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;


public class Dev {
    public static void main(String[] args) {
        JGame game = new JGame(.00001);

        game.start().andThen((arg)->{
            System.out.println("Started");
        });
        
        Paintable y = new Paintable();
        y.Position = new Vector2(50,50);
        y.Size = new Vector2(100, 400);
        y.FillColor = Color.red;
        y.setParent(game);

        game.onTick((dt)->{
            if (game.isKeyDown(KeyEvent.VK_D)){
                y.Position.add(new Vector2(1, 0));
            }

            if (game.isKeyDown(KeyEvent.VK_A)){
                y.Position.subtract(new Vector2(1, 0));
            }
        });

    }
}