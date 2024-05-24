import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.Vector2;

public class Creator {

    static JGame game = new JGame();

    static Vector2 firstClick = null;
    static Vector2 secondClick = null;

    public static void main(String[] args) {
        game.Services.InputService.OnMouseClick.Connect(()->{
            if (firstClick != null && secondClick != null){
                firstClick = null;
                secondClick = null;
            }

            if (firstClick == null){
                firstClick = game.Services.InputService.GetMouseLocation();
                return;
            }

            secondClick = game.Services.InputService.GetMouseLocation();
            Box2D b = new Box2D();
            b.CFrame.Position = firstClick;
            b.Size.X = secondClick.X-firstClick.X;
            b.Size.Y = secondClick.Y-firstClick.Y;
            b.FillColor = Color.red;
            game.addInstance(b);
        });
    }
}