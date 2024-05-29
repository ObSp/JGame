package AirTaxi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import AirTaxi.Classes.Constants;
import AirTaxi.Classes.MusicQueue;
import JGamePackage.JGame.JGame;
import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.lib.task;

public class Main {
    static JGame game = new JGame(new StartArgs(true));

    static Image2D plr = new Image2D();

    static Camera cam = game.Camera;

    static double plrPositionLerpSpeed = .1;

    static double obstacleSpawnBufferSeconds = 3;

    static int plrSpeed = 10;

    static int curPassengers = 0;

    static int passengersDroppedOff = 0;

    static Image2D background = new Image2D();

    static JFrame window;

    static ArrayList<Instance> obstacles = new ArrayList<>();

    static MusicQueue queue = new MusicQueue(Constants.MusicQueue);

    static void showCursor(){
        window.setCursor(Cursor.getDefaultCursor());
    }

    static void hideCursor(){
        window.getContentPane().setCursor(window.getToolkit().createCustomCursor(
            new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(),
            null
        ));
    }

    public static void main(String[] args){

        background.SetImagePath("AirTaxi\\Media\\Background.png");
        background.MoveWithCamera = false;
        background.Size = game.getTotalScreenSize().add(0, 200);
        background.ZIndex = -2;
        game.addInstance(background);

        queue.Start();

        //game.setBackground(new Color(249, 69, 87));
        game.setWindowTitle("Air Taxi");
        window = game.getWindow();

        hideCursor();

        cam.AnchorPoint = new Vector2(50); 
        plr.Size = new Vector2(70);
        plr.FillColor = new Color(255, 185, 0);
        plr.AnchorPoint = new Vector2(0);
        plr.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        plr.Anchored = false;
        plr.WeightPercentage = 0;
        plr.SetImagePath("AirTaxi\\Media\\Player.png");
        game.addInstance(plr);

        cam.Position.Y = plr.CFrame.Position.Y;

        game.OnTick.Connect(dt->{
            plr.CFrame.Position.X += plrSpeed;

                        //collision check
            if (game.Services.CollisionService.CheckCollisionInBox(plr.GetCornerPosition(0), plr.Size, new CollisionOptions(new Instance[] {plr}, true))!=null){
                gameOver();
            }

            Vector2 mousePos = game.Services.InputService.GetMouseLocation();
            Vector2 plrPos = plr.CFrame.Position;


            plrPos.Y = (int) lerp(plrPos.Y, mousePos.Y, plrPositionLerpSpeed);

                                            //add 200 regardless of mousePos.X so no weird jittering occurs when moving the mouse horizontally
            plr.CFrame.LookAt(new Vector2(plrPos.X+150, mousePos.Y));
                                                            //adding 500 to make it on the left side of the screen
            //cam.Position.X =  (int) lerp(cam.Position.X, plrPos.X+600, CAM_LERP_SPEED);

            cam.Position.X = plrPos.X+600;

            for (int i = obstacles.size()-1; i > -1; i--){
                Instance obs = obstacles.get(i);
                if (obs.GetCornerPosition(Enum.InstanceCornerType.TopRight).X < 0){
                    obstacles.remove(i);
                    game.removeInstance(obs);
                }
            }
        });

        task.spawn(()->{
            while (true){
                game.waitSeconds(obstacleSpawnBufferSeconds);
                spawnObstacle();
            }
        });

        game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (kc == KeyEvent.VK_F11) System.exit(0);
        });
    }

    static void gameOver(){
        System.exit(0);
    }

    static void speedup(){
        if (plrSpeed < 30){
            plrSpeed += 1;
        }

        if (obstacleSpawnBufferSeconds > .7){
            obstacleSpawnBufferSeconds -= .05;
        }
    }

    static void setTopObstacle(Image2D obj){
        obj.CFrame.Position.Y = (int) random(200, obj.Size.Y);
    }

    static void setBottomObstacle(Image2D obj){
        Vector2 topLeftofCam = cam.GetTopLeftCorner();
        int bottomOfScreen = topLeftofCam.Y+game.getScreenHeight();
        obj.AnchorPoint.Y = 0;

        obj.CFrame.Position.Y = (int) random(bottomOfScreen-200, bottomOfScreen-obj.Size.Y);
    }

    static void spawnObstacle(){
        speedup();

        Vector2 topLeftofCam = cam.GetTopLeftCorner();
        int rightSideOfScreen = topLeftofCam.X+game.getScreenWidth();

        double scaleFactor = random(1, 1.4);

        Image2D obj = new Image2D();
        obj.AnchorPoint = new Vector2(0, 100);
        obj.Size = new Vector2(350, 703);
        obj.Size.X = (int) ((double) obj.Size.X * scaleFactor);
        obj.Size.Y = (int) ((double) obj.Size.Y * scaleFactor);
        obj.CFrame.Position.X = rightSideOfScreen;
        obj.SetImagePath("AirTaxi\\Media\\Building.png");
        obj.Solid = true;



        if (Math.random()>=.5){
            setTopObstacle(obj);
        } else {
            setBottomObstacle(obj);
        }
        game.addInstance(obj);
    }

    static double lerp(double a, double b, double t){
        return (1-t) * a + t*b;
    }

    static double random(double min, double max){
        return (Math.random()*(max-min))+min;
    }
}
