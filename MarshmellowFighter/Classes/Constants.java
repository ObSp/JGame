package MarshmellowFighter.Classes;

public final class Constants {

    //--{PLAYER RELATED}--//

    
    public static final String PlayerStatePath = "MarshmellowFighter\\Media\\PlayerStates\\";


    public static final SpriteSheet PlayerIdleSprites = new SpriteSheet(new String[] {
        PlayerStatePath+"idle1.png", PlayerStatePath+"idle2.png",
        PlayerStatePath+"idle3.png", PlayerStatePath+"idle4.png"
    }, null);

    public static final SpriteSheet KnifeAttackSprites = new SpriteSheet(new String[] {
        PlayerStatePath+"atk1.png", PlayerStatePath+"atk2.png",
        PlayerStatePath+"atk3.png", PlayerStatePath+"atk4.png"
    }, new int[] {6,5,4,3});

    public static final int IDLE_ANIM_BUFFER_TICKS = 15;
    public static final int RUN_ANIM_BUFFER_TICKS = 10;


    //--{BasicMarshmallow}--//

    public static final String BasicMarshmallowPath = "MarshmellowFighter\\Media\\BasicMarshmallowStates\\";

    public static final SpriteSheet BasicMarshmallowIdle = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"idle1.png", BasicMarshmallowPath+"idle2.png"
    }, null);

    public static final int BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS = 40;



    //--{CLASSES}--//
    public class EntityStateTypes{
        public static int Idle = 0;
        public static int Running = 1;
    }
}
