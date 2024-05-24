package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.Vector2;

public class Rectangle extends Player {

    public Rectangle(JGame game){
        super(game);

        model.Size = new Vector2(100, 50);
        model.AnchorPoint = new Vector2(50, 50);
        model.CFrame.Position.X = 500;
        model.SetImagePath(Constants.RECTANGLE_IMAGE_PATH);
        model.Anchored = false;
        model.Associate = this;
        model.Solid = true;
        model.WeightPercentage = .5;
        game.addInstance(model);
    }

    @Override
    public void ability1() {
    }

    @Override
    public void ability2() {
    }

    @Override
    public void ability3() {
    }

    @Override
    public void ult() {
    }
    
}
