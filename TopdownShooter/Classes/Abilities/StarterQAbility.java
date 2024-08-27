package TopdownShooter.Classes.Abilities;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Oval2D;
import JGamePackage.JGame.Types.Tween;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;
import TopdownShooter.Classes.Enemy;

public class StarterQAbility extends QAbility {

    public StarterQAbility(JGame game) {
        super(game);
    }

    @Override
    public void onActivate() {
        Oval2D blast = new Oval2D();
        blast.Size = new Vector2(5);
        blast.CFrame.Position = player.model.CFrame.Position.clone();
        blast.AnchorPoint = new Vector2(50);
        blast.ZIndex = 2;
        game.addInstance(blast);

        Tween blastSize = blast.TweenSizeParallel(new Vector2(500), new TweenInfo(.1));

        @SuppressWarnings("unchecked")
        var enemies = (ArrayList<Enemy>) game.Globals.get("Enemies");

        task.spawn(()->{
            while (!blastSize.Finished) {
                game.waitForTick();

                synchronized (enemies) {
                    for (int i = 0; i < enemies.size(); i++){
                        Enemy e = enemies.get(i);
                        if (e == null) continue;
            
                        Vector2 center = e.model.GetCenterPosition();
            
                        if (blast.isCoordinateInBounds(center)){
                            e.Humanoid.TakeDamage(e.Humanoid.GetHealth());
                        }
                    }
                }
            }

            game.Services.DebrisService.AddItem(blast, .3);
        });
        
    }

    @Override
    public void Enable() {
        
    }
    
}
