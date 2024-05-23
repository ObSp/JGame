package MarshmallowFighter.Classes;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class ShopInteractible extends Interactible {
    

    public ShopInteractible(JGame game){
        super(game, Constants.SHOP_INTERACTIBLE_PATH);

        this.InteractionKey = KeyEvent.VK_F;

        model = new Image2D();

        prompt = new Image2D();
        prompt.AnchorPoint = new Vector2(50);
        prompt.Size = new Vector2(100);
        prompt.addTag("Prompt");
        prompt.SetImagePath(path);
        prompt.CFrame = model.CFrame;
        prompt.ZIndex = 2;
    }

    public void onInteract(){

    }

    public void PlayerEnteredBounds(){
        game.addInstance(prompt);
    }

    public void PlayerExitedBounds(){
        game.removeInstance(prompt);
    }
}
