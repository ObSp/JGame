package MarshmallowFighter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;

public class LoadingScreen {
    private JGame game;

    private Box2D background = new Box2D();

    public LoadingScreen(JGame game){
        this.game = game;

        background.Size = game.getTotalScreenSize();
        background.FillColor = Color.black;
        background.ZIndex = 5;
        background.MoveWithCamera = false;
        background.addTag("ZStatic");
    }

    public void ShowWithoutAnimation(){
        if (background.GetOpacity() != 1.0)
            background.SetOpacity(1);
        game.addInstance(background);
    }

    public void HideWithoutAnimation(){
        game.removeInstance(background);
    }

    public void Show(){
        if (background.Parent != null) return; //already shown
        background.SetOpacity(0);
        game.addInstance(background);
        background.TweenOpacity(1.0, null);
    }

    public void Hide(){
        if (background.Parent == null) return; //already hidden
        background.TweenOpacity(0.0, null);
        game.removeInstance(background);
    }
}
