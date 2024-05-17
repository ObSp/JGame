package MarshmellowFighter;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import MarshmellowFighter.Classes.*;

public class Main {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    static Player plr;

    static Camera cam = game.Camera;
    static final double CAM_LERP_SPEED = .05;

    static Box2D obstacle;

    static double dtMult = 500;

    public static void main(String[] args) {
        game.setBackground(new Color(221, 140, 198));

        BasicMarshmallow mallow = new BasicMarshmallow(game);
        plr = new Player(game);

        gameLoop();
        inputDetect();

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

            if (yInputOffset < 0){
                setFlipped(true);
            } else if (yInputOffset > 0)
                setFlipped(false);
            

            if (xInputOffset < 0){
                setFlipped(true);
            } else if (xInputOffset > 0){
                setFlipped(false);
            }


            player.CFrame.Position.X += xInputOffset;
            player.CFrame.Position.Y -= yInputOffset;

            cam.Position.X = (int) Util.lerp(cam.Position.X, player.CFrame.Position.X, CAM_LERP_SPEED);
            cam.Position.Y = (int) Util.lerp(cam.Position.Y, player.CFrame.Position.Y, CAM_LERP_SPEED);
        });
    }

    static void setFlipped(boolean flip){
        if (flip && !plr.model.FlipHorizontally){
            plr.model.FlipHorizontally = true;
            plr.model.CFrame.Position.X += 100;
        }

        if (!flip && plr.model.FlipHorizontally){
            plr.model.FlipHorizontally = false;
            plr.model.CFrame.Position.X -= 100;
        }
    }

    static void attack(){
        plr.PlayAnimation(Constants.KnifeAttackSprites);
    }

    static void inputDetect(){
        input.OnKeyPress.Connect(e->{
            if (e.getKeyCode() == KeyEvent.VK_SPACE) attack();
        });
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