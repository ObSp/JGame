package ShooterGame;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.CFrame;
import JGamePackage.JGame.Types.Vector2;

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
        player.CFrame.Position = new Vector2(1000,500);
        player.FillColor = Color.red;
        game.addInstance(player);

        game.OnTick.Connect(dt->{
            player.CFrame.LookAt(game.Services.InputService.GetMouseLocation());
        });
    }
}
