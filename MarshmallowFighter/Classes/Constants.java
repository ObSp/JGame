package MarshmallowFighter.Classes;

public final class Constants {

    //--{PLAYER RELATED}--//

    
    public static final String PlayerStatePath = "MarshmallowFighter\\Media\\PlayerStates\\";


    public static final SpriteSheet PlayerIdleSprites = new SpriteSheet(new String[] {
        PlayerStatePath+"idle1.png", PlayerStatePath+"idle2.png",
        PlayerStatePath+"idle3.png", PlayerStatePath+"idle4.png"
    }, null);

    public static final SpriteSheet KnifeAttackSprites = new SpriteSheet(new String[] {
        PlayerStatePath+"atk1.png", PlayerStatePath+"atk2.png",
        PlayerStatePath+"atk3.png", PlayerStatePath+"atk4.png"
    }, new int[] {6,5,4,1});

    public static final int IDLE_ANIM_BUFFER_TICKS = 15;
    public static final int RUN_ANIM_BUFFER_TICKS = 10;

    public static final int KNIFE_ATTACK_RANGE_PIXELS = 200;

    public static final int KNIFE_ATTACK_KNOCKBACK_X = 30;
    public static final int KNIFE_ATTACK_KNOCKBACK_Y = -10;

    public static final int PLAYER_HITBOX_SIZE_Y = 50;
    public static final int PLAYER_HITBOX_SIZE_X = 70;


    //--{BasicMarshmallow}--//

    public static final String BasicMarshmallowPath = "MarshmallowFighter\\Media\\BasicMarshmallowStates\\";

    public static final SpriteSheet BasicMarshmallowIdle = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"idle1.png", BasicMarshmallowPath+"idle2.png"
    }, null);

    public static final SpriteSheet BasicMarshmallowHit = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"hit1.png", BasicMarshmallowPath+"hit2.png", BasicMarshmallowPath+"hit1.png"
    }, new int[] {10,5,1});

    public static final SpriteSheet BasicMarshmallowDeath = new SpriteSheet(new String[] {

    }, null);

    public static final int BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS = 40;

    public static final int BASIC_MARSHMALLOW_KNIFE_ATTACK_DAMAGE = 20;

    public static final String[] BasicMarshmallowHurtProgression = {
        /*[1] =  */BasicMarshmallowPath+"hurt1.png",
        /*[2] =  */BasicMarshmallowPath+"hurt2.png",
        /*[3] =  */BasicMarshmallowPath+"hurt3.png",
        /*[4] =  */BasicMarshmallowPath+"hurt4.png",
    };



    //--{CLASSES}--//
    public class EntityStateTypes{
        public static int Idle = 0;
        public static int Running = 1;
    }
}
