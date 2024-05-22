package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.lib.Signal;

public class Player extends Entity{

    //CONSTANTS AND SETTINGS

    public static final SpriteSheet idleSprites = Constants.PlayerIdleSprites;

    public static final int PLAYER_SIZE = 150;

    public int MarshmallowLootCount = 0;
    public int MarshmallowsKilled = 0;

    public boolean attacking = false;

    public int anim_buffer_ticks = Constants.IDLE_ANIM_BUFFER_TICKS;

    //interactibles
    private int shownMarshies = 0;

    public Interactible currentlyShownInteractible;

    public Signal<Interactible> interactibleTriggered = new Signal<>();
    
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
        detectInput();

    }

    public Vector2 getPositionIncludingReflectShift(){
        return model.CFrame.Position.add(model.FlipHorizontally ? -100 : 0, 0);
    }


    @SuppressWarnings("unchecked")
    private void checkInteractibles(){
        ArrayList<Interactible> interactibles = (ArrayList<Interactible>) game.Globals.get("interactibles");

        if (interactibles == null) return;

        Vector2 plrPos = getPositionIncludingReflectShift();

        for (Interactible v : interactibles){
            Vector2 modelPos = v.model.CFrame.Position;
            if (Math.abs(plrPos.X-modelPos.X)<= v.InteractionDistanceX && Math.abs(plrPos.Y-modelPos.Y)<=v.InteractionDistanceY){
                if (v.InteractionPromptVisible || shownMarshies>=Constants.INTERACTIBLE_MAX_SHOWN_AT_ONCE) continue;
                v.InteractionPromptVisible = true;
                v.PlayerEnteredBounds();
                shownMarshies++;
                currentlyShownInteractible = v;
            } else if (v.InteractionPromptVisible) {
                v.InteractionPromptVisible = false;
                v.PlayerExitedBounds();
                shownMarshies--;
            }
        }
    }

    protected void detectInput(){
        game.Services.InputService.OnKeyPress.Connect(e->{
            if (currentlyShownInteractible != null && e.getKeyCode() == currentlyShownInteractible.InteractionKey){
                interactibleTriggered.Fire(currentlyShownInteractible);
                shownMarshies--;
            }
        });
    }
    
    @Override
    protected void gameLoop(){
        game.OnTick.Connect(dt->{
            //hitbox
            hitbox.CFrame.Position = model.GetCornerPosition(Enum.InstanceCornerType.BottomLeft)
                .add(model.FlipHorizontally ? -100 : 0, 0);

            //interatible stuff
            checkInteractibles();

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