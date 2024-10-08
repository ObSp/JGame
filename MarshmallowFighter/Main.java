package MarshmallowFighter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import JGamePackage.JGame.*;
import JGamePackage.JGame.GameObjects.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;
import MarshmallowFighter.Classes.*;

/**NOTES:
 * 
 * 
 * 
 * 
 */

public class Main {
    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    static ArrayList<Entity> entities = new ArrayList<>();

    static String knifeSoundPath = "MarshmallowFighter\\Media\\SFX\\KnifeSwing.wav";
    static String knifeHitPath = "MarshmallowFighter\\Media\\SFX\\KnifeHit.wav";

    static Sound music;

    static Player plr;
    static Vector2 plrPos;

    static int numMellows = 0;

    static Shop shop;

    static ArrayList<Instance> hitboxes;

    /**1: facing right, -1: facing left */
    static int plrDirection = 1;

    static LoadingScreen loadingScreen = new LoadingScreen(game);

    static House home;

    static Instance[] movementCastingBlacklist;
    static CollisionOptions colOpts;

    static Camera cam = game.Camera;
    static final double CAM_LERP_SPEED = .05;

    static Box2D obstacle;

    static double dtMult = 500;

    static DataHandler dataHandler = new DataHandler(game);

    public static void main(String[] args) {
        loadingScreen.ShowWithoutAnimation();
        game.BackgroundColor = new Color(87, 41, 75);
        game.setWindowTitle("Marshmallow Fighter");
        game.setWindowIcon("MarshmallowFighter\\Media\\BasicMarshmallowStates\\idle1.png");

        hitboxes = game.Services.ParserService.ParseJSONToInstances(new File(Constants.HITBOX_SAVE_PATH));

        for (Instance hitB : hitboxes){
            hitB.Solid = true;
            hitB.SetOpacity(0);
            game.addInstance(hitB);
        }

        @SuppressWarnings("unused")
        var map = MapLoader.LoadMap(game, "MarshmallowFighter\\Media\\DATA.json");

        cam.AnchorPoint = new Vector2(50);
        //music
        music = new Sound("MarshmallowFighter\\Media\\Music\\Background.wav");
        music.SetVolume(.7);
        music.setInfiniteLoop(true);

        shop = new Shop(game);

        plr = new Player(game);
        plrPos = plr.model.CFrame.Position;
        plr.model.ZIndex = 0;

        for (int i = 0; i < 15; i++){
            BasicMarshmallow mallow = new BasicMarshmallow(game);
            mallow.model.CFrame.Position.X = (int) (Math.random()*2000);
            mallow.model.CFrame.Position.Y = (int) (Math.random()*2000);
        }
        
        home = new House(game, plr);
        home.Enter();

        SaveObject data = loadData();
        if (data == null){
            firstTimeScene();
        }

        movementCastingBlacklist = new Instance[] {plr.model};
        colOpts = new CollisionOptions(movementCastingBlacklist, true);

        gameLoop();
        inputDetect();
        zindexManagement();

        plr.interactibleTriggered.Connect(interactible ->{
            if (interactible instanceof BasicMarshmallowLoot){
                plr.MarshmallowLootCount++;
            }
            interactible.onInteract();
        });

        input.GameClosing.Connect(()->saveData());

        task.wait(3);
        loadingScreen.Hide();
    }

    static SaveObject loadData(){
        SaveObject data = dataHandler.ImportData(Constants.SAVE_DATA_PATH);
        if (data==null) return null;

        System.out.println(data);

        plr.MarshmallowLootCount = data.MarshmallowsCollected;
        plr.MarshmallowsKilled = data.MarshmallowsKilled;
        //plr.model.CFrame.Position = data.SavedPosition;

        return data;
    }

    static void saveData(){
        dataHandler.ExportData(new SaveObject(plr.MarshmallowLootCount, plr.MarshmallowsKilled, plr.model.CFrame.Position), Constants.SAVE_DATA_PATH);
    }

    static void firstTimeScene(){
       home.exitInteractible.onInteract = ()->{
            plr.model.CFrame.Position = new Vector2(1000);
            home.exitInteractible.onInteract = null;
       };
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

            if (yInputOffset != 0 || xInputOffset != 0){
                plr.translatePosition(xInputOffset, yInputOffset);
            }

            cam.Position.X = (int) Util.lerp(cam.Position.X, plr.getPositionIncludingReflectShift().X, CAM_LERP_SPEED);
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
            int plrY = (int) (plr.model.GetRenderPosition().Y + plr.model.Size.Y - 10);
            for (Instance inst : game.instances) {
                if (inst == plr.model || inst==null || inst.hasTag("Prompt") || inst.hasTag("ZStatic"))
                    continue;
                int xY = (int) (inst.GetRenderPosition().Y + inst.Size.Y);
                if (xY > plrY) {
                    inst.ZIndex = 1;
                } else {
                    inst.ZIndex = -1;
                }
            }
        });
    }

    static void attack(){
        if (plr.attacking) return;
        plr.attacking = true;
        var swing = new Sound(knifeSoundPath);
        swing.SetFramePosition(1000);
        swing.UnPause();
        plr.PlayAnimation(Constants.KnifeAttackSprites).Finished.Once(()->{
            plr.attacking = false;
        });

        var actPos = plr.getPositionIncludingReflectShift();

        //raycasting
        RaycastResult result = game.Services.RaycastService.Raycast(actPos, new Vector2((plrPos.X+Constants.KNIFE_ATTACK_RANGE_PIXELS)*plrDirection, plrPos.Y), 
            new RaycastParams(colOpts.Blacklist, false));

        
        if (result==null || Math.abs(result.FinalPosition.X-actPos.X)>Constants.KNIFE_ATTACK_RANGE_PIXELS
            || result.HitInstance.Associate == null || !(result.HitInstance.Associate instanceof Entity))
            return;

        
        Entity hitEnt = (Entity) result.HitInstance.Associate;
        Damage(hitEnt);

        if (hitEnt.isDead){
            plr.MarshmallowsKilled++;
            return;
        }
        task.spawn(()->{
            game.Services.TimeService.waitTicks(15);
            var hitsound = new Sound(knifeHitPath);
            hitsound.SetVolume(1);
            hitsound.Play();
        });
    }

    static void Damage(Entity ent){
        ent.onHit();
    }

    static void inputDetect(){
        input.OnKeyPress.Connect(e->{
            if (e.getKeyCode() == KeyEvent.VK_SPACE) attack();
        });

        input.OnMouseClick.Connect(()->{
            attack();
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