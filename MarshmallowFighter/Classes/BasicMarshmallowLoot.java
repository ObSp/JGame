package MarshmallowFighter.Classes;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Types.Vector2;

public class BasicMarshmallowLoot extends Interactible {

    public boolean PickedUp = false;


    public BasicMarshmallowLoot(JGame game, Vector2 origin) {
        super(game, Constants.BASIC_MARSHMALLOW_LOOT_PATH);

        model = new Image2D();
        model.Size = new Vector2(Constants.BASIC_MARSHMALLOW_LOOT_SIZE);
        model.CFrame.Position = origin;
        model.AnchorPoint = new Vector2(50);
        model.ImagePath = Constants.BASIC_MARSHMALLOW_LOOT_PATH;
        model.UpdateImagePath();
        model.Associate = this;
        game.addInstance(model);

        prompt = new Image2D();
        prompt.AnchorPoint = model.AnchorPoint;
        prompt.CFrame = model.CFrame;
        prompt.SetImagePath(Constants.INTERACTIBLE_E_IMAGE_PATH);
        prompt.Size = model.Size.clone();
        prompt.addTag("Prompt");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onInteract() {
        game.removeInstance(model);
        game.removeInstance(prompt);
        ((ArrayList<Interactible>) game.Globals.get("interactibles")).remove(this);
    }

    @Override
    public void PlayerEnteredBounds() {
        game.addInstance(prompt);
    }

    @Override
    public void PlayerExitedBounds() {
        game.removeInstance(prompt);
    }
    
}
