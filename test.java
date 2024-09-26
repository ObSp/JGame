import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;

import java.awt.Color;

public class test {
    static JGame game = new JGame();

    public static void main(String[] args) {
        Box2D b = new Box2D();
        b.FillColor = Color.red;
        b.CFrame.Position.UseScale = true;
        b.CFrame.Position.X = .5;
        b.CFrame.Position.Y = .5;

        game.addInstance(b);
    }
}
