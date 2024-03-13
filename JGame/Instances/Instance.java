package JGame.Instances;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import JGame.*;
import JGame.Types.*;
import lib.ArrayTable;



public abstract class Instance extends JComponent {
    public Vector2 Position;
    public Vector2 Size;
    public String Name;
    public JGame Parent;
    public Color FillColor = Color.white;
    public int BorderSizePixel = 0;
    public Color BorderColor = Color.black;
    public ArrayTable<String> Tags = new ArrayTable<>();
    public Vector2 Velocity = new Vector2(0, 0);
    public boolean Solid = false;
    public boolean Anchored = true;

    /**Sets the "Parent" property of Instance {@code this} to the JGame newparent and adds {@code this} into it's render hierarchy
     * 
     * @param newParent : The new JGame parent
     */
    public void setParent(JGame newParent){
        Parent = newParent;
        Parent.addInstance(this);
    }

    /**Removes Instance {@code this} from the parent's render hierarchy and sets the {@code Parent} property to {@code null}
     * 
     */
    public void Destroy(){
        Parent.removeInstance(this);
        Parent = null;
    }

        /**Adds the tag {@code tag} to this Instance's tags
     * 
     * @param tag The tag to be added to this instance
     */
    public void addTag(String tag){
        if (Tags.indexOf(tag)==-1) return; // already added tag

        Tags.add(tag);
    }

    public void removeTag(String tag){
        Tags.remove(tag);
    }

    public boolean hasTag(String tag){
        return Tags.indexOf(tag) != -1 ? true : false;
    }

    public String[] getTags(){
        return Tags.toArray();
    }

    /**Returns whether or not Instance {@code is touching the top border of the screen}
     * 
     * @return
     */
    public boolean touchingBorderTop(){
        return (Position.Y+(Size.Y/2))<0;
    }

    public boolean touchingBorderLeft(){
        return (Position.X+(Size.X/2))<0;
    }

    public boolean touchingBorderRight(){
        return (Position.X+(Size.X/2))>Parent.getTotalScreenSize().X;
    }

    public boolean touchingBorderBottom(){
        return Position.Y+(Size.Y/2)>Parent.getTotalScreenSize().Y;
    }

    public boolean collidingRight(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(Position, Position.X+(Size.X), bl, new Vector2(2, Size.Y-3));

        return r != null ? true : false; 
    }


    public boolean collidingLeft(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(Position, Position.X-4, bl, new Vector2(2, Size.Y-3));

        return r != null ? true : false; 
    }

    public boolean collidingBottom(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(Position, Position.Y+Size.Y, bl, new Vector2(Size.X-3, 2));

        return r != null ? true : false; 
    }

    public boolean collidingTop(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(Position, Position.Y-3, bl, new Vector2(Size.X-3, 2));

        return r != null ? true : false; 
    }


    public boolean canMoveLeft(){
        return !collidingLeft() && !touchingBorderLeft();
    }

    public boolean canMoveRight(){
        return !collidingRight() && !touchingBorderRight();
    }

    public boolean canMoveUp(){
        return !collidingTop() && !touchingBorderTop();
    }

    public boolean canMoveDown(){
        return !collidingBottom() && !touchingBorderBottom();
    }


    abstract public void render(Graphics2D g);
}
