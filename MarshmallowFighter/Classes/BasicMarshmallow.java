package MarshmallowFighter.Classes;

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
        model.UpdateImagePath();
        game.addInstance(model);
    }

    protected void setAnimationImage(String path){
        model.ImagePath = path;
        model.UpdateImagePath();
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
            });
        });
    }

    private void advanceHurt(){
        if (curHurtPos <= 0){
            die();
            return;
        }
        //String p = Constants.BasicMarshmallowHurtProgression[Constants.BasicMarshmallowHurtProgression.length-curHurtPos];
        //model.ImagePath = p;
        //model.UpdateImagePath();
    }


    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            if (playingAnimation || game.TickCount%Constants.BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS!=0) return;

            if (isHurt && !playingAnimation){
                model.ImagePath = Constants.BasicMarshmallowHurtProgression[4-curHurtPos > -1 && 4-curHurtPos < 4 ? 4-curHurtPos : 0];
                model.UpdateImagePath();
                return;
            }

            model.ImagePath = idleSprites.Sprites[idleSprites.AdvanceSpritePosition()];
            model.UpdateImagePath();
        });
    }
}
