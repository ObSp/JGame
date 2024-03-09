package Tests;

import java.awt.Color;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;

public class Movement {
    public static void main(String[] args) {
        double speed = 1;

        JGame game = new JGame();

        Box2D player = new Box2D();
        player.Size = new Vector2(100, 100);
        player.Position = new Vector2(300, 300);
        player.FillColor = Color.black;
        player.setParent(game);

        game.start();

        game.onTick((dt)->{
            player.Velocity.X = (int) (game.getInputHorizontal()*(dt*1000)*speed);
        });
    }
}
