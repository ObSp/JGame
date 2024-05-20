package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Types.Vector2;

public class Shop extends Model {
    
    public Shop(JGame game){
        super(game);

        model = new Image2D();
        model.ImagePath = Constants.SHOP_IMAGE_PATH;
        model.UpdateImagePath();
        model.Size = new Vector2(250);
        model.CFrame.Position = new Vector2(1180 , 353);
        model.Associate = this;
        model.AnchorPoint = new Vector2(50);
        model.Solid = true;
        game.addInstance(model);
    }
}
