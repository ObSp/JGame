package JGamePackage.JGame;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.*;

import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class UTIL_VARS {
}


public class JGame{
    public int TickCount = 0;

    public double TickSpeed = .016; //exactly 60 fps

    public double tickMult = 1000;


    public String Title = "JGame";

    private JFrame gameWindow;
    private DrawGroup drawGroup;

    private ArrayList<Consumer<Double>> onTicks;
    public ArrayList<Instance> instances;
    private ArrayList<Consumer<KeyEvent>> keyEvents;
    private ArrayList<String> heldKeys;
    private ArrayList<Runnable> clickEvents;

    public ServiceContainer Services = new ServiceContainer(this);

    //input vars
    private boolean isMouse1Down = false;
    @SuppressWarnings("unused")
    private boolean isMouse2Down = false;

    private void staticConstruct(){
        onTicks = new ArrayList<>();
        gameWindow = new JFrame(Title);
        instances = new ArrayList<>();
        keyEvents = new ArrayList<>();
        heldKeys = new ArrayList<>();
        drawGroup = new DrawGroup();
        clickEvents = new ArrayList<>();
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
        detectInput();
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

    /**Returns the current JFrame window.<p>
     * <b>NOTE:</b> This should only be used if root-level access to the window itself is needed. This should almost never be needed,
     * so developer-implemented methods like addInstance are preferred.
     * 
     */
    public JFrame getWindow(){
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

    public void setBackground(Color c){
        gameWindow.getContentPane().setBackground(c);
    }


    //INPUT
    private void detectInput(){
        gameWindow.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))!=-1) return;
                //if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))==-1) heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                for (int i = 0; i<keyEvents.size(); i++){
                    keyEvents.get(i).accept(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String keyText = KeyEvent.getKeyText(e.getKeyCode());
                heldKeys.remove(keyText);
            }
            
        });

        gameWindow.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isMouse1Down = true;
                for (Runnable r : clickEvents){
                    r.run();
                }

                //firing MouseButton1Click events in instances

                Instance target = getMouseTarget();
                if (target != null){
                    Vector2 mouseLoc = getMouseLocation();
                    target.MouseButton1Down.Fire(mouseLoc.X, mouseLoc.Y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouse1Down = false;

                //firing MouseButton1Click events in instances

                Instance target = getMouseTarget();
                if (target != null){
                    Vector2 mouseLoc = getMouseLocation();
                    target.MouseButton1Up.Fire(mouseLoc.X, mouseLoc.Y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                for (Instance inst : instances){
                    if (e.getSource() == inst){
                        Vector2 mouseLoc = getMouseLocation();
                        inst.MouseEntered.Fire(mouseLoc.X, mouseLoc.Y);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                for (Instance inst : instances){
                    if (e.getSource() == inst){
                        Vector2 mouseLoc = getMouseLocation();
                        inst.MouseExited.Fire(mouseLoc.X, mouseLoc.Y);
                    }
                }
            }
        });
    }


    /**Returns the current {@code Instance} the mouse is hovering over,
     * returning {@code null} if it's not hovering over anything.
     * 
     * @return The Instance the mouse is currently focused on
     */
    public Instance getMouseTarget(){
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        for (Instance i : instances){
            if (i.isCoordinateInBounds(new Vector2(mouseLoc.x, mouseLoc.y))){
                return i;
            }
        }

        return null;
    }

    public Vector2 getMouseLocation(){
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        return new Vector2(mouseLoc.getX(), mouseLoc.getY());
    }

    /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with horizontal movement, such as the {@code A and D} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the horizontal direction of keys currently pressed by the user
     * 
     * @see JGame#isKeyDown(int)
     * @see JGame#getInputVertical()
     */
    public int getInputHorizontal(){
        if (isKeyDown(KeyEvent.VK_A)){
            return -1;
        }else if(isKeyDown(KeyEvent.VK_D)){
            return 1;
        }
        return 0;
    }

    /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with vertical movement, such as the {@code W and S} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the vertical direction of keys currently pressed by the user
     * 
     * @see JGame#isKeyDown(int)
     * @see JGame#getInputHorizontal()
     */
    public int getInputVertical(){
        if (isKeyDown(KeyEvent.VK_S)){
            return -1;
        }else if(isKeyDown(KeyEvent.VK_W)){
            return 1;
        }
        return 0;
    }



    public void onKeyPress(Consumer<KeyEvent> onpressfunc){
        keyEvents.add(onpressfunc);
    }

    public void onMouseClick(Runnable onclickfunc){
        clickEvents.add(onclickfunc);
    }


    /**Returns whether or not the Key corresponding to the provided {@code KeyCode} is currently being pressed by the user.
     * 
     * @param KeyCode : The KeyCode of the Key to be checked
     * @return Whether or not the Key is currently being pressed by the user
     * 
     * @see KeyEvent
     */
    public boolean isKeyDown(int KeyCode){
        String keyText = KeyEvent.getKeyText(KeyCode);

        return heldKeys.indexOf(keyText)>-1 ? true : false;
    }

    public boolean isMouseDown(){
        return isMouse1Down;
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
                if (raycastBox.overlaps(inst) && !utilFuncs.blacklistContains(blacklist, inst) && !inst.equals(raycastBox) && !inst.Name.equals("raybox@Jgame")){
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
                if (raycastBox.overlaps(inst) && !utilFuncs.blacklistContains(blacklist, inst) && !inst.equals(raycastBox) && !inst.Name.equals("raybox@Jgame")){
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
        for (int i = 0; i<x.size(); i++){
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