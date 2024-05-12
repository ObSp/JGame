package Tests;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.*;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * platformer
 */
public class platformer {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    public static void main(String[] args) {
        Promise.await(game.start());

        Box2D player = new Box2D();
        player.FillColor = Color.red;
        player.Position = new Vector2(game.getScreenWidth()/2-player.Size.X, 0);
        player.Solid = true;
        player.Anchored = false;
        game.addInstance(player);

        Box2D ground = new Box2D();
        ground.FillColor = Color.green;
        ground.Size = new Vector2(2000, 100);
        ground.Position.X = 0;
        ground.Position.Y = 900;
        ground.Solid = true;
        game.addInstance(ground);


        Box2D x = new Box2D();
        x.FillColor = Color.red;
        x.Size = new Vector2(500, 50);
        x.Position = new Vector2(1000, 700);
        x.Solid = true;
        game.addInstance(x);


        game.onTick((dt)->{
            player.Velocity.X = (int)(input.GetInputHorizontal()*(dt*1000));
        });


        input.OnKeyPress(e ->{
            if (e.getKeyCode()!= KeyEvent.VK_SPACE) return;

            player.Velocity.Y = -15;
        });
    }
}