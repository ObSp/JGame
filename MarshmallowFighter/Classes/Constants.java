package MarshmallowFighter.Classes;

import JGamePackage.JGame.Types.Vector2;

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

    public static final int KNIFE_ATTACK_RANGE_PIXELS = 125;

    public static final int KNIFE_ATTACK_KNOCKBACK_X = 30;
    public static final int KNIFE_ATTACK_KNOCKBACK_Y = -10;

    public static final double PLAYER_HITBOX_WIDTH_PERCENT = .5;
    public static final double PLAYER_HITBOX_HEIGHT_PERCENT = .2;

    public static final int PLAYER_HITBOX_UP_SHIFT = 5;
    public static final int PLAYER_HITBOX_DOWN_SHIFT = 5;

    public static final int PLAYER_HITBOX_LEFT_SHIFT = 5;
    public static final int PLAYER_HITBOX_RIGHT_SHIFT = 5;


    //--{BasicMarshmallow}--//

    public static final String BasicMarshmallowPath = "MarshmallowFighter\\Media\\BasicMarshmallowStates\\";

    public static final SpriteSheet BasicMarshmallowIdle = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"idle1.png", BasicMarshmallowPath+"idle2.png"
    }, null, true);

    public static final SpriteSheet BasicMarshmallowHit = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"hit1.png", BasicMarshmallowPath+"hit2.png", BasicMarshmallowPath+"hit1.png"
    }, new int[] {10,5,1});

    public static final SpriteSheet BasicMarshmallowDeath = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"die1.png", BasicMarshmallowPath+"die2.png", BasicMarshmallowPath+"die3.png",
        BasicMarshmallowPath+"die4.png", BasicMarshmallowPath+"die5.png", BasicMarshmallowPath+"die6.png"
    }, new int[] {10,10,10,10,10,10});

    
    public static final SpriteSheet BasicMarshmallowHurtProgression = new SpriteSheet(new String[] {
        BasicMarshmallowPath+"hurt1.png",
        BasicMarshmallowPath+"hurt2.png",
        BasicMarshmallowPath+"hurt3.png",
        BasicMarshmallowPath+"hurt4.png",
    }, null);

    public static final int BASIC_MARSHMALLOW_IDLE_ANIM_BUFFER_TICKS = 40;

    public static final int BASIC_MARSHMALLOW_KNIFE_ATTACK_DAMAGE = 20;

    public static final String BASIC_MARSHMALLOW_LOOT_PATH = BasicMarshmallowPath+"Loot.png";
    public static final int BASIC_MARSHMALLOW_LOOT_SIZE = 100;

    public static final double BASIC_MARSHMALLOW_HITBOX_WIDTH_PERCENT = .6;
    public static final double BASIC_MARSHMALLOW_HITBOX_HEIGHT_PERCENT = .1;


    //--{SHOP}--//
    public static final String SHOP_IMAGE_PATH = "MarshmallowFighter\\Media\\Models\\Shop.png";
    public static final double SHOP_HITBOX_WIDTH_PERCENT = .6;
    public static final double SHOP_HITBOX_HEIGHT_PERCENT = .1;

    public static final String SHOP_INTERACTIBLE_PATH = "MarshmallowFighter\\Media\\Misc\\OpenText.png";


    //--{HOME}--//
    public static final SpriteSheet HomeAnimationProgression = new SpriteSheet(new String[] {
        "MarshmallowFighter\\Media\\Models\\House\\1.png", "MarshmallowFighter\\Media\\Models\\House\\2.png",
        "MarshmallowFighter\\Media\\Models\\House\\3.png", "MarshmallowFighter\\Media\\Models\\House\\4.png"
    }, null, true);
    public static final int HomeAnimationTickBuffer = 20;

    public static final Vector2 HOME_TELEPORT_INSIDE_POSITION = new Vector2(10000120, 10000000-100);


    //--{INTERACTIBLES}--//
    public static final String INTERACTIBLE_E_IMAGE_PATH = "MarshmallowFighter\\Media\\Misc\\PickupTextLoot.png";
    public static final int INTERACTIBLE_MAX_SHOWN_AT_ONCE = 1;

    //--{MISC}--//
    public static final String HITBOX_SAVE_PATH = "MarshmallowFighter\\Media\\HITBOXES.json";


    //--{CLASSES}--//
    public class EntityStateTypes{
        public static int Idle = 0;
        public static int Running = 1;
    }
}
