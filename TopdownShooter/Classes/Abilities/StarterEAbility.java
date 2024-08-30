package TopdownShooter.Classes.Abilities;

import JGamePackage.JGame.JGame;
import JGamePackage.lib.task;

public class StarterEAbility extends EAbility {
    
    public StarterEAbility(JGame game){
        super(game);

        this.Enable();
    }

    public void Enable(){}

    @Override
    public void onActivate(){
        double numBullets = 10;
        task.spawn(()->{
            for (int i = 0; i < numBullets; i++){
                player.gun.Shoot(game.Services.InputService.GetMouseLocation(),player.gun.BulletDamage + player.gun.CritBonus);
                game.Services.TimeService.waitSeconds(.1);
            }
        });
    }
}
