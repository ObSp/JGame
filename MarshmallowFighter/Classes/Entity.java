package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;

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
    protected SpriteSheet curAnimSprites;
    protected Animation curAnim;

    public Humanoid Humanoid = new Humanoid();

    protected final JGame game;

    public Entity(JGame game){
        this.game = game;

        initAnimTickLoop();
        gameLoop();
    }

    protected void advanceAnimationFrame(){
        if (curAnimSprites == null || !playingAnimation || curAnim == null) return;

        if (game.TickCount % curAnimSprites.TickBuffers[curAnimSprites.SpritePosition]!=0) return;

        if (!curAnimSprites.hasNext()){
            curAnim.Finished.Fire();
            curAnim.Finished = null;
            curAnimSprites = null;
            playingAnimation = false;
            curAnim = null;
            return;
        }
        curAnimSprites.AdvanceSpritePosition();
        setAnimationImage(curAnimSprites.Sprites[curAnimSprites.SpritePosition]);
    }


    public Animation PlayAnimation(SpriteSheet sprites){
        curAnimSprites = sprites;
        curAnimSprites.SpritePosition = 0;
        playingAnimation = true;
        curAnim = new Animation();
        return curAnim;
    }

    protected void initAnimTickLoop(){
        game.OnTick.Connect(dt->{
            if (!playingAnimation) return;

            advanceAnimationFrame();
        });
    }


    /**Must implement animation behaviour
     * 
     */
    protected abstract void gameLoop();

    protected abstract void setAnimationImage(String path);

    public abstract void onHit();
}
