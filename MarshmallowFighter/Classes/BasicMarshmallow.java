package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

public class BasicMarshmallow extends Entity {

    public static final int MarshmallowSize = 100;
    private SpriteSheet idleSprites = Constants.BasicMarshmallowIdle;

    private int curHurtPos = 5;

    //whether to display a hurt animation instead of idle
    private boolean isHurt = false;
    
    public BasicMarshmallow(JGame game){
        super(game, "BasicMarshmallow", Constants.BasicMarshmallowHit);

        model = new Image2D();
        model.Associate = this;
        model.Size = new Vector2(MarshmallowSize);
        model.CFrame.Position = new Vector2(0,0);
        model.AnchorPoint = new Vector2(50);
        model.ImagePath = idleSprites.Sprites[0];
        model.Solid = true;
        model.UpdateImagePath();
        game.addInstance(model);
    }

    protected void setAnimationImage(BufferedImage path){
        this.model.SetBufferedImage(path);
    }

    public void onHit(){
        if (isDead) return;
        if (!isHurt) isHurt = true;
        this.PlayAnimation(Constants.BasicMarshmallowHit);

        this.Humanoid.TakeDamage(Constants.BASIC_MARSHMALLOW_KNIFE_ATTACK_DAMAGE);

        int simplifiedHealth = (int) Humanoid.GetHealth()/20;
        if (simplifiedHealth!=curHurtPos){
            curHurtPos--;
            advanceHurt();
        }
    }

    private void die(){
        isDead = true;
        task.spawn(()->{
            game.waitTicks(10);
            this.PlayAnimation(Constants.BasicMarshmallowDeath, true).Finished.Once(()->{
                game.removeInstance(model);
                this.Died.Fire();
                this.Destroy();
                new BasicMarshmallowLoot(game, model.CFrame.Position.clone());
            });
        });
    }

    private void advanceHurt(){
        if (curHurtPos <= 0){
            die();
            return;
        }
    }


    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            if (playingAnimation || game.TickCount%Constants.BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS!=0) return;

            if (isHurt && !playingAnimation){
                model.SetBufferedImage(Constants.BasicMarshmallowHurtProgression.ImageBuffer[4-curHurtPos > -1 && 4-curHurtPos < 4 ? 4-curHurtPos : 0]);
                return;
            }

            model.SetBufferedImage(idleSprites.ImageBuffer[idleSprites.AdvanceSpritePosition()]);
        });
    }
}
