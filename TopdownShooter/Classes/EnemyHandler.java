package TopdownShooter.Classes;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.CollisionOptions;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.Signal.Connection;

public class EnemyHandler {
    private JGame game;
    
    private ArrayList<Enemy> enemies = new ArrayList<>();

    @SuppressWarnings({ "rawtypes", "unused" })
    private Connection MoveCon;

    private Player plr;

    public double PlayerHitCooldown = .3;
    private double lastPlrDamage;

    public EnemyHandler(JGame game, Player player){
        this.game = game;

        lastPlrDamage = game.GetElapsedSeconds();

        game.Globals.put("Enemies", enemies);

        plr = player;
        MoveCon = game.OnTick.Connect(dt->{
            UpdateEnemies(dt);
        });
    }

    private void UpdateEnemies(double dt){
        double elapsed = game.GetElapsedSeconds();


        for (int i = enemies.size()-1; i > -1; i--){
            Enemy e = enemies.get(i);
            if (e.Humanoid.IsDead()){
                game.removeInstance(e.model);
                enemies.remove(i);
                continue;
            }
            e.model.ZIndex = (int) e.model.CFrame.Position.Y;

            Vector2 posToMoveTo = plr.model.GetCenterPosition();



            Vector2 moveDir = e.model.CFrame.Position.subtract(posToMoveTo).Normalized().multiply(e.Velocity);
            e.model.CFrame.Position = e.model.CFrame.Position.add(moveDir.multiply(-1));

            e.TurnToPlayer();

            Instance[] col = game.Services.CollisionService.GetInstancesInBox(
                e.model.CFrame.Position, 
                e.model.Size, 
                new CollisionOptions(new Instance[]{e.model})
            );

            for (Instance x : col){
                if (x.Associate == plr && elapsed-lastPlrDamage > PlayerHitCooldown){
                    lastPlrDamage = elapsed;
                    plr.Humanoid.TakeDamage(15);
                }
            }
        }
    }

    public void NewEnemy(){
        enemies.add(new Enemy(game));
    }

}
