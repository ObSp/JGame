package JGame;

import java.util.function.Consumer;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import JGame.Instances.*;

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
            gameWindow.setUndecorated(false);


            new Thread(()->{
                run();
            }).start();
            detectInput();
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


    //--INPUT--//

    private void detectInput(){
        gameWindow.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
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

    public boolean isKeyDown(int KeyCode){
        String keyText = KeyEvent.getKeyText(KeyCode);

        return heldKeys.indexOf(keyText)>-1 ? true : false;
    }




    //--CHANGE FUNCTIONS--//
    public void setTickRate(double newtick){
        tickspeed = newtick;
    }

}