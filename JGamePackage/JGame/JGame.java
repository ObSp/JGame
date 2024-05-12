package JGamePackage.JGame;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.*;

import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;

import java.awt.Color;
import java.awt.Dimension;


public class JGame{
    public int TickCount = 0;

    public double TickSpeed = .016; //exactly 60 fps

    public double tickMult = 1000;


    public String Title = "JGame";

    private JFrame gameWindow = new JFrame(Title);
    private DrawGroup drawGroup = new DrawGroup();

    private ArrayList<Consumer<Double>> onTicks = new ArrayList<>();
    public ArrayList<Instance> instances = new ArrayList<>();


    public ServiceContainer Services;

    private void staticConstruct(){
        Promise.await(this.start());
        Services = new ServiceContainer(this);
    }

    public JGame(){
        staticConstruct();
    }

    private void render(){
        drawGroup.instances = instances;
        gameWindow.repaint();
    }

    private void tick(double dtSeconds){
        TickCount++;
        render();
        for (Consumer<Double> ontick : onTicks){
            ontick.accept(dtSeconds);
        }
        Services.PhysicsService.runPhysics(dtSeconds);
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
     * @return : A promise that is resolved once initialization is complete
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

    //adding a method to onTick
    public void onTick(Consumer<Double> ontick){
        onTicks.add(ontick);
    }

    public void waitForTick(){
        int lastTick = this.TickCount;
        while (this.TickCount == lastTick) {
            System.out.print("");
        }
    }

    public void addInstance(Instance x){
        instances.add(x);
        x.Parent = this;
    }

    public void removeInstance(Instance x){
        instances.remove(x);
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

        //--RAYCASTING--//
    /**Shoots a ray from the {@code X} coordinate of Vector2 {@code startVector2} to finishX,
     * checking for collisions each {@code Raystep} and returning a new {@code RaycastResult} if
     * a collision is detected.
     * <p>
     * If no collision is detected on the specified path, this function simply returns {@code null}
     * 
     * <p>
     * <b>NOTE:</b> The "ray" is in reality a {@code Box2D} instance, hence the required {@code raySize} parameter.
     * 
     * @param startVector2 : The start position of the ray, as a {@code Vector2}
     * @param finishX : The end X coordinate of the ray
     * @param blacklist : The list of {@code Instances} to be ignored in the case that they collide with the ray
     * @param raySize : The size of the ray box, as a Vector2
     * @return A new RaycastResult or null, depending on whether a collision was detected or not
     * 
     * @see RaycastResult
     * @see Vector2
     * @see Instance
     * @see Box2D
     */
    public RaycastResult RaycastX(Vector2 startVector2, int finishX, Instance[] blacklist, Vector2 raySize){
        Box2D raycastBox = new Box2D();
        raycastBox.Position = new Vector2(startVector2.X, startVector2.Y);
        raycastBox.Size = raySize;
        raycastBox.Name = "raybox@Jgame";
        raycastBox.FillColor = Color.blue;
        addInstance(raycastBox);

        int dir = startVector2.X<finishX ? 1 : -1;
        

        int startX = dir == 1 ? startVector2.X : finishX;
        int endX = dir == 1 ? finishX : startVector2.X;


        for (int x = startX; x <= endX; x++){
            
            raycastBox.Position.X+= dir;
            for (int i = 0; i < instances.size(); i++){
                Instance inst = instances.get(i);
                if (raycastBox.overlaps(inst) && !utilFuncs.blacklistContains(blacklist, inst) && !inst.equals(raycastBox) 
                    && !inst.Name.equals("raybox@Jgame") && inst.Solid){
                    removeInstance(raycastBox);
                    return new RaycastResult(inst, raycastBox.Position);
                }
            }
        }
        removeInstance(raycastBox);
        return null;
    }

    /**Shoots a ray from the {@code Y} coordinate of Vector2 {@code startVector2} to finishY,
     * checking for collisions each {@code Raystep} and returning a new {@code RaycastResult} if
     * a collision is detected.
     * <p>
     * If no collision is detected on the specified path, this function simply returns {@code null}
     * 
     * <p>
     * <b>NOTE:</b> The "ray" is in reality a {@code Box2D} instance, hence the required {@code raySize} parameter.
     * 
     * @param startVector2 : The start position of the ray, as a {@code Vector2}
     * @param finishY : The end Y coordinate of the ray
     * @param blacklist : The list of {@code Instances} to be ignored in the case that they collide with the ray
     * @param raySize : The size of the ray box, as a Vector2
     * @return A new RaycastResult or null, depending on whether a collision was detected or not
     * 
     * @see RaycastResult
     * @see Vector2
     * @see Instance
     * @see Box2D
     */
    public RaycastResult RaycastY(Vector2 startVector2, int finishY, Instance[] blacklist, Vector2 raySize){
        Box2D raycastBox = new Box2D();
        raycastBox.Position = new Vector2(startVector2.X, startVector2.Y);
        raycastBox.Size = raySize;
        raycastBox.FillColor = Color.green;
        raycastBox.Name = "raybox@Jgame";
        addInstance(raycastBox);
        int dir = startVector2.Y<finishY ? 1 : -1;
        

        int startY = dir == 1 ? startVector2.Y : finishY;
        int endY = dir == 1 ? finishY : startVector2.Y;


        for (int y = startY; y <= endY; y++){
            
            raycastBox.Position.Y+= dir;
            for (int i = 0; i < instances.size(); i++){
                Instance inst = instances.get(i);
                if (raycastBox.overlaps(inst) && !utilFuncs.blacklistContains(blacklist, inst) && !inst.equals(raycastBox) 
                    && !inst.Name.equals("raybox@Jgame") && inst.Solid){
                        removeInstance(raycastBox);
                    return new RaycastResult(inst, raycastBox.Position);
                }
            }
        }
        removeInstance(raycastBox);
        return null;
    }

}



class utilFuncs{
    static Instance[] toInstArray(ArrayList<Instance> x){
        Instance[] arr = new Instance[x.size()];
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