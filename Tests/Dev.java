package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.Promise;


public class Dev {
    public static void main(String[] args) {

        JGame game = new JGame(.005);//cannot be lower, otherwise and out of bounds error will throw???

        Promise.await(game.start());
        
        Box2D y = new Box2D();
        y.Name = "yy";
        y.Position = new Vector2(300,50);
        y.Size = new Vector2(100, 100);
        y.FillColor = Color.black;
        y.setParent(game);

        Box2D wall = new Box2D();
        wall.Position = new Vector2(y.Position.X+100, y.Position.Y);
        wall.Size = new Vector2(350, 100);
        wall.FillColor = Color.red;
        wall.BorderSizePixel = 3;
        wall.setParent(game);




        System.out.println(y.collidingLeft());


        game.onKeyPress((e)->{
            if (e.getKeyCode() == KeyEvent.VK_F11){
                game.quit();
                System.exit(0);
            }
        });

    }
}