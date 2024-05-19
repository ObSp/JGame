package MarshmallowFighter.Classes;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Vector2;

public class BasicMarshmallow extends Entity {

    public static final int MarshmallowSize = 100;
    private SpriteSheet idleSprites = Constants.BasicMarshmallowIdle;

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
        this.PlayAnimation(Constants.BasicMarshmallowHit);

        this.Humanoid.TakeDamage(Constants.BASIC_MARSHMALLOW_KNIFE_ATTACK_DAMAGE);
    }


    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            if (playingAnimation || game.TickCount%Constants.BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS!=0 || isHurt) return;

            model.ImagePath = idleSprites.Sprites[idleSprites.AdvanceSpritePosition()];
            model.UpdateImagePath();
        });
    }
}
