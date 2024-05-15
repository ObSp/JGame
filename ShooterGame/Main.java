package ShooterGame;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

public class Main {
    static JGame game = new JGame();

    static Box2D player;
    static Image2D box;
    public static void main(String[] args) {
        playerInit();
    }

    static void firstSceneInit(){
        
    }

    static void playerInit(){

        player = new Box2D();
        player.Position = new Vector2(1000,500);
        player.FillColor = Color.red;
        game.addInstance(player);

        game.OnTick.Connect(dt->{
            Vector2 mousePos = game.Services.InputService.GetMouseLocation();
            double xDiff = mousePos.X - player.Position.X;
            if (xDiff == 0) return;

            double yDiff = mousePos.Y - player.Position.Y;
            player.Rotation = Math.atan(yDiff/xDiff);
        });
    }
}
