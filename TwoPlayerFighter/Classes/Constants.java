package TwoPlayerFighter.Classes;

import java.awt.event.KeyEvent;

public final class Constants {
    
    public final static String BACKGROUND_IMAGE = "TwoPlayerFighter\\Media\\Menu\\Background.png";
    public final static String MAP_IMAGE = "TwoPlayerFighter\\Media\\map.png";

    public static final String PLAYER_1_TYPE = "Square";
    public static final String PLAYER_2_TYPE = "Rectangle";

    public static final Controls PLAYER_1_CONTROLS = new Controls(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W);
    public static final Controls PLAYER_2_CONTROLS = new Controls(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP);

    public static final String SQUARE_IMAGE_PATH = "TwoPlayerFighter\\Media\\square.png";
    public static final String RECTANGLE_IMAGE_PATH = "TwoPlayerFighter\\Media\\rectangle.png";

    public static final int SQUARE_MOVEMENT_SPEED = 8;
}
