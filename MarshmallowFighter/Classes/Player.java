package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

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
        model.ImagePath = idleSprites.Sprites[0];
        model.UpdateImagePath();
        model.Size = new Vector2(PLAYER_SIZE);
        game.addInstance(model);

    }

    
    @Override
    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            if (game.TickCount%anim_buffer_ticks!=0 || playingAnimation) return;

            model.SetBufferedImage(idleSprites.ImageBuffer[idleSprites.AdvanceSpritePosition()]);
        });
    }

    @Override
    public void onHit(){}


    protected void setAnimationImage(BufferedImage path){
        this.model.SetBufferedImage(path);
    }
}