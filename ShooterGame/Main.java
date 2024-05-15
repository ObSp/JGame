package ShooterGame;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
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
        player.Rotation = Math.toRadians(45);
        player.FillColor = Color.red;
        game.addInstance(player);
    }
}
