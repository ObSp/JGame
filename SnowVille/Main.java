import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;
import JGamePackage.lib.Signal;
import Scenes.*;

@SuppressWarnings("unused")
public class Main {

    static JGame game = new JGame();

    //scenes
    static Scene startScene = new Start();

    //Services
    static ServiceContainer services = game.Services;
    static SceneService sceneService = services.SceneService;
    static InputService input = services.InputService;

    static Image2D foreground;
    static Image2D middleground;

    static Vector2 maximizedBoth;

    static String AssetPath = "C:\\Users\\Paul W\\Documents\\GitHub\\JGame\\SnowVille\\Assets\\";

    static Image2D player = new Image2D();

    static final int LOADING_TIME_MULTIPLIER = 5;

    public static void main(String[] args) {
        game.setWindowTitle("SnowVille");
        game.setWindowIcon("SnowVille\\Assets\\WindowIcon.png");

        maximizedBoth = game.getTotalScreenSize();
        
        player.Size = new Vector2(50,100);
        player.BackgroundTransparent = false;
        player.ImagePath = "SnowVille\\Assets\\Title.png";
        player.FillColor = Color.black;
        player.Solid = true;
        player.Anchored = false;

        //title
        intro();

        backgroundSetup();

        
        initPlayer();

        firstSceneSetup();
    }

    static void intro(){
        //music
        Sound music = new Sound("SnowVille\\Assets\\Sounds\\BackgroundMusic.wav");
        music.Play();
        music.setInfiniteLoop(true);

        //background
        Image2D title = new Image2D();
        title.Size = maximizedBoth;
        title.ImagePath = "SnowVille\\Assets\\Title.png";
        title.AnchorPoint = new Vector2(0);
        title.UpdateImagePath();
        game.addInstance(title);
        
        //progress bars
        Box2D progressBackground = new Box2D();
        progressBackground.Size = new Vector2(maximizedBoth.X-400, 40);
        progressBackground.CFrame.Position = new Vector2(maximizedBoth.X/2-progressBackground.Size.X/2, maximizedBoth.Y-200);
        progressBackground.FillColor = new Color(33, 33, 33);
        progressBackground.AnchorPoint = new Vector2(0);

        Box2D bar = new Box2D();
        bar.Size = new Vector2(0, progressBackground.Size.Y);
        bar.CFrame.Position = progressBackground.CFrame.Position;
        bar.FillColor = new Color(119, 200, 247);
        bar.AnchorPoint = new Vector2(0);

        game.addInstance(progressBackground);
        game.addInstance(bar);

        int sizeIncrement = ((maximizedBoth.X*2)*LOADING_TIME_MULTIPLIER)/maximizedBoth.X;

        while (bar.Size.X < progressBackground.Size.X) {
            bar.Size.X += sizeIncrement;
            game.waitForTick();
        }

        //setting sizes and positions to same object reference so both can be controlled at once
        bar.Size = progressBackground.Size;
        bar.CFrame.Position = progressBackground.CFrame.Position;

        game.removeInstance(progressBackground);

        task.wait(3);

        int alphaDecrement = 5;

        while (bar.FillColor.getAlpha()>0) {
            Color orig = bar.FillColor;
            bar.FillColor = new Color(orig.getRed(), orig.getGreen(), orig.getBlue(), orig.getAlpha()-alphaDecrement);
            game.waitForTick();
        }
        task.wait(1);

        //lower music volume
        while (music.Volume > .85f) {
            music.SetVolume(music.Volume-.05f);
            task.wait(.1);
        }

        game.removeInstance(title);

        
    }

    static void initPlayer(){
        player.UpdateImagePath();
        game.addInstance(player);

        game.OnTick.Connect(dt->{
            player.Velocity.X = (int) (input.GetInputHorizontal()*(dt*1000));
        });

        input.OnKeyPress.Connect(e->{
            if (e.getKeyCode()== KeyEvent.VK_SPACE){
                player.Velocity.Y = -10;
            }
        });
    }

    static void backgroundSetup(){
        foreground = new Image2D();
        foreground.Size = maximizedBoth;
        foreground.ImagePath = "SnowVille\\Assets\\Background\\Foreground.png";
        foreground.AnchorPoint = new Vector2(0);
        foreground.UpdateImagePath();

        middleground = new Image2D();
        middleground.AnchorPoint = new Vector2(0);
        middleground.Size = maximizedBoth;
        middleground.ImagePath = "SnowVille\\Assets\\Background\\MiddleGround.png";
        middleground.UpdateImagePath();
        middleground.CFrame.Position.Y -= 20;



        game.addInstance(middleground);
        game.addInstance(foreground);
    }

    static void firstSceneSetup(){
        sceneService.AddScene(startScene);
        sceneService.ShowScene(startScene);
    }


    static int scaleY(int percentage){
        return maximizedBoth.Y*percentage;
    }

    static int scaleX(int percentage){
        return maximizedBoth.X*percentage;
    }
}