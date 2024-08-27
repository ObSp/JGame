package TopdownShooter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Vector2;

public class Enemy {

    private JGame game;
    private Player plr;
    
    public Image2D model = new Image2D();

    public double Velocity = 2;

    public final Humanoid Humanoid = new Humanoid();

    public DamagePopup DamagePopup;

    public Direction SpawnPoint;
    public int PlayerCornerToMoveTo;

    private void staticConstruct(JGame g){
        game = g;
        
        plr = (Player) game.Globals.get("Player");

        DamagePopup = new DamagePopup(game, model);

        Velocity += Utils.random(-1, 1);

        model.AnchorPoint = new Vector2(50);
        model.ImagePath = "TopdownShooter\\Media\\Enemy0.png";
        model.Size = plr.model.Size.clone();
        model.Associate = this;

        double screenWidth = game.getScreenWidth();
        double screenHeight = game.getScreenHeight();

        boolean fixedHorizontalSide = Utils.randBoolean();

        if (fixedHorizontalSide){ //spawn on either right or left of screen with a random y position
            boolean left = Utils.randBoolean();

            model.CFrame.Position.X = left ? -model.Size.X : screenWidth + model.Size.X;
            model.CFrame.Position.Y = Utils.random(0, screenHeight);

            SpawnPoint = left ? Direction.Left : Direction.Right;
        } else { //spawn on either top or bottom of screen with a random x value
            boolean top = Utils.randBoolean();

            model.CFrame.Position.X = Utils.random(0, screenWidth);
            model.CFrame.Position.Y = top ? -model.Size.Y : screenHeight + model.Size.Y;

            SpawnPoint = top ? Direction.Top : Direction.Bottom;
        }
        
        model.Solid = true;
        game.addInstance(model);

        this.Humanoid.Hurt.Connect((dmg)->{
            onHit(dmg);
        });
    }

    public Enemy(JGame g){
        staticConstruct(g);
    }

    public Enemy(JGame g, double vel){
        staticConstruct(g);
        this.Velocity = vel;
    }

    public void TurnToPlayer(){
        if (model.CFrame.Position.X > plr.model.CFrame.Position.X && !model.FlipHorizontally){
            model.FlipHorizontally = true;
        } else if (model.CFrame.Position.X <= plr.model.CFrame.Position.X && model.FlipHorizontally){
            model.FlipHorizontally = false;
        }
    }

    public void onHit(double damage){
        this.DamagePopup.OnHit(damage, damage > plr.gun.BulletDamage);
    }
}