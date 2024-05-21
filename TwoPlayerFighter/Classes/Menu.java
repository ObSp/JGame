package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class Menu {
    private final JGame game;

    public Image2D background = new Image2D();
    public Image2D basicMapButton = new Image2D();

    public Menu(JGame game){
        this.game = game;

        background.ImagePath = "TwoPlayerFighter\\Media\\Menu\\MenuBackground.png";
        background.UpdateImagePath();
        background.Size = game.getTotalScreenSize();
        background.MoveWithCamera = false;
        

        basicMapButton.SetImagePath("TwoPlayerFighter\\Media\\Menu\\EnterBasic.png");
        basicMapButton.Size = new Vector2(300);
        basicMapButton.AnchorPoint = new Vector2(50);
        basicMapButton.CFrame.Position = new Vector2(500,750);

        basicMapButton.MouseEntered.Connect((x,y)->{
            System.out.println("Hello");
            basicMapButton.TweenSize(basicMapButton.Size.add(20), new TweenInfo(2));
        });

        basicMapButton.MouseExited.Connect(((x,y)->{
            basicMapButton.TweenSize(basicMapButton.Size.subtract(20), new TweenInfo(2));
        }));
    }

    public void Show(){
        game.addInstance(background);
        game.addInstance(basicMapButton);
    }
}
