package JGame;

import java.util.function.Consumer;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import JGame.Instances.*;
import JGame.Types.RaycastResult;
import JGame.Types.Vector2;
import lib.*;
import lib.ArrayTable;


class JGAME_DEFAULTS{
    static double TICK_SPEED = .05;
}

public class JGame {
    private ArrayTable<Consumer<Double>> onTicks;

    private ArrayTable<Instance> instances;

    private ArrayTable<Consumer<KeyEvent>> keyEvents;

    private ArrayTable<String> heldKeys;

    private double tickspeed;

    private JFrame gameWindow;

    public String WindowTitle;

    private draw drawUtil;

    public int TickCount = 0;

    

    //--CONSTRUCTORS--//

    public JGame(){
        tickspeed = JGAME_DEFAULTS.TICK_SPEED;
        StaticConstruct();
    }

    public JGame(double tick_speed){
        tickspeed = tick_speed;
        StaticConstruct();
    }

    private void StaticConstruct(){
        onTicks = new ArrayTable<>();
        instances = new ArrayTable<>();
        keyEvents = new ArrayTable<>();
        heldKeys = new ArrayTable<>();
        drawUtil = new draw();
    }

    //--TICK FUCNTIONS--//
    private void tick(double deltaTimeSeconds){
        render();
        for (Consumer<Double> ontick : onTicks){
            ontick.accept(deltaTimeSeconds);
        }
    }

    public void onTick(Consumer<Double> ontickfunc){
        onTicks.add(ontickfunc);
    }


    //--GAME LOOP FUNCTIONS--//

    private void run(){
        double lastTick = getCurMillis();

        while (true) {
            double curMillis = getCurMillis();
            double dt = getDeltaTime(lastTick, curMillis)/1000;
            if (dt>=tickspeed){
                lastTick = curMillis;
                tick(dt);
            }
        }
    }

    private double getDeltaTime(double t1, double t2){
        return t2-t1;
        
    }

    private double getCurMillis(){
        return System.currentTimeMillis();
    }


    //--RENDERING--//
    private void render(){
        gameWindow.getContentPane().removeAll();
        drawUtil.items = instances;
        gameWindow.getContentPane().add(drawUtil);
        gameWindow.getContentPane().repaint();
    }


    public void addInstance(Instance x){
        instances.add(x);
    }


    //--START--//
    public Promise start(){
        return new Promise((self)->{
            
            gameWindow = new JFrame(WindowTitle!=null ? WindowTitle : "JGame Window");

            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameWindow.setUndecorated(true);


            new Thread(()->{
                run();
            }).start();

            //init input
            detectInput();
            //pre-render, otherwise it won't show up??
            render();

            
            gameWindow.setVisible(true);

            self.resolve();
        });
    }


    //--INSTANCE MANAGEMENT--//
    public Instance getInstanceByName(String name){
        for (Instance x : instances){
            if (x.Name.equals(name)) return x;
        }

        throw new Error("Unable to find instance '"+name+"' in instance collection: "+instances);
    }

    private boolean blacklistContains(Instance[] blacklist, Instance x){
        for (Instance j : blacklist){
            if (j.equals(x)) return true;
        }
        return false;
    }

    //--RAYCASTING--//
    public RaycastResult RaycastX(Vector2 startVector2, int finishX, Instance[] blacklist){
        Box2D raycastBox = new Box2D();
        raycastBox.Position = new Vector2(startVector2.X, startVector2.Y);
        raycastBox.Size = new Vector2(10, 10);
        raycastBox.FillColor = new Color(0,0,0,0);
        raycastBox.setParent(this);

        int dir = startVector2.X<finishX ? 1 : -1;
        

        int startX = dir == 1 ? startVector2.X : finishX;
        int endX = dir == 1 ? finishX : startVector2.X;

        System.out.println(dir);

        for (int x = startX; x <= endX; x+=dir){
            raycastBox.Position.X+= dir;
            for (Instance inst : instances){
                if (raycastBox.overlaps((Box2D) inst) && !blacklistContains(blacklist, inst) && !inst.equals(raycastBox)){
                    return new RaycastResult(inst, raycastBox.Position);
                }
            }
        }
        return null;
    }


    //--INPUT--//

    private void detectInput(){
        gameWindow.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))!=-1) return;
                //if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))==-1) heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                for (Consumer<KeyEvent> eventFunc : keyEvents){
                    eventFunc.accept(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                heldKeys.remove(KeyEvent.getKeyText(e.getKeyCode()));
            }
            
        });
    }

    public void onKeyPress(Consumer<KeyEvent> onpressfunc){
        keyEvents.add(onpressfunc);
    }

    public boolean isKeyDown(int KeyCode){
        String keyText = KeyEvent.getKeyText(KeyCode);

        return heldKeys.indexOf(keyText)>-1 ? true : false;
    }

    public Vector2 getTotalScreenSize(){
        Dimension size = gameWindow.getContentPane().getSize();
        return new Vector2((int) size.getWidth(),(int) size.getHeight());
    }




    //--CHANGE FUNCTIONS--//
    /**
     * 
     * @param newtick : The new tick rate
     */
    public void setTickRate(double newtick){
        tickspeed = newtick;
    }

    /**BROKEN */
    public void setFullscreen(){
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(true);
        
    }
    /**BROKEN */
    public void setBorderedFullScreen(){
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(false);
    }


    //--MISC FUNCTIONS--//
    public void quit(){
        gameWindow.dispose();
    }

}