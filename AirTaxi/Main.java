package AirTaxi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import AirTaxi.Classes.*;
import JGamePackage.JGame.JGame;
import JGamePackage.JGame.GameObjects.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.lib.AbstractConnection;
import JGamePackage.lib.Signal;
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

    static Image2D foreground = new Image2D();
    static Image2D foreground2 = new Image2D();

    static BlackScreen blackScreen = new BlackScreen(game);

    static Font font;

    //menu stuff
    static boolean playing = false;

    static AbstractConnection[] menuConnections = new AbstractConnection[3];


    static MusicQueue queue = new MusicQueue(Constants.MusicQueue);

    static Sound backgroundMusic = new Sound("AirTaxi\\Media\\Music\\Music1.wav");

    static ArrayList<Instance> taxiStations = new ArrayList<>();


    static Signal<Double>.Connection gameLoop;


    //config:
    static double TaxiStationChance = .9;


    //for fps stuff
    static double elapsedTicks = 0;
    static double elapsedTime = 0;
    static double fps = 0;

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

    public static void main(String[] args){
        game.runPhysics = false;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("AirTaxi\\Media\\Fonts\\PixeloidSans-mLxMm.ttf"));
        } catch(Exception e){
            throw new Error(e);
        }

        int fg_mult = 13;

        foreground = new Image2D();
        foreground.SetImagePath("AirTaxi\\Media\\bg-foreground.png");
        foreground.Size = new Vector2(fg_mult*128, fg_mult*64);
        foreground.ZIndex = -3;
        foreground.MoveWithCamera = false;
        foreground.AnchorPoint.Y = 100;
        foreground.AnchorPoint.X = 0;
        foreground.CFrame.Position.Y = game.getScreenHeight();
        game.addInstance(foreground);

        foreground2 = foreground.clone();
        foreground2.CFrame.Position.X = foreground.Size.X;
        game.addInstance(foreground2);

        queue.Start();

        game.setWindowTitle("Air Taxi");
        game.setWindowIcon("AirTaxi\\Media\\Player.png");
        window = game.getWindow();

        plr.SetImagePath("AirTaxi\\Media\\Player.png");
        plr.Size = new Vector2(70);
        plr.AnchorPoint = new Vector2(50);

        game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (kc == KeyEvent.VK_Q) System.exit(0);
        });

        showMenu();
    }

    static void showMenu(){
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

        menuConnections[0] = game.OnTick.Connect(dt->{
            startButton.CFrame.Position.Y = (int)((double) startBY + 10.0*Math.sin(game.TickCount*.1));
            startButton.CFrame.Rotation = .05*Math.sin(game.TickCount*.06);
        });

        if (blackScreen.Visible){
            game.waitSeconds(2);
            blackScreen.Hide();
        }


        menuConnections[1] = game.Services.InputService.OnKeyPress.Connect(e->{
            int kc = e.getKeyCode();
            if (kc == KeyEvent.VK_SPACE){
                playing = true;
            }
        });

        menuConnections[2] = startButton.MouseButton1Down.Connect((x,y)->{
            playing = true;
        });

        while (!playing){
            
            game.waitForTick();
        }


        new Sound("AirTaxi\\Media\\SFX\\blipSelect.wav").Play();
        blackScreen.Show();

        for (int i = 0; i < menuConnections.length; i++){
            menuConnections[i].Disconnect();
            menuConnections[i] = null;
        }

        game.removeInstance(startButton);

        startGame();
    }

    static void startGame(){
        hideCursor();

        cam.AnchorPoint = new Vector2(50); 
        plr.CFrame.Position = game.getTotalScreenSize().divide(2, 2);

        Image2D personSilhuette = new Image2D();
        personSilhuette.SetImagePath("AirTaxi\\Media\\PersonSilhuette.png");
        personSilhuette.MoveWithCamera = false;
        personSilhuette.AnchorPoint = new Vector2(50);
        personSilhuette.Size = new Vector2(50);
        personSilhuette.CFrame.Position = new Vector2(100);
        personSilhuette.ZIndex = 5;
        game.addInstance(personSilhuette);

        Text2D passengerCounter = new Text2D();

        passengerCounter.AnchorPoint = new Vector2(50);
        passengerCounter.CFrame.Position = new Vector2(150, 112);
        passengerCounter.Size = new Vector2(50);

        passengerCounter.TextColor = Color.white;
        passengerCounter.Font = font.deriveFont(50f);
        passengerCounter.HorizontalOffsetPercentage = 0;

        passengerCounter.Text = "0";
        passengerCounter.ZIndex = 5;
        passengerCounter.MoveWithCamera = false;
        game.addInstance(passengerCounter);
        
        game.addInstance(plr);

        cam.Position.Y = plr.CFrame.Position.Y;

        if (blackScreen.Visible){
            game.waitSeconds(2);
            task.spawn(()->blackScreen.Hide());
        }

        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        gameLoop = game.OnTick.Connect(dt->{
            plr.CFrame.Position.X += plrSpeed;

            int foregroundMove = (int) (plrSpeed/1.6);

            foreground.CFrame.Position.X -= foregroundMove;
            foreground2.CFrame.Position.X -= foregroundMove;


            if (foreground.GetRenderPosition().X+foreground.Size.X < 0){
                foreground.CFrame.Position.X = foreground2.CFrame.Position.X + screenWidth;
            }

            if (foreground2.GetRenderPosition().X+foreground.Size.X < 0){
                foreground2.CFrame.Position.X = foreground.CFrame.Position.X + screenWidth;
            }

            //collision check
            Instance col = game.Services.CollisionService.CheckCollisionInBox(
                plr.GetCornerPosition(0), 
                plr.Size.add(-15), 
                new CollisionOptions(new Instance[] {plr}, true)
            );
            
            if (col != null){
                if (col.Name == "Station"){
                    curPassengers++;
                    passengerCounter.Text = curPassengers+"";
                    col.Solid = false;
                    new Sound("AirTaxi\\Media\\SFX\\pickedUp.wav").Play();
                } else {
                    playing = false;
                }
            }

            Vector2 mousePos = game.Services.InputService.GetMouseLocation();
            Vector2 plrPos = plr.CFrame.Position;


            plrPos.Y = (int) lerp(plrPos.Y, mousePos.Y, plrPositionLerpSpeed);

                                            //add 150 regardless of mousePos.X so no weird jittering occurs when moving the mouse horizontally
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

                for (int i = taxiStations.size()-1; i > -1; i--){
                    Instance station = taxiStations.get(i);
                    if (station.GetRenderPosition().X+station.Size.X < 0){
                        taxiStations.remove(i);
                        game.removeInstance(station);
                    }
                }
            }

            elapsedTicks++;
            elapsedTime += dt;
            if ((int) elapsedTicks == 500){
                fps = elapsedTicks/elapsedTime;
                elapsedTicks = 0;
                elapsedTime = 0;
                System.out.println(fps);
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

        game.removeInstance(passengerCounter);
        game.removeInstance(personSilhuette);
        gameOver();
    }

    static void gameOver(){
        gameLoop.Disconnect();

        //game over anim
        Oval2D explosion = new Oval2D();
        explosion.Size = new Vector2();
        explosion.AnchorPoint = new Vector2(50);
        explosion.CFrame.Position = plr.GetCenterPosition();
        explosion.ZIndex = 3;
        game.addInstance(explosion);

        new Sound("AirTaxi\\Media\\SFX\\explosion.wav").Play();
        explosion.TweenSize(new Vector2(200), new TweenInfo(15));

        blackScreen.Show();
        game.removeInstance(plr);
        game.removeInstance(explosion);
        for (Instance obs : obstacles){
            game.removeInstance(obs);
        }
        obstacles.clear();
        for (Instance station : taxiStations){
            game.removeInstance(station);
        }
        taxiStations.clear();

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

        boolean top = true;

        if (Math.random()>=.5){
            setTopObstacle(obj);
        } else {
            setBottomObstacle(obj);
            top = false;
        }

        if (Math.random()<=TaxiStationChance){//spawn taxi station
            Image2D station = new Image2D();
            station.SetImagePath("AirTaxi\\Media\\StationEmpty.png");
            station.BackgroundTransparent = false;
            station.CFrame.Rotation = top ? Math.toRadians(180) : 0;
            station.AnchorPoint = new Vector2(50, 0);
            station.Size = new Vector2(80);
            station.CFrame.Position.X = obj.CFrame.Position.X+(obj.Size.X/2);
            station.CFrame.Position.Y = !top ? 
                obj.GetCornerPosition(Enum.InstanceCornerType.TopLeft).Y-station.Size.Y : 
                obj.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).Y;

            station.Name = "Station";
            station.Solid = true;
            station.FillColor = new Color(3, 111, 252);
            station.SetTransparency(.5);
            station.ZIndex = 1;
            game.addInstance(station);
            taxiStations.add(station);
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
