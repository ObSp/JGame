package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Services.InputService;

public abstract class Player {
    static int playerCount = 0;

    protected JGame game;
    public Image2D model = new Image2D();

    public int movementDirection = 0;

    public boolean stunned = false;

    protected InputService input;

    private static Player newSquare(JGame game){
        return new Square(game);
    }

    private static Player newRect(JGame game){
        return new Rectangle(game);
    }

    public static Player newPlayer(JGame game){
        playerCount++;
        if (playerCount == 1){
            return newSquare(game);
        }else {
            return newRect(game);
        }
    }

    public abstract void ability1();
    public abstract void ability2();
    public abstract void ability3();
    public abstract void ult();

    public Player(JGame game){
        this.game = game;

        input = game.Services.InputService;
    }

    public void jump(){
        if (!model.inAir)
            model.Velocity.Y = -30;
    }
}
