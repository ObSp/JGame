package TopdownFighter;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;

public class Main {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    static Image2D player;
    static int playerSize = 90;

    static Camera cam = game.Camera;
    static final double CAM_LERP_SPEED = .05;

    static Box2D obstacle;

    static double dtMult = 500;

    public static void main(String[] args) {
        game.setBackground(new Color(38, 30, 61));

        obstacle = new Box2D();
        obstacle.FillColor = Color.black;
        game.addInstance(obstacle);

        playerSetup();
        gameLoop();
    }

    static void playerSetup(){
        player = new Image2D();
        player.ImagePath = "TopdownFighter\\Media\\PlayerStates\\idle.png";
        player.UpdateImagePath();
        player.CFrame.Position = new Vector2(500);
        player.Size = new Vector2(playerSize);
        player.AnchorPoint = new Vector2(50);
        game.addInstance(player);
    }

    static void gameLoop(){
        game.OnTick.Connect(dt->{
            player.CFrame.Position.X += input.GetInputHorizontal()*5;
            player.CFrame.Position.Y -= input.GetInputVertical()*5;

            cam.Position.X = (int) Util.lerp(cam.Position.X, player.CFrame.Position.X, CAM_LERP_SPEED);
            cam.Position.Y = (int) Util.lerp(cam.Position.Y, player.CFrame.Position.Y, CAM_LERP_SPEED);
        });
    }
}

class Util{
    public static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
}