package AirTaxi.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Types.TweenInfo;

public class BlackScreen {

    private Box2D screen = new Box2D();
    private JGame game;

    public boolean Visible = false;

    private TweenInfo tweenInfo = new TweenInfo(1.0);
    
    public BlackScreen(JGame game){
        this.game = game;
        screen.FillColor = Color.black;
        screen.Size = game.getTotalScreenSize();
        screen.MoveWithCamera = false;
        screen.ZIndex = 100;
    }

    public void Show(){
        screen.SetTransparency(0.0);
        game.addInstance(screen);
        screen.TweenTransparency(1.0, tweenInfo);
        Visible = true;
    }

    public void Hide(){
        screen.TweenTransparency(0.0, tweenInfo);
        game.removeInstance(screen);
        Visible = false;
    }
}
