package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Vector2;
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

    public boolean hitBoxSidesSolid = false;

    private boolean stopGameLoop = false;

    public final String type;

    public Humanoid Humanoid = new Humanoid();
    public final VoidSignal Died = new VoidSignal();
    public Box2D hitbox = new Box2D();

    public final SpriteSheet hurtAnim;


    protected final JGame game;

    public Entity(JGame game, String type, SpriteSheet hurt){
        this.game = game;
        this.type = type;
        this.hurtAnim = hurt;

        initAnimTickLoop();
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

     public boolean canMoveUp(){
        Vector2 hitboxPos = hitbox.GetRenderPosition();

        for (Instance inst : game.instances){
            var temp = inst;
            if (inst.Associate instanceof Entity)
                inst = ((Entity)inst.Associate).hitbox != null ? ((Entity)inst.Associate).hitbox : inst;

            if (inst.Associate instanceof Model)
                inst = ((Model)inst.Associate).hitbox != null ? ((Model)inst.Associate).hitbox : inst;
            
            if (!inst.Solid && temp==inst) continue;
            

            
            Vector2 posToCheck = new Vector2(
                hitboxPos.X,
                hitboxPos.Y-Constants.PLAYER_HITBOX_UP_SHIFT
            );

            Vector2 sizeToCheck = new Vector2(
                hitbox.Size.X,
                Constants.PLAYER_HITBOX_UP_SHIFT
            );

            if (inst.overlaps(posToCheck, sizeToCheck))
                return false;
        }
        return true;
    }

    public boolean canMoveDown(){
        Vector2 hitboxPos = hitbox.GetRenderPosition();
        
        for (Instance inst : game.instances){
            var temp = inst;
            if (inst.Associate instanceof Entity)
                inst = ((Entity)inst.Associate).hitbox != null ? ((Entity)inst.Associate).hitbox : inst;

            if (inst.Associate instanceof Model)
                inst = ((Model)inst.Associate).hitbox != null ? ((Model)inst.Associate).hitbox : inst;
            
            if (!inst.Solid && temp==inst) continue;
            
            Vector2 posToCheck = new Vector2(
                hitboxPos.X,
                hitboxPos.Y+Constants.PLAYER_HITBOX_DOWN_SHIFT
            );

            Vector2 sizeToCheck = new Vector2(
                hitbox.Size.X,
                Constants.PLAYER_HITBOX_DOWN_SHIFT
            );

            if (inst.overlaps(posToCheck, sizeToCheck))
                return false;
        }
        return true;
    }

    public boolean canMoveRight(){
        Vector2 hitboxPos = hitbox.GetRenderPosition().add(hitbox.Size.X,0);

        for (Instance inst : game.instances){
            var temp = inst;
            if (inst.Associate instanceof Entity)
                inst = ((Entity)inst.Associate).hitbox != null ? ((Entity)inst.Associate).hitbox : inst;

            if (inst.Associate instanceof Model)
                inst = ((Model)inst.Associate).hitbox != null ? ((Model)inst.Associate).hitbox : inst;
            
            if (!inst.Solid && temp==inst) continue;
            
            Vector2 posToCheck = new Vector2(
                hitboxPos.X+Constants.PLAYER_HITBOX_RIGHT_SHIFT,
                hitboxPos.Y
            );

            Vector2 sizeToCheck = new Vector2(
                Constants.PLAYER_HITBOX_RIGHT_SHIFT,
                hitbox.Size.Y
            );

            if (inst.overlaps(posToCheck, sizeToCheck))
                return false;
        }

        return true;
    }

    public boolean canMoveLeft(){
        Vector2 hitboxPos = hitbox.GetRenderPosition();

        for (Instance inst : game.instances){
            var temp = inst;
            if (inst.Associate instanceof Entity)
                inst = ((Entity)inst.Associate).hitbox != null ? ((Entity)inst.Associate).hitbox : inst;

            if (inst.Associate instanceof Model)
                inst = ((Model)inst.Associate).hitbox != null ? ((Model)inst.Associate).hitbox : inst;
            
            if (!inst.Solid && temp==inst) continue;
            
            Vector2 posToCheck = new Vector2(
                hitboxPos.X-Constants.PLAYER_HITBOX_LEFT_SHIFT,
                hitboxPos.Y
            );

            Vector2 sizeToCheck = new Vector2(
                Constants.PLAYER_HITBOX_LEFT_SHIFT,
                hitbox.Size.Y
            );

            if (inst.overlaps(posToCheck, sizeToCheck))
                return false;
        }

        return true;
    }

    public void translatePosition(int xoffset, int yoffset){
        Vector2 hitboxPos = hitbox.GetRenderPosition();

        int verticalShift = Constants.PLAYER_HITBOX_UP_SHIFT;
        int horizontalShift = Constants.PLAYER_HITBOX_UP_SHIFT;

        Vector2 horizontalCheckSize = new Vector2(Constants.PLAYER_HITBOX_LEFT_SHIFT,hitbox.Size.Y-10);

        Vector2 verticalCheckSize = new Vector2(hitbox.Size.X, Constants.PLAYER_HITBOX_UP_SHIFT);

        boolean moveHorizontal = true;
        boolean moveVertical = true;

        for (Instance inst : game.instances){
            if (!moveHorizontal && !moveVertical) break; //no point running the loop after
            var temp = inst;
            if (inst.Associate instanceof Entity)
                inst = ((Entity)inst.Associate).hitbox != null ? ((Entity)inst.Associate).hitbox : inst;

            if (inst.Associate instanceof Model)
                inst = ((Model)inst.Associate).hitbox != null ? ((Model)inst.Associate).hitbox : inst;
            
            if (!inst.Solid && temp==inst) continue;

            //horizontal

            if (moveHorizontal && xoffset != 0){ // only run this code if we haven't determined we can't move vertically yet and we're trying to move vertically
                Vector2 checkPos = new Vector2(hitboxPos.X, hitboxPos.Y);
                checkPos.X += xoffset < 0 ? -horizontalShift :horizontalShift+hitbox.Size.X;
                if (inst.overlaps(checkPos, horizontalCheckSize))
                    moveHorizontal = false;
                
            }

            if (moveVertical && yoffset != 0){
                Vector2 checkPos = new Vector2(hitboxPos.X,hitboxPos.Y);
                checkPos.Y += yoffset < 0 ? verticalShift+20: -verticalShift-5;

                if (inst.overlaps(checkPos, verticalCheckSize))
                    moveVertical = false;
            }

        }


        if (moveHorizontal) model.CFrame.Position.X += xoffset;
        if (moveVertical) model.CFrame.Position.Y -= yoffset;


    }


    /**Must implement animation behaviour
     * 
     */
    protected abstract void gameLoop();

    protected abstract void setAnimationImage(BufferedImage img);

    public abstract void onHit();
}
