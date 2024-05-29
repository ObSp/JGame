package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.lib.VoidSignal;

public abstract class Player {
    static int playerCount = 0;

    protected JGame game;
    public Image2D model = new Image2D();

    public int movementDirection = 0;

    private int Health = 100;

    public boolean stunned = false;

    public VoidSignal Died = new VoidSignal();

    protected InputService input;

    private static Player newSquare(JGame game){
        Square sq = new Square(game);
        sq.model.Name = "Player1";
        return sq;
    }

    private static Player newRect(JGame game){
        Rectangle rect = new Rectangle(game);
        rect.model.Name = "Player2";
        return rect;
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
        if (!model.inAir && !stunned)
            model.Velocity.Y = -30;
    }

    public void TakeDamage(int dmg){
        if (Health <= 0) return;
        Health -= dmg;
        if (Health <= 0){
            Died.Fire();
        }
    }

    public int GetHealth(){
        return Health;
    }
}
