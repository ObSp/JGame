package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;

import JGamePackage.lib.*;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InputService extends Service{
    

    //signals
    private Signal<KeyEvent> onkeypress = new Signal<>();
    public SignalWrapper<KeyEvent> OnKeyPress = new SignalWrapper<>(onkeypress);

    private VoidSignal onclick = new VoidSignal();
    public VoidSignalWrapper OnMouseClick = new VoidSignalWrapper(onclick);


    private ArrayList<String> heldKeys = new ArrayList<>();

    //input vars
    private boolean isMouse1Down = false;
    @SuppressWarnings("unused")
    private boolean isMouse2Down = false;


    public InputService(JGame game){
        super(game);
        initInput();
    }

    //KEY RELATED STUFF//

        /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with horizontal movement, such as the {@code A and D} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the horizontal direction of keys currently pressed by the user
     * 
     * @see InputService#IsKeyDown(int)
     * @see InputService#GetInputVertical()
     */
    public int GetInputHorizontal(){
        int val = 0;

        if (IsKeyDown(KeyEvent.VK_A)){
            val--;
        }else if(IsKeyDown(KeyEvent.VK_D)){
            val++;
        }
        return val;
    }

        /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with vertical movement, such as the {@code W and S} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the vertical direction of keys currently pressed by the user
     * 
     * @see InputService#IsKeyDown(int)
     * @see InputService#GetInputHorizontal()
     */
    public int GetInputVertical(){
        if (IsKeyDown(KeyEvent.VK_S)){
            return -1;
        }else if(IsKeyDown(KeyEvent.VK_W)){
            return 1;
        }
        return 0;
    }

        /**Returns whether or not the Key corresponding to the provided {@code KeyCode} is currently being pressed by the user.
     * 
     * @param KeyCode : The KeyCode of the Key to be checked
     * @return Whether or not the Key is currently being pressed by the user
     * 
     * @see KeyEvent
     */
    public boolean IsKeyDown(int KeyCode){
        String keyText = KeyEvent.getKeyText(KeyCode);

        return heldKeys.indexOf(keyText)>-1 ? true : false;
    }



    //MOUSE RELATED STUFF

    /**Returns the current {@code Instance} the mouse is hovering over,
     * returning {@code null} if it's not hovering over anything.
     * 
     * @return The Instance the mouse is currently focused on
     */
    public Instance GetMouseTarget(){
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        for (Instance i : Parent.instances){
            if (i.isCoordinateInBounds(new Vector2(mouseLoc.x, mouseLoc.y-20))){ // weird offset when not subtracting 20 px????
                return i;
            }
        }

        return null;
    }

    public Vector2 GetMouseLocation(){
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        return new Vector2(mouseLoc.getX(), mouseLoc.getY());
    }

    public boolean IsMouseDown(){
        return isMouse1Down;
    }


    private void initInput(){
        var gameWindow = this.Parent.getWindow();
        var instances = this.Parent.instances;
        //Key input
        gameWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))!=-1) return;
                //if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))==-1) heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                onkeypress.Fire(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String keyText = KeyEvent.getKeyText(e.getKeyCode());
                heldKeys.remove(keyText);
            }
        });
    
        gameWindow.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                isMouse1Down = true;
                onclick.Fire();

                //firing MouseButton1Click events in instances

                Instance target = GetMouseTarget();
                if (target != null){
                    Vector2 mouseLoc = GetMouseLocation();
                    target.MouseButton1Down.Fire(mouseLoc.X, mouseLoc.Y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouse1Down = false;

                //firing MouseButton1Click events in instances

                Instance target = GetMouseTarget();
                if (target != null){
                    Vector2 mouseLoc = GetMouseLocation();
                    target.MouseButton1Up.Fire(mouseLoc.X, mouseLoc.Y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                for (Instance inst : instances){
                    if (e.getSource() == inst){
                        Vector2 mouseLoc = GetMouseLocation();
                        inst.MouseEntered.Fire(mouseLoc.X, mouseLoc.Y);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                for (Instance inst : instances){
                    if (e.getSource() == inst){
                        Vector2 mouseLoc = GetMouseLocation();
                        inst.MouseExited.Fire(mouseLoc.X, mouseLoc.Y);
                    }
                }
            }
        });
    
    }
}