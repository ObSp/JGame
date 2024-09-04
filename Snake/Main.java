package Snake;

import java.awt.Color;
import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;

public class Main {
    static JGame game = new JGame();

    static Box2D[][] boxes = new Box2D[22][22];

    static final double boxSize = 50;

    static ArrayList<SnakePart> snake = new ArrayList<>();


    static void createBoxes(){
        for (int x = 0; x < boxes.length; x++){
            for (int y = 0; y < boxes[x].length; y++){
                Box2D b = new Box2D();
                b.Size = new Vector2(boxSize);
                b.CFrame.Position.X = x*(boxSize + 1);
                b.CFrame.Position.Y = y*(boxSize + 1);
                b.FillColor = Color.black;
                boxes[x][y] = b;
                game.addInstance(b);
            }
        }

        for (int i = 0; i < 3; i++){
            Box2D b = new Box2D();
            b.Size = new Vector2(boxSize);
            b.FillColor = Color.red;
            game.addInstance(b);
            snake.add(new SnakePart(0, 0, b));
        }
    }

    static void moveSnake(int stepX, int stepY){

    }

    static void followHead(){
        for (int i = 1; i < snake.size(); i++){
            
        }
    }

    static void moveSnakeTo(int x, int y){
        snake.get(0).box.CFrame.Position = boxes[x][y].CFrame.Position.clone();
    }

    public static void main(String[] args) {
        createBoxes();

        moveSnakeTo(5, 5);




    }
}

class SnakePart{
    public int boxX = 0;
    public int boxY = 0;
    public final Box2D box;

    public SnakePart(int x, int y, Box2D box){
        boxX = x;
        boxX = y;
        this.box = box;
    }


}
