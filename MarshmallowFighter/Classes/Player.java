package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.JGame.Types.Enum;

public class Player extends Entity{

    //CONSTANTS AND SETTINGS

    public static final SpriteSheet idleSprites = Constants.PlayerIdleSprites;

    public static final int PLAYER_SIZE = 150;

    public boolean attacking = false;

    public int anim_buffer_ticks = Constants.IDLE_ANIM_BUFFER_TICKS;
    
    public Player(JGame game){
        super(game, "Player", null);

        model = new Image2D();

        model.AnchorPoint = new Vector2(50);
        model.Image = idleSprites.ImageBuffer[0];
        model.Size = new Vector2(PLAYER_SIZE);
        model.Name = "Player";
        game.addInstance(model);

        hitbox.AnchorPoint = new Vector2(-20,100);
        hitbox.Size = new Vector2(
            (double) model.Size.X * Constants.PLAYER_HITBOX_WIDTH_PERCENT, 
            (double) model.Size.Y * Constants.PLAYER_HITBOX_HEIGHT_PERCENT
        );
        //game.addInstance(hitbox);

        gameLoop();

    }

    
    @Override
    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            //hitbox
            hitbox.CFrame.Position = model.GetCornerPosition(Enum.InstanceCornerType.BottomLeft)
                .add(model.FlipHorizontally ? -100 : 0, 0);

            //animation 
            if (game.TickCount%anim_buffer_ticks!=0 || playingAnimation) return;

            model.Image = idleSprites.ImageBuffer[idleSprites.AdvanceSpritePosition()];
        });
    }

    @Override
    public void onHit(){}


    protected void setAnimationImage(BufferedImage path){
        this.model.Image = path;
    }
}
