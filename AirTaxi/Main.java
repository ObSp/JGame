package AirTaxi;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.GameObjects.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.Signal;
import JGamePackage.lib.task;
import JGamePackage.lib.Signal.Connection;

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

    static Image2D foreground = new Image2D();

    //menu stuff
    static boolean playing = false;
    @SuppressWarnings({"rawtypes" })
    static Signal.Connection[] menuConnections = new Signal.Connection[2];


    //static MusicQueue queue = new MusicQueue(Constants.MusicQueue);

    static Sound backgroundMusic = new Sound("AirTaxi\\Media\\Music\\Music1.wav");


    static Signal<Double>.Connection gameLoop;

    static void showCursor(){
        window.getContentPane().setCursor(Cursor.getDefaultCursor());
    }

    static void hideCursor(){
        window.getContentPane().setCursor(window.getToolkit().createCustomCursor(
            new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(),
            null
        ));
    }

    //for fps stuff
    static double elapsedTicks = 0;
    static double elapsedTime = 0;
    static double fps = 0;

    @SuppressWarnings("rawtypes")
    static void showMenu(){
        hideCursor();
        showCursor();
        cam.AnchorPoint = new Vector2();
        cam.Position = new Vector2();

        int startBY = game.getScreenHeight()/2 + 200;
        Image2D startButton = new Image2D();
        startButton.SetImagePath("AirTaxi\\Media\\StartButton.png");
        startButton.AnchorPoint = new Vector2(50);
        startButton.CFrame.Position = game.getTotalScreenSize().divide(2, 1);
        startButton.CFrame.Position.Y = startBY;
        startButton.Size = new Vector2(300);
        startButton.Name = "StartButton";
        game.addInstance(startButton);
        System.out.println(game.instances);
        System.out.println(cam.isInstanceInViewport(startButton));


        menuConnections[0] = game.OnTick.Connect(dt->{
            startButton.CFrame.Position.Y = (int)((double) startBY + 10.0*Math.sin(game.TickCount*.1));
            startButton.CFrame.Rotation = .05*Math.sin(game.TickCount*.06);
        });


        menuConnections[1] = game.Services.InputService.OnKeyPress.Connect(e->{
            playing = true;
        });

        while (!playing){
            game.waitForTick();
        }

        game.removeInstance(startButton);

        startGame();
    }

    static void startGame(){
        hideCursor();

        cam.AnchorPoint = new Vector2(50); 
        plr.Size = new Vector2(70);
        plr.AnchorPoint = new Vector2(0);
        plr.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        plr.SetImagePath("AirTaxi\\Media\\Player.png");
        game.addInstance(plr);

        cam.Position.Y = plr.CFrame.Position.Y;

        gameLoop = game.OnTick.Connect(dt->{
            plr.CFrame.Position.X += plrSpeed;

                        //collision check
            if (game.Services.CollisionService.CheckCollisionInBox(plr.GetCornerPosition(0), plr.Size, new CollisionOptions(new Instance[] {plr}, true))!=null){
                playing = false;
            }

            Vector2 mousePos = game.Services.InputService.GetMouseLocation();
            Vector2 plrPos = plr.CFrame.Position;


            plrPos.Y = (int) lerp(plrPos.Y, mousePos.Y, plrPositionLerpSpeed);

                                            //add 200 regardless of mousePos.X so no weird jittering occurs when moving the mouse horizontally
            plr.CFrame.LookAt(new Vector2(plrPos.X+150, mousePos.Y));
                                                            //adding 500 to make it on the left side of the screen
            //cam.Position.X =  (int) lerp(cam.Position.X, plrPos.X+600, CAM_LERP_SPEED);

            cam.Position.X = plrPos.X+600;

            if (game.TickCount % 100 == 0){
                for (int i = obstacles.size()-1; i > -1; i--){
                    Instance obs = obstacles.get(i);
                    if (obs.GetRenderPosition().X+obs.Size.X < 0){
                        obstacles.remove(i);
                        game.removeInstance(obs);
                    }
                }
            }

            elapsedTicks++;
            elapsedTime += dt;
            if ((int) elapsedTicks == 500){
                fps = elapsedTicks/elapsedTime;
                elapsedTicks = 0;
                elapsedTime = 0;
            }
        });

        task.spawn(()->{
            while (playing){
                game.waitSeconds(obstacleSpawnBufferSeconds);
                spawnObstacle();
            }
        });

        while (playing){
            game.waitForTick();
        }

        gameOver();
    }

    public static void main(String[] args){

        background.SetImagePath("AirTaxi\\Media\\BetterBG.png");
        background.Size = game.getTotalScreenSize().add(-0, -0);
        background.ZIndex = -4;
        background.MoveWithCamera = false;
        game.addInstance(background);

        foreground = new Image2D();
        foreground.SetImagePath("AirTaxi\\Media\\bg-foreground.png");
        foreground.Size = new Vector2(game.getScreenWidth()*2, 1000);
        foreground.ZIndex = -3;
        foreground.MoveWithCamera = false;
        foreground.AnchorPoint.Y = 100;
        foreground.CFrame.Position.Y = game.getScreenHeight()+500;
        game.addInstance(foreground);

        //queue.Start();
        backgroundMusic.setInfiniteLoop(true);

        game.setWindowTitle("Air Taxi");
        game.setWindowIcon("AirTaxi\\Media\\Player.png");
        window = game.getWindow();

        game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (kc == KeyEvent.VK_Q) System.exit(0);
        });

        showMenu();
    }

    static void gameOver(){
        playing = false;
        gameLoop.Disconnect();
        game.removeInstance(plr);
        for (Instance obs : obstacles){
            game.removeInstance(obs);
        }
        obstacles.clear();

        //reset values
        obstacleSpawnBufferSeconds = 3;
        plrSpeed = 10;
        curPassengers = 0;
        passengersDroppedOff = 0;


        showMenu();
        
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
        obstacles.add(obj);



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
