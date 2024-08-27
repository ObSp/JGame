package TopdownShooter.Classes;

import java.awt.Color;
import java.awt.Font;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Instances.Text2D;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.Tween;
import JGamePackage.JGame.Types.Vector2;

public class DamagePopup {
    private final Image2D Parent;
    private final JGame game;

    static Font baseFont = new Font("Arial", Font.PLAIN, 10).deriveFont(40f);
    static Font font = baseFont.deriveFont(10f);
    static Font critFont = baseFont.deriveFont(12f);

    public DamagePopup(JGame Game, Image2D parent){
        this.Parent = parent;
        this.game = Game;
        
    }

    public void OnHit(double damageTaken, boolean crit){
        Text2D text = new Text2D();
        text.ZIndex = Parent.ZIndex+1;
        text.Size = new Vector2(1);
        text.TextColor = crit ? new Color(245, 200, 66) : Color.white;
        text.Font = crit ? critFont : font;

        text.Text = ""+(int) damageTaken;

        text.CFrame.Position = Parent.GetCornerPosition(Enum.InstanceCornerType.TopRight);

        text.SetTextOpacity(1);
        game.addInstance(text);

        text.TweenPositionParallel(text.CFrame.Position.add(0, -50), null);
        Tween opacTween = text.TweenTextOpacityParallel(0.0, null);

        opacTween.Ended.Connect(()->{
            game.removeInstance(text);
        });
    }
}