package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.Promise;


public class Dev {
    public static void main(String[] args) {
        int SPEED = 1;

        JGame game = new JGame(.005);//cannot be lower, otherwise and out of bounds error will throw???

        Promise.await(game.start());
        
        Box2D y = new Box2D();
        y.Name = "yy";
        y.Position = new Vector2(300,250);
        y.Size = new Vector2(100, 100);
        y.FillColor = Color.black;
        y.setParent(game);

        Box2D wall = new Box2D();
        wall.Name = "wall";
        wall.Position = new Vector2(700, 400);
        wall.Size = new Vector2(100, 100);
        wall.FillColor = Color.red;
        wall.BorderSizePixel = 0;
        wall.setParent(game);

        




        game.onTick((dt)->{
            
            if (game.isKeyDown(KeyEvent.VK_A) && y.canMoveLeft()){
                y.Position.X-= SPEED*(dt*1000);
            }

            if (game.isKeyDown(KeyEvent.VK_D) && y.canMoveRight()){
                y.Position.X+= SPEED*(dt*1000);
            }

            if (game.isKeyDown(KeyEvent.VK_W) && y.canMoveUp()){
                y.Position.Y-= SPEED*(dt*1000);
            }

            if (game.isKeyDown(KeyEvent.VK_S) && y.canMoveDown()){
                y.Position.Y+= SPEED*(dt*1000);
            }
        });


        game.onKeyPress((e)->{
            if (e.getKeyCode() == KeyEvent.VK_Q){
                game.quit();
                System.exit(0);
            }
        });

    }
}