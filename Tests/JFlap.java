package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.*;

class Settings{
    static int Gravity = 1;
    static int DeltaMultiplier = 500;
}

public class JFlap {
    static boolean canFlap = true;
    static int playerVel = Settings.Gravity;
    static int SideVelocity = 1;
    public static void main(String[] args) {
        
        JGame game = new JGame(.005);

        Box2D player = new Box2D();
        player.Position = new Vector2(500, 0);
        player.Size = new Vector2(60, 60);
        player.FillColor = Color.cyan;
        player.setParent(game);

        game.start();

        game.onTick(dt->{
            player.Position.Y+=playerVel*(dt*Settings.DeltaMultiplier);
            player.Position.X += SideVelocity;

            if (player.touchingBorderRight() && SideVelocity>0){
                SideVelocity = -SideVelocity;
            }

            if (player.touchingBorderLeft() && SideVelocity<0){
                SideVelocity = -SideVelocity;
            }
        });

        game.onKeyPress((KeyEvent e)->{
            if (e.getKeyCode() != KeyEvent.VK_SPACE) return;

            if (!canFlap) return;

            canFlap = false;

            playerVel-=2;

            Task.Delay(.2, ()->{
                canFlap = true;
                if (!canFlap) return;
                int origVel = playerVel;
                for (double i = 1; i<=2; i++){
                    if (!canFlap) continue;
                    playerVel+=1;
                    TimeUtil.sleep(.05);
                }
                if (!canFlap) return;
                playerVel = origVel+2;
            });
        });
    }
}
