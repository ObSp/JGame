package TwoPlayerFighter.Classes;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.Types.Vector2;

public final class Constants {
    
    public final static String BACKGROUND_IMAGE = "TwoPlayerFighter\\Media\\Menu\\Background.png";
    public final static String MAP_IMAGE = "TwoPlayerFighter\\Media\\map.png";

    public static final String PLAYER_1_TYPE = "Square";
    public static final String PLAYER_2_TYPE = "Rectangle";

    public static final Controls PLAYER_1_CONTROLS = new Controls(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_E);
    public static final Controls PLAYER_2_CONTROLS = new Controls(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_NUMPAD0);

    public static final String SQUARE_IMAGE_PATH = "TwoPlayerFighter\\Media\\square.png";
    public static final String RECTANGLE_IMAGE_PATH = "TwoPlayerFighter\\Media\\rectangle.png";

    public static final int SQUARE_MOVEMENT_SPEED = 8;
    public static final int RECTANGLE_MOVEMENT_SPEED = 7;

    //--{PLAYER 1 CONFIG}--//
    public static final int PLAYER_1_AB1_STUN_FRAMES = 15;
    public static final Vector2 PLAYER_1_AB1_KNOCKBACK = new Vector2(30, -30);
    public static final int PLAYER_1_AB1_DAMAGE = 10;

    //--{PLAYER 2 CONFIG}--//
    public static final Vector2 PLAYER_2_AB1_PLATFORM_SIZE = new Vector2(100, 30);
    public static final int PLAYER_2_AB1_UP_SHIFT = 100;
    


    public static final String GAME_MUSIC_PATH = "TwoPlayerFighter\\Media\\Sounds\\FightingMusic.wav";
}
