package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
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
        model.AnchorPoint = new Vector2(0,100);
        model.Solid = true;
        model.Name = "Shop";
        game.addInstance(model);

        hitBoxSidesSolid = true;


        hitbox = new Box2D();
        hitbox.Size = new Vector2(
            Constants.SHOP_HITBOX_WIDTH_PERCENT*(double) model.Size.X, 
            Constants.SHOP_HITBOX_HEIGHT_PERCENT * (double) model.Size.Y
        );
        hitbox.CFrame.Position = model.CFrame.Position.add(hitbox.Size.X/4, -hitbox.Size.Y);
    }
}
