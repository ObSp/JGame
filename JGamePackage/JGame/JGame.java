package JGamePackage.JGame;

import java.util.ArrayList;

import javax.swing.*;

import JGamePackage.JGame.GameObjects.Camera;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;
import JGamePackage.lib.ArrayTable;

import java.awt.Color;
import java.awt.Dimension;


public class JGame{
    public int TickCount = 0;

    public double TickSpeed = .016; //exactly 60 fps

    public double tickMult = 1000;

    public ArrayDictionary<Object, Object> Globals = new ArrayDictionary<>();

    //Signals
    private Signal<Double> ontick = new Signal<>();
    public final SignalWrapper<Double> OnTick = new SignalWrapper<>(ontick);


    public String Title = "JGame";

    private JFrame gameWindow = new JFrame(Title);
    private DrawGroup drawGroup = new DrawGroup();

    public ArrayTable<Instance> instances = new ArrayTable<>();

    public Camera Camera;


    public ServiceContainer Services;

    private void staticConstruct(){
        Promise.await(this.start());
        Services = new ServiceContainer(this);
        Camera = new Camera(this);
    }

    public JGame(){
        staticConstruct();
    }

    private void render(){
        drawGroup.instances = utilFuncs.toInstArray(instances);
        gameWindow.repaint();
    }

    private void tick(double dtSeconds){
        TickCount++;
        ontick.Fire(dtSeconds);
        if (this.Services != null)
            Services.PhysicsService.runPhysics(dtSeconds);

        render();
    }

    private double curSeconds(){
        return ((double)System.currentTimeMillis())/1000;
    }

    private void run(){
        render();
        double lastTick = curSeconds();

        while (true) {
            double curSecs = curSeconds();
            if (curSecs-lastTick>=TickSpeed){
                tick(curSecs-lastTick);
                lastTick = curSecs;
            }
        }
    }


    /**Initializes the {@code JGame} framework and returns a {@code Promise} that is resolved once
     * initialization is complete.
     * 
     * <p>
     * 
     * <b>NOTE:</b> This method <i>must</i> be called before anything else related to JGame, as it constructs and initializes
     * the {@code JFrame} window that is essential to many {@code JGame} functions.
     * 
     * <p>
     * 
     * <b>NOTE:</b> This should be called inside a {@code Promise.await(Promise)} function to ensure
     * the framework has been initialized before running any code related to it.
     * 
     * @return A promise that is resolved once initialization is complete
     * 
     * 
     * @see Promise
     * @see JFrame
     */
    public Promise start(){
        return new Promise(self ->{
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

            gameWindow.getContentPane().setBackground(Color.white);

            gameWindow.setIconImage(new ImageIcon("JGamePackage\\JGame\\Files\\icon.png").getImage());

            gameWindow.add(drawGroup);
            gameWindow.setVisible(true);

            task.wait(1);
            self.resolve();

            run();

        });
    }

    public void waitForTick(){
        int lastTick = this.TickCount;
        while (this.TickCount == lastTick) {
            System.out.print("");
        }
    }

    public void waitTicks(int ticksToWait){
        for (int t = 0; t < ticksToWait; t++)
            waitForTick();
    }

    public void addInstance(Instance x){
        x.Parent = this;
        instances.add(x);
    }

    public void removeInstance(Instance x){
        for (int i = instances.getLength()-1; i > -1; i--)
            if (instances.get(i)==x)
                instances.remove(i);
        x.Parent = null;
    }

    public Instance[] getInstances(){
        return utilFuncs.toInstArray(instances);
    }

    public Instance getInstanceByName(String name){
        for (Instance x : instances){
            if (x.Name.equals(name))
                return x;
        }

        return null;
    }

    /**Returns the current JFrame window.<p>
     * <b>NOTE:</b> This should only be used if root-level access to the window itself is needed. This should almost never be needed,
     * so developer-implemented methods like addInstance are preferred.
     * 
     */
    public JFrame getWindow(){
        if (this.gameWindow == null){
            while (gameWindow == null){
                System.out.print("");
            }
        }
        return gameWindow;
    }

    /**Returns a {@code Vector2} containing the {@code X} and {@code Y} size of the current {@code JFrame window}.
     * 
     * @return : The current size of the {@code JFrame window} as a {@code Vector2}
     */
    public Vector2 getTotalScreenSize(){
        Dimension s = gameWindow.getSize();
        return new Vector2((int)s.getWidth(), (int)s.getHeight());
    }

    public int getScreenHeight(){
        return gameWindow.getContentPane().getHeight();
    }

    public int getScreenWidth(){
        return gameWindow.getContentPane().getWidth();
    }

    public void setWindowTitle(String newTitle){
        gameWindow.setTitle(newTitle);
    }

    public void setWindowIcon(String path){
        gameWindow.setIconImage(new ImageIcon(path).getImage());
    }

    public void setBackground(Color c){
        gameWindow.getContentPane().setBackground(c);
    }

}



class utilFuncs{
    static Instance[] toInstArray(ArrayTable<Instance> x){
        Instance[] arr = new Instance[x.getLength()];
        for (int i = 0; i<arr.length; i++){
            arr[i] = x.get(i);
        }
        return arr;
    }

    static boolean blacklistContains(Instance[] blacklist, Instance x){
        for (Instance j : blacklist){
            if (j.equals(x)) return true;
        }
        return false;
    }

    static void invertY(Vector2 v){
        v.Y*=-1;
    }
}