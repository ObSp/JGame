package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Types.Vector2;

public class Player {
    private JGame game;

    public Image2D model = new Image2D();

    public double MaxHealth = 200;
    public double Health = MaxHealth;

    public Player(JGame game){
        this.game = game;

        model.FillColor = Color.black;
        model.AnchorPoint = new Vector2(50);
        model.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        model.Size = new Vector2(50);
        model.ImagePath = "TopdownShooter\\Media\\Player.png";
        game.addInstance(model);
    }
}
