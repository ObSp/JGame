package MarshmallowFighter.Classes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class House extends Model {

    public Interactible enterInteractible;
    private Player player;

    public House(JGame game, Player plr) {
        super(game);

        player = plr;

        model = new Image2D();
        model.CFrame.Position = new Vector2(-492, -301);
        model.Size = new Vector2(450);
        model.Image = Constants.HomeAnimationProgression.ImageBuffer[0];
        game.addInstance(model);
        game.OnTick.Connect(dt->updateAnimationState());

        enterInteractible = new Interactible(game, "MarshmallowFighter\\Media\\Misc\\OpenText.png") {

            @Override
            public void onInteract() {
                Enter();
                game.removeInstance(enterInteractible.prompt);
            }

            @Override
            public void PlayerEnteredBounds() {
                game.addInstance(enterInteractible.prompt);
            }

            @Override
            public void PlayerExitedBounds() {
                game.removeInstance(enterInteractible.prompt);
            }
            
        };
        enterInteractible.prompt = new Image2D();
        enterInteractible.prompt.Size = new Vector2(100);
        enterInteractible.prompt.CFrame.Position = model.CFrame.Position.add(model.Size).add(-250, -100);
        enterInteractible.InteractionKey = KeyEvent.VK_F;
        enterInteractible.prompt.SetImagePath(enterInteractible.path);
        enterInteractible.prompt.ZIndex = 2;
        enterInteractible.InteractionDistanceX = enterInteractible.InteractionDistanceY = 150;

        enterInteractible.model = new Image2D();
        enterInteractible.model.CFrame = enterInteractible.prompt.CFrame;
    }

    private void updateAnimationState(){
        if (game.TickCount%Constants.HomeAnimationTickBuffer != 0) return;

        Constants.HomeAnimationProgression.AdvanceSpritePosition();
        model.Image = Constants.HomeAnimationProgression.ImageBuffer[Constants.HomeAnimationProgression.SpritePosition];
    }

    public void Enter(){
        player.model.CFrame.Position = Constants.HOME_TELEPORT_INSIDE_POSITION.clone();
        game.Camera.Position = player.model.CFrame.Position.add(-1000);
        game.setBackground(new Color(38, 20, 33));
    }
    
}
