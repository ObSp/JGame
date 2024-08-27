package TopdownShooter;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import TopdownShooter.Classes.*;

public class Main {
    static JGame game = new JGame(new StartArgs(true));
    static InputService input = game.Services.InputService;

    static Player player = new Player(game);
    static Image2D plrModel = player.model;

    //layers
    static int BackgroundLayer = -1;
    static int PlayerLayer = 0;
    static int UILayer = 1;

    static EnemyHandler enemyHandler;


    //healthbar
    static Healthbar healthbar = new Healthbar(game, UILayer);


    //enemy stuff
    static double enemySpawnSeconds = 4;

    public static void main(String[] args) {
        game.BackgroundColor = new Color(50, 58, 79);

        enemyHandler = new EnemyHandler(game, player);

        game.OnTick.Connect(dt->{
            player.OffsetByInput(dt);
            player.UpdateGunLocation();

            healthbar.UpdateSize(player.Humanoid.GetHealth(), player.Humanoid.GetMaxHealth());
        });

        input.OnMouseClick.Connect(()->{
            player.Shoot();
        });

        while (true) {
            game.waitSeconds(enemySpawnSeconds);
            enemyHandler.NewEnemy();
            if (enemySpawnSeconds > .5){
                enemySpawnSeconds -= .1;
            }
        }
    }
}
