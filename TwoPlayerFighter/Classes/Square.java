package TwoPlayerFighter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.CollisionOptions;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

public class Square extends Player {

    public Square(JGame game){
        super(game);

        model.Size = new Vector2(50, 50);
        model.AnchorPoint = new Vector2(50, 50);
        model.CFrame.Position.X = 400;
        model.SetImagePath(Constants.SQUARE_IMAGE_PATH);
        model.Anchored = false;
        model.Associate = this;
        model.Solid = true;
        model.WeightPercentage = 1.3;
        game.addInstance(model);
    }

    @Override
    public void ability1() {
        Box2D hitbox = new Box2D();
        hitbox.Size = model.Size.clone().add(0,-10);
        hitbox.CFrame.Position = model.GetCornerPosition(0).add(0, -hitbox.Size.X+10);
        hitbox.FillColor = new Color(255, 0, 0, 30);
        game.addInstance(hitbox);
        game.Services.DebrisService.AddItem(hitbox, .5);

        Instance hit = game.Services.CollisionService.CheckCollisionInBox(hitbox.CFrame.Position, hitbox.Size, 
        new CollisionOptions(new Instance[] {model}, true));
        if (hit == null) return;
        
        if (!(hit.Associate instanceof Player)) return;

        Player hitPlr = (Player) hit.Associate;
        hitPlr.TakeDamage(Constants.PLAYER_1_AB1_DAMAGE);
        hitPlr.stunned = true;
        hitPlr.model.Velocity = Constants.PLAYER_1_AB1_KNOCKBACK.clone().multiply(Math.random()>.5 ? -1 : 1, 1);
        task.spawn(()->{
            game.Services.TimeService.waitTicks(Constants.PLAYER_1_AB1_STUN_FRAMES);
            hitPlr.stunned = false;
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
