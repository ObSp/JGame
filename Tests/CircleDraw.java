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

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            Box2D b = new Box2D();
            b.FillColor = Color.black;
            b.Size = new Vector2(25);
            double x = 500 + (i*b.Size.X);
            double y = 500 + getY(x)*b.Size.Y;
            b.CFrame.Position = new Vector2(x,y);
            System.out.println(b.CFrame.Position);
            game.addInstance(b);
        }
    }
}
