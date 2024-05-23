package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;

public class Menu {
    private final JGame game;

    public Image2D background = new Image2D();
    public Image2D basicMapButton = new Image2D();

    public Menu(JGame game){
        this.game = game;

        background.ImagePath = Constants.BACKGROUND_IMAGE;
        background.UpdateImagePath();
        background.Size = game.getTotalScreenSize();
        background.MoveWithCamera = false;
    
    }

    public void Show(){
        game.addInstance(background);
    }
}
