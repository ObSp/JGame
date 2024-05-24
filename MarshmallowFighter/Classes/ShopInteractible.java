package MarshmallowFighter.Classes;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class ShopInteractible extends Interactible {
    
    @SuppressWarnings("unused")
    private Shop parent;

    public ShopInteractible(JGame game, Shop p){
        super(game, Constants.SHOP_INTERACTIBLE_PATH);
        parent = p;

        this.InteractionKey = KeyEvent.VK_F;
        this.InteractionDistanceX = 100;
        this.InteractionDistanceY = 100;

        model = new Image2D();
        model.Associate = this;

        prompt = new Image2D();
        prompt.AnchorPoint = new Vector2(50);
        prompt.Size = new Vector2(100);
        prompt.addTag("Prompt");
        prompt.SetImagePath(path);
        prompt.CFrame = model.CFrame;
        prompt.ZIndex = 2;

        prompt.CFrame.Position = p.model.CFrame.Position.add(100, -50);
    }

    public void onInteract(){
        game.removeInstance(prompt);
        removeFromGlobals();
    }

    public void PlayerEnteredBounds(){
        game.addInstance(prompt);
    }

    public void PlayerExitedBounds(){
        game.removeInstance(prompt);
    }
}
