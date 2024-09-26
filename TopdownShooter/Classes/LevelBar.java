package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.Vector2;

public class LevelBar {
    private JGame game;

    public int XP = 0;
    public int CurMaxXP = 100;

    private Box2D background = new Box2D();
    private Box2D fill = new Box2D();

    public LevelBar(JGame game, int layer){
        background.CFrame.Position = new Vector2(.5, .01);
        background.CFrame.Position.UseScale = true;
        background.AnchorPoint = new Vector2(50, 0);
        background.FillColor = new Color(-12500928);
        background.Size = new Vector2(.1, .1);
        background.Size.UseScale = true;
        game.addInstance(background);

        fill.CFrame.Position = new Vector2(.5, .01);
        fill.CFrame.Position.UseScale = true;
        fill.AnchorPoint = new Vector2(50, 0);
        fill.FillColor = new Color(-214190);
        fill.Size = new Vector2(250, 30);
        game.addInstance(fill);
    }
}
