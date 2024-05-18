package MarshmallowFighter;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.GameObjects.Sound;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.CollisionOptions;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.RaycastParams;
import JGamePackage.JGame.Types.RaycastResult;
import JGamePackage.JGame.Types.Vector2;
import MarshmallowFighter.Classes.*;

public class Main {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    static Entity[] enemies = new Entity[1];

    static String knifeSoundPath = "MarshmallowFighter\\Media\\SFX\\KnifeSwing.wav";
    static String knifeHitPath = "MarshmallowFighter\\Media\\SFX\\KnifeHit.wav";

    static Player plr;
    static Vector2 plrPos;
    static Box2D hitbox;
    static Box2D topBox;

    /**1: facing right, -1: facing left */
    static int plrDirection = 1;

    static Instance[] movementCastingBlacklist;
    static CollisionOptions colOpts;

    static Camera cam = game.Camera;
    static final double CAM_LERP_SPEED = .05;

    static Box2D obstacle;

    static double dtMult = 500;

    public static void main(String[] args) {
        game.setBackground(new Color(100, 115, 125));
        game.setWindowTitle("Marshmallow Fighter");
        game.setWindowIcon("MarshmallowFighter\\Media\\BasicMarshmallowStates\\idle1.png");

        //music
        new Sound("MarshmallowFighter\\Media\\Music\\Background.wav").setInfiniteLoop(true);

        BasicMarshmallow mallow = new BasicMarshmallow(game);
        enemies[0] = mallow;
        plr = new Player(game);
        plrPos = plr.model.CFrame.Position;

        hitbox = new Box2D();
        hitbox.AnchorPoint = new Vector2(-50+(Constants.PLAYER_HITBOX_SIZE_X-45), 100);
        hitbox.Size.X = Constants.PLAYER_HITBOX_SIZE_X;
        hitbox.Size.Y = Constants.PLAYER_HITBOX_SIZE_Y;

        topBox = new Box2D();
        topBox.Size.X = hitbox.Size.X;
        topBox.Size.Y = 10;
        topBox.FillColor = Color.red;

        movementCastingBlacklist = new Instance[] {plr.model};
        colOpts = new CollisionOptions(movementCastingBlacklist, false);

        gameLoop();
        inputDetect();
        zindexManagement();
    }

    static void gameLoop(){
        Image2D player = plr.model;

        game.OnTick.Connect(dt->{
            int xInputOffset = (int) (input.GetInputHorizontal()*(dt*dtMult));
            int yInputOffset = (int) (input.GetInputVertical()*(dt*dtMult));

            if (!isMoving() && plr.state != Constants.EntityStateTypes.Idle){ //not moving, idle
                plr.state = Constants.EntityStateTypes.Idle;
                plr.anim_buffer_ticks = Constants.IDLE_ANIM_BUFFER_TICKS;
            } else if (plr.state == Constants.EntityStateTypes.Idle && isMoving()){ //moving but not yet detected
                plr.state = Constants.EntityStateTypes.Running;
                plr.anim_buffer_ticks = Constants.RUN_ANIM_BUFFER_TICKS;
            }

            if (xInputOffset < 0){
                setFlipped(true);
            } else if (xInputOffset > 0){
                setFlipped(false);
            }


            if (xInputOffset!=0){
                player.CFrame.Position.X += xInputOffset;
            }

            if (yInputOffset!=0){

                if (yInputOffset>0 && canMoveUp()){
                    player.CFrame.Position.Y -= yInputOffset;
                }else if (yInputOffset < 0 && canMoveDown()){
                    player.CFrame.Position.Y -= yInputOffset;
                }

            }

            hitbox.CFrame.Position = player.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).add(player.FlipHorizontally ? -100 : 0, 0);
            topBox.CFrame.Position = hitbox.CFrame.Position.add(15,-hitbox.Size.Y-10);

            cam.Position.X = (int) Util.lerp(cam.Position.X, player.CFrame.Position.X, CAM_LERP_SPEED);
            cam.Position.Y = (int) Util.lerp(cam.Position.Y, player.CFrame.Position.Y, CAM_LERP_SPEED);
        });
    }

    static void setFlipped(boolean flip){
        if (flip && !plr.model.FlipHorizontally){
            plr.model.FlipHorizontally = true;
            plr.model.CFrame.Position.X += 100;
            plrDirection = -1;
        }

        if (!flip && plr.model.FlipHorizontally){
            plr.model.FlipHorizontally = false;
            plr.model.CFrame.Position.X -= 100;
            plrDirection = 1;
        }
    }

    static void zindexManagement(){
        game.OnTick.Connect(dt->{
            for (Entity x : enemies){
                int plrY = plr.model.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).Y - 10; //sub because shadow
                int xY = x.model.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).Y;
                if (xY > plrY && x.model.ZIndex <= plr.model.ZIndex){
                    x.model.ZIndex = plr.model.ZIndex + 1;
                } else if (xY <= plrY && x.model.ZIndex >= plr.model.ZIndex)
                x.model.ZIndex = plr.model.ZIndex - 1;
            }
        });
    }

    static void attack(){
        if (plr.attacking) return;
        plr.attacking = true;
        new Sound(knifeSoundPath).Play();
        plr.PlayAnimation(Constants.KnifeAttackSprites).Finished.Once(()->{
            plr.attacking = false;
        });

        //raycasting
        RaycastResult result = game.Services.RaycastService.Raycast(plrPos, new Vector2((plrPos.X+Constants.KNIFE_ATTACK_RANGE_PIXELS)*plrDirection, plrPos.Y), 
            new RaycastParams(colOpts.Blacklist, false));

        
        if (result==null || Math.abs(result.FinalPosition.X-plrPos.X)>Constants.KNIFE_ATTACK_RANGE_PIXELS)
            return;


        new Sound(knifeHitPath).Play();
    }

    static void inputDetect(){
        input.OnKeyPress.Connect(e->{
            if (e.getKeyCode() == KeyEvent.VK_SPACE) attack();
        });

        input.OnMouseClick.Connect(()->{
            attack();
        });
    }


    static boolean canMoveUp(){
        Instance col = game.Services.CollisionService.CheckCollisionInBox(hitbox.CFrame.Position.add(15,-hitbox.Size.Y+20), new Vector2(hitbox.Size.X-20, 2), colOpts);
        return !(col != null && col.CFrame.Position.Y <= plr.model.CFrame.Position.Y);
    }

    static boolean canMoveDown(){
        Instance col = game.Services.CollisionService.CheckCollisionInBox(hitbox.CFrame.Position.add(15,-80), new Vector2(hitbox.Size.X-20, 2), colOpts);
        return !(col != null && col.CFrame.Position.Y >= plr.model.CFrame.Position.Y);
    }


    static boolean isMoving(){
        return !(input.GetInputHorizontal() == 0 && input.GetInputVertical() == 0);
    }
}

class Util{
    public static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
}