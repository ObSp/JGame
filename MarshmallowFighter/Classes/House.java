package MarshmallowFighter.Classes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class House extends Model {

    public Interactible enterInteractible;
    public Interactible exitInteractible;
    private Player player;

    Vector2 posB4Tp;
    private Color cB4Tp;

    public House(JGame game, Player plr) {
        super(game);

        player = plr;

        model = new Image2D();
        model.CFrame.Position = new Vector2(-492, -301);
        model.Size = new Vector2(450);
        model.Image = Constants.HomeAnimationProgression.ImageBuffer[0];
        game.addInstance(model);
        game.OnTick.Connect(dt->updateAnimationState());

        enterInteractible = new Interactible(game, "MarshmallowFighter\\Media\\Misc\\EnterText.png") {

            @Override
            public void onInteract() {
                Enter();
                game.removeInstance(enterInteractible.prompt);
                super.onInteract();
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
        enterInteractible.prompt.ZIndex = 3;
        enterInteractible.prompt.addTag("Prompt");
        enterInteractible.InteractionDistanceX = enterInteractible.InteractionDistanceY = 150;

        enterInteractible.model = new Image2D();
        enterInteractible.model.CFrame = enterInteractible.prompt.CFrame;

        exitInteractible = new Interactible(game, "MarshmallowFighter\\Media\\Misc\\ExitText.png") {

            @Override
            public void onInteract() {
                Exit();
                game.removeInstance(exitInteractible.prompt);
                super.onInteract();
            }

            @Override
            public void PlayerEnteredBounds() {
                game.addInstance(exitInteractible.prompt);
            }

            @Override
            public void PlayerExitedBounds() {
                game.removeInstance(exitInteractible.prompt);
            }
            
        };
        exitInteractible.prompt = new Image2D();
        exitInteractible.prompt.Size = new Vector2(100);
        exitInteractible.prompt.CFrame.Position = new Vector2(10000280, 9999765);
        exitInteractible.InteractionKey = KeyEvent.VK_F;
        exitInteractible.prompt.SetImagePath(exitInteractible.path);
        exitInteractible.prompt.ZIndex = 3;
        exitInteractible.prompt.addTag("Prompt");
        exitInteractible.InteractionDistanceX = exitInteractible.InteractionDistanceY = 100;

        exitInteractible.model = new Image2D();
        exitInteractible.model.CFrame = exitInteractible.prompt.CFrame;
    }

    private void updateAnimationState(){
        if (game.TickCount%Constants.HomeAnimationTickBuffer != 0) return;

        Constants.HomeAnimationProgression.AdvanceSpritePosition();
        model.Image = Constants.HomeAnimationProgression.ImageBuffer[Constants.HomeAnimationProgression.SpritePosition];
    }

    public void Enter(){
        cB4Tp = game.BackgroundColor;
        posB4Tp = player.model.CFrame.Position.clone();
        player.model.CFrame.Position = Constants.HOME_TELEPORT_INSIDE_POSITION.clone().add(player.model.FlipHorizontally ? 100 : 0, 0);
        game.Camera.Position = player.model.CFrame.Position.add(-1000);
        game.BackgroundColor = new Color(38, 20, 33);
    }

    public void Exit(){
        player.model.CFrame.Position = Constants.HOME_TELEPORT_EXIT_POSITION.clone();
        game.Camera.Position = player.model.CFrame.Position.add(1000);
        game.BackgroundColor = cB4Tp;
    }
    
}
