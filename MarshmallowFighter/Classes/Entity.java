package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.lib.VoidSignal;

/**Represents all moving objects, including the Player, enemies, and other non-static objects
 * 
 */
public abstract class Entity {
    
    /**All subclasses need to initialize this themselves
     * 
     */
    public Image2D model;

    public int state;

    public boolean playingAnimation = false;
    public boolean isDead = false;
    protected SpriteSheet curAnimSprites;
    protected Animation curAnim;

    private boolean stopGameLoop = false;

    public final String type;

    public Humanoid Humanoid = new Humanoid();
    public final VoidSignal Died = new VoidSignal();

    public final SpriteSheet hurtAnim;


    protected final JGame game;

    public Entity(JGame game, String type, SpriteSheet hurt){
        this.game = game;
        this.type = type;
        this.hurtAnim = hurt;

        initAnimTickLoop();
        gameLoop();
    }

    protected void advanceAnimationFrame(){
        if (curAnimSprites == null || !playingAnimation || curAnim == null) return;

        if (game.TickCount % curAnimSprites.TickBuffers[curAnimSprites.SpritePosition]!=0) return;

        //animation done
        if (!curAnimSprites.hasNext()){
            curAnim.Finished.Fire();
            curAnim.Finished = null;
            curAnimSprites = null;
            playingAnimation = false;
            curAnim = null;
            return;
        }
        curAnimSprites.AdvanceSpritePosition();
        setAnimationImage(curAnimSprites.ImageBuffer[curAnimSprites.SpritePosition]);
    }


    public Animation PlayAnimation(SpriteSheet sprites, boolean override){
        if (playingAnimation && !override)
            return null;
        
        curAnimSprites = sprites;
        curAnimSprites.SpritePosition = 0;
        playingAnimation = true;
        curAnim = new Animation();
        return curAnim;
    }

    public Animation PlayAnimation(SpriteSheet sprites){
        return PlayAnimation(sprites, false);
    }

    protected void initAnimTickLoop(){
        game.OnTick.Connect(dt->{
            if (!playingAnimation || stopGameLoop) return;

            advanceAnimationFrame();
        });
    }

    public void Destroy(){
        stopGameLoop = true;
    }


    /**Must implement animation behaviour
     * 
     */
    protected abstract void gameLoop();

    protected abstract void setAnimationImage(BufferedImage img);

    public abstract void onHit();
}
