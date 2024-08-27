package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.Vector2;

public class Healthbar {
    @SuppressWarnings("unused")
    private JGame game;

    public Box2D background = new Box2D();
    public Box2D bar = new Box2D();

    public Healthbar(JGame game, int layer){
        this.game = game;

        background.CFrame.Position = new Vector2(20);
        background.Size = new Vector2(200, 30);
        background.MoveWithCamera = false;
        background.FillColor = new Color(22, 22, 23);
        background.ZIndex = layer;
        game.addInstance(background);

        bar = background.clone();
        bar.FillColor = new Color(237, 28, 70);
        bar.ZIndex = background.ZIndex + 1;
        game.addInstance(bar);
        
    }

    public void UpdateSize(double health, double maxHealth){
        bar.Size.X = Utils.lerp(bar.Size.X, (health/maxHealth)*background.Size.X, .1);
    }
}
