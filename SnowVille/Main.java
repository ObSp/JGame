import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;
import Scenes.*;

public class Main {

    static JGame game = new JGame();

    //scenes
    static Scene startScene = new Start();

    //Services
    static ServiceContainer services = game.Services;
    static SceneService sceneService = services.SceneService;

    static Image2D foreground;

    static Vector2 maximizedBoth;

    static String AssetPath = "C:\\Users\\Paul W\\Documents\\GitHub\\JGame\\SnowVille\\Assets\\";

    static Clip playSound(String path){
        File f = new File(path);
        Clip audioclip = null;

        try{
            audioclip = AudioSystem.getClip();
            audioclip.open(AudioSystem.getAudioInputStream(f));
        } catch (Exception e){
            e.printStackTrace();
        }

        if (audioclip==null) return null;

        audioclip.start();

        
        return audioclip;
    }

    public static void main(String[] args) {
        Promise.await(game.start());
        game.setWindowTitle("SnowVille");
        game.setWindowIcon(AssetPath+"WindowIcon.png");

        maximizedBoth = game.getTotalScreenSize().clone();

        //title
        intro();

        backgroundSetup();

        //firstSceneSetup();
    }

    static void intro(){
        //music
        Clip clip = playSound(AssetPath+"Sounds\\BackgroundMusic.wav");
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        //background
        Image2D title = new Image2D();
        // to make sure transparency works with the image
        title.BackgroundTransparent = false;
        title.Size = maximizedBoth;
        title.ImagePath = AssetPath+"Title.png";
        game.addInstance(title);
        
        //progress bars
        Box2D progressBackground = new Box2D();
        progressBackground.Size = new Vector2(maximizedBoth.X-400, 40);
        progressBackground.Position = new Vector2(maximizedBoth.X/2-progressBackground.Size.X/2, maximizedBoth.Y-200);
        progressBackground.FillColor = new Color(33, 33, 33);

        Box2D bar = new Box2D();
        bar.Size = new Vector2(0, progressBackground.Size.Y);
        bar.Position = progressBackground.Position;
        bar.FillColor = new Color(119, 200, 247);

        game.addInstance(progressBackground);
        game.addInstance(bar);

        while (bar.Size.X < progressBackground.Size.X) {
            bar.Size.X += 1;
            game.waitForTick();
        }

        //setting sizes and positions to same object reference so both can be controlled at once
        bar.Size = progressBackground.Size;
        bar.Position = progressBackground.Position;

        game.removeInstance(progressBackground);

        task.wait(3);

        int alphaDecrement = 5;

        while (bar.FillColor.getAlpha()>0) {
            Color orig = bar.FillColor;
            bar.FillColor = new Color(orig.getRed(), orig.getGreen(), orig.getBlue(), orig.getAlpha()-alphaDecrement);
            game.waitForTick();
        }
        task.wait(2);

        game.removeInstance(title);

        
    }

    static void backgroundSetup(){
        foreground = new Image2D();
        foreground.Size = maximizedBoth;
        foreground.ImagePath = AssetPath + "Background\\Foreground.png";
        game.addInstance(foreground);
    }

    static void firstSceneSetup(){
        sceneService.AddScene(startScene);
        //sceneService.ShowScene(startScene);
    }
}