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

    Sound gameMusic = new Sound(Constants.GAME_MUSIC_PATH);

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

        Controls p1C = Constants.PLAYER_1_CONTROLS;
        Controls p2C = Constants.PLAYER_2_CONTROLS;

        game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (p1C.isKeyCodeJump(e.getKeyCode())){
                player1.jump();
            } else if (p2C.isKeyCodeJump(kc)){
                player2.jump();
            }

            if (kc == p1C.ability1){
                player1.ability1();
            } else if (kc == p2C.ability1){
                player2.ability1();
            }

            
        });

        game.OnTick.Connect(dt->{
            movement(dt);
        });
    }

    public void movement(double dt){
        player1.movementDirection = input.IsKeyDown(Constants.PLAYER_1_CONTROLS.left) ? -1 : (input.IsKeyDown(Constants.PLAYER_1_CONTROLS.right) ? 1 : 0);

        if (!player1.stunned){
            player1.model.Velocity.X = (int) (player1.movementDirection*(dt*(Constants.SQUARE_MOVEMENT_SPEED*100)));
        }

        player2.movementDirection = input.IsKeyDown(Constants.PLAYER_2_CONTROLS.left) ? -1 : (input.IsKeyDown(Constants.PLAYER_2_CONTROLS.right) ? 1 : 0);

        if (!player2.stunned){
            player2.model.Velocity.X = (int) (player2.movementDirection*(dt*(Constants.RECTANGLE_MOVEMENT_SPEED*100)));
        }
            
    }
}
