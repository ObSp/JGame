package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

public class BasicMarshmallow extends Entity {

    public static final int MarshmallowSize = 100;
    private SpriteSheet idleSprites;

    private int curHurtPos = 5;

    //whether to display a hurt animation instead of idle
    private boolean isHurt = false;
    
    public BasicMarshmallow(JGame game){
        
        super(game, "BasicMarshmallow", Constants.BasicMarshmallowHit.copy());

        idleSprites = Constants.BasicMarshmallowIdle.copy();

        model = new Image2D();
        model.Associate = this;
        model.Size = new Vector2(MarshmallowSize);
        model.CFrame.Position = new Vector2(0,0);
        model.AnchorPoint = new Vector2(50);
        model.Image = idleSprites.ImageBuffer[0];
        model.Solid = true;
        model.Name = "BasicMarshmallow";
        game.addInstance(model);


        
        hitbox.AnchorPoint = new Vector2(-40,100);
        hitbox.Size = new Vector2(
            (double) model.Size.X * Constants.BASIC_MARSHMALLOW_HITBOX_WIDTH_PERCENT, 
            (double) model.Size.Y * Constants.BASIC_MARSHMALLOW_HITBOX_HEIGHT_PERCENT
        );
        hitbox.Solid = true;

        gameLoop();
    }

    protected void moveRandom(){
        Vector2 pos = model.CFrame.Position;

        //y
        if (Math.random() > .5){
            pos.Y -= 1;
        } else {
            pos.Y += 1;
        }

        //x
        if (Math.random() > .5){
            pos.X += 1;
        } else {
            pos.Y -= 1;
        }
    }

    protected void setAnimationImage(BufferedImage path){
        this.model.Image = path;
    }

    public void onHit(){
        if (isDead) return;
        if (!isHurt) isHurt = true;
        this.PlayAnimation(Constants.BasicMarshmallowHit.copy());

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
            this.PlayAnimation(Constants.BasicMarshmallowDeath.copy(), true).Finished.Once(()->{
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
            //hitbox
            hitbox.CFrame.Position = model.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).add(0, -10);

            //moveRandom();

            //animation
            if (playingAnimation || game.TickCount%Constants.BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS!=0) return;

            if (isHurt && !playingAnimation){
                model.Image = Constants.BasicMarshmallowHurtProgression.ImageBuffer[4-curHurtPos > -1 && 4-curHurtPos < 4 ? 4-curHurtPos : 0];
                return;
            }

            model.Image = idleSprites.ImageBuffer[idleSprites.AdvanceSpritePosition()];
        });
    }
}
