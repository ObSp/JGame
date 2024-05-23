package Tests;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.*;
import JGamePackage.lib.Signal;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * platformer
 */
@SuppressWarnings("unused")
public class platformer {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    public static void main(String[] args) {

        Box2D player = new Box2D();
        player.FillColor = Color.red;
        player.CFrame.Position = new Vector2(game.getScreenWidth()/2-player.Size.X, 0);
        player.Solid = true;
        player.Anchored = false;
        player.ZIndex = 2;
        game.addInstance(player);

        Box2D ground = new Box2D();
        ground.FillColor = Color.green;
        ground.Size = new Vector2(2000, 100);
        ground.CFrame.Position.X = 0;
        ground.CFrame.Position.Y = 900;
        ground.Solid = true;
        game.addInstance(ground);


        Box2D x = new Box2D();
        x.FillColor = Color.red;
        x.Size = new Vector2(500, 50);
        x.CFrame.Position = new Vector2(1000, 800);
        x.Solid = true;
        game.addInstance(x);


        game.OnTick.Connect((dt)->{
            player.Velocity.X = (int)(input.GetInputHorizontal()*(dt*1000));
        });


        input.OnKeyPress.Connect(e ->{
            if (e.getKeyCode()!= KeyEvent.VK_SPACE) return;

            player.Velocity.Y = -30;
        });
    }
}