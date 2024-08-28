package Tests;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.Vector2;

public class CircleDraw {
    static JGame game = new JGame();

    //x^2 + y^2 = 1^2 
    //x^2 + y^2 = 1
    //y^2 = 1 - x^2
    //y = root(1-x^2)
    static double getY(double x){
        return Math.sqrt(1-x*x);
    }

    static double floorToTen(double val) {
        return Math.floor(val/10)*10;
    }

    public static void main(String[] args) {
        for (double x = -1; x <= 1; x += .1) {
            Box2D point = new Box2D();
            point.FillColor = Color.black;
            point.Size = new Vector2(10);
            point.CFrame.Position = new Vector2(500 + x*100, floorToTen(500 + getY(x)*100));
            game.addInstance(point);

            Box2D clone = point.clone();
            
            game.addInstance(clone);
        }
    }
}
