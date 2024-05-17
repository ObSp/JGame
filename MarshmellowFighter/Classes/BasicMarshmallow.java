package MarshmellowFighter.Classes;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Vector2;

public class BasicMarshmallow extends Entity {

    public static final int MarshmallowSize = 100;
    private SpriteSheet idleSprites = Constants.BasicMarshmallowIdle;
    
    public BasicMarshmallow(JGame game){
        super(game);

        model = new Image2D();
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


    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            if (playingAnimation || game.TickCount%Constants.BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS!=0) return;

            model.ImagePath = idleSprites.Sprites[idleSprites.AdvanceSpritePosition()];
            model.UpdateImagePath();

        });
    }
}
