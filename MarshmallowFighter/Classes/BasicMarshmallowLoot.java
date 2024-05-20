package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Types.Vector2;

public class BasicMarshmallowLoot extends Interactible {


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
    }
    
}
