import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.Vector2;

import java.awt.Color;

public class test {
    static JGame game = new JGame();

    public static void main(String[] args) {
        Box2D b = new Box2D();
        b.FillColor = Color.red;
        game.addInstance(b);

        b.Anchored = false;
    }
}
