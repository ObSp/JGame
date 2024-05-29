package TwoPlayerFighter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

public class Rectangle extends Player {

    public Rectangle(JGame game){
        super(game);

        model.Size = new Vector2(100, 50);
        model.AnchorPoint = new Vector2(50, 50);
        model.CFrame.Position.X = 900;
        model.SetImagePath(Constants.RECTANGLE_IMAGE_PATH);
        model.Anchored = false;
        model.Associate = this;
        model.Solid = true;
        model.WeightPercentage = .5;
        game.addInstance(model);
    }

    @Override
    public void ability1() {
        Box2D platform = new Box2D();
        platform.FillColor = Color.WHITE;
        platform.Size = new Vector2(0, Constants.PLAYER_2_AB1_PLATFORM_SIZE.Y);
        platform.AnchorPoint = new Vector2(50, 0);
        platform.CFrame.Position = model.CFrame.Position.add(0,-Constants.PLAYER_2_AB1_UP_SHIFT);
        platform.Solid = true;
        platform.Name = "Platform";
        game.addInstance(platform);

        task.spawn(()->{
            platform.TweenSize(Constants.PLAYER_2_AB1_PLATFORM_SIZE.clone(), new TweenInfo(5));
        });

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
