package TwoPlayerFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.GameObjects.Sound;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.Vector2;

public class Game {
    
    private JGame game;
    public Player player1;
    public Player player2;

    Sound gameMusic = new Sound(Constants.GAME_MUSIC_MATH);

    private InputService input;

    public Game(JGame game){
        this.game = game;

        input = game.Services.InputService;
    }

    public void start(){
        var total = game.getTotalScreenSize();

        Image2D map = new Image2D();
        map.SetImagePath(Constants.MAP_IMAGE);
        map.Size = new Vector2(288*4, 192*4);
        map.AnchorPoint = new Vector2(50, 40);
        map.CFrame.Position = new Vector2(total.X/2, total.Y);
        map.Solid = true;
        game.addInstance(map);

        gameMusic.setInfiniteLoop(true);

        player1 = Player.newPlayer(game);
        player2 = Player.newPlayer(game);

        game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (Constants.PLAYER_1_CONTROLS.isKeyCodeJump(e.getKeyCode())){
                player1.jump();
            } else if (Constants.PLAYER_2_CONTROLS.isKeyCodeJump(kc)){
                player2.jump();
            }
        });

        game.OnTick.Connect(dt->{
            movement(dt);
        });
    }

    public void movement(double dt){
        if (!player1.stunned){
            if (input.IsKeyDown(Constants.PLAYER_1_CONTROLS.left)){
                player1.model.Velocity.X = -(int) (dt*(Constants.SQUARE_MOVEMENT_SPEED*100));
            } else if (input.IsKeyDown(Constants.PLAYER_1_CONTROLS.right)){
                player1.model.Velocity.X = (int) (dt*(Constants.SQUARE_MOVEMENT_SPEED*100));
            } else {
                player1.model.Velocity.X = 0;
            }
        }

        if (!player2.stunned){
            if (input.IsKeyDown(Constants.PLAYER_2_CONTROLS.left)){
                player2.model.Velocity.X = -(int) (dt*(Constants.RECTANGLE_MOVEMENT_SPEED*100));
            } else if (input.IsKeyDown(Constants.PLAYER_2_CONTROLS.right)){
                player2.model.Velocity.X = (int) (dt*(Constants.RECTANGLE_MOVEMENT_SPEED*100));
            } else {
                player2.model.Velocity.X = 0;
            }
        }
            
    }
}
