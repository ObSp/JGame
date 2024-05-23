package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;

public class TitleScreen {
    private final JGame game;

    public Image2D background = new Image2D();
    public Image2D start = new Image2D();

    public TitleScreen(JGame game){
        this.game = game;
    }

    public void Show(){
        game.Title = "j";
    }
}
