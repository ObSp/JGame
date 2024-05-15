package JGamePackage.JGame.Instances;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.ArrayTable;
import JGamePackage.lib.BiSignal;


/**An abstract class that all {@code JGame} classes are a subclass of. <p>
 * It contains basic methods, like Instance.Destroy() that are needed when working with basic rendering objects.
 * There is currently only one constructor present, as the instance variables are intended to be set after creating the object.<p>
 * 
 * The {@code Parent} property should only be set once <b><i>all inital properties have been set</i></b>, as events and rendering will start as soon
 * as this object has been added to a JGame's rendering list.<p>
 * In addition, the {@code Parent} property should <b>never</b> be set by the user.
 * Instead, the intended use is to call {@code JGame.addInstance(this)}.
 * 
 */
public abstract class Instance extends JComponent {
    /**The position of the Instance in 2D space.<p>
     * <b>NOTE:</b> This is the position of the <b>top-left corner</b> of the object, <i> not</i> the middle.
     * 
     */
    public Vector2 Position = new Vector2(0, 0);

    /**The size of the Instance in 2D space.
     * 
     */
    public Vector2 Size = new Vector2(100, 100);

    /**A non-unique identifier that can be used to access this object through the {@code Parent}.
     * 
     */
    public String Name = "Instance";
    public JGame Parent;

    /**What color this object will be drawn as
     * 
     */
    public Color FillColor = Color.white;

    /**The number of pixels of border that will surround this instance.
     * 
     */
    public int BorderSizePixel = 0;

    /**The color of this instance's outline. Will only be applied if {@code BorderSizePixel} is greater than 0.
     * 
     */
    public Color BorderColor = Color.black;

    /**The rotation, in <b>radians</b>, of the Instance.
     * 
     */
    public double Rotation = 0;

    /**A list of non-unique strings that can be used to gather collections of objects
     * 
     */
    public ArrayTable<String> Tags = new ArrayTable<>();

    /**By how much this object will be displaced every tick. <p>
     * <b>NOTE:</b> This will only be applied on non-anchored instances.
     * 
     */
    public Vector2 Velocity = new Vector2(0, 0);

    /**Controls whether or not other solid objects can pass through this object
     * 
     */
    public boolean Solid = false;

    /**Controls whether this instance will be affected by physics
     * 
     */
    public boolean Anchored = true;

    /**Whether an instance will be repainted every frame. This is useful for background images that don't change and therefore shouldn't
     * tank performance by being painted every frame but should still be shown
     * 
     */
    public boolean Static = false;
    public boolean wasDrawn = false;

    /**A signal fired when the user left-clicks on this Instance
     */
    public BiSignal<Integer, Integer> MouseButton1Down = new BiSignal<>();

    /**A signal fired when the user left-clicks on this Instance
     */
    public BiSignal<Integer, Integer> MouseButton1Up = new BiSignal<>();

    /**A signal fired when the mouse pointer enters this Instance
     */
    public BiSignal<Integer, Integer> MouseEntered = new BiSignal<>();

    /**A signal fired when the mouse pointer exits this Instance
     */
    public BiSignal<Integer, Integer> MouseExited = new BiSignal<>();

    public boolean inAir = false;
    public double timeInAir = 0.0;


    /**Sets {@code this.Parent} to null, removes itself from the rendering list of {@code this.Parent}, and sets all instance variables to null
     * 
     */
    public void Destroy(){
        Parent.removeInstance(this);
        Position = null;
        Size = null;
        Name = null;
        FillColor = null;
        BorderSizePixel = 0;
        BorderColor = null;
        Tags = null;
        Velocity = null;
        Solid = false;
        Anchored = false;
    }



    /**Adds the tag {@code tag} to this Instance's tags
     * 
     * @param tag The tag to be added to this instance
     */
    public void addTag(String tag){
        if (Tags.indexOf(tag)==-1) return; // already added tag

        Tags.add(tag);
    }

    /**Removes the tag {@code tag} from this Instance's tags
     * 
     * @param tag The tag to be removed from this instance
     */
    public void removeTag(String tag){
        Tags.remove(tag);
    }
    /**Returns whether or not the Tag {@code tag} is present in the list of this instance's tags
     * 
     * @param tag : The tag to check for
     * @return Whether or not the tag is present in this instance's list of tags
     */
    public boolean hasTag(String tag){
        return Tags.indexOf(tag) != -1;
    }


    /**Returns a shallow array copy of this instance's tags
     * 
     * @return A String[] array of this instance's tags
     */
    public String[] getTags(){
        return Tags.toArray();
    }

    /**Returns whether or not Instance {@code is touching the top border of the screen}
     * 
     * @return
     */
    public boolean touchingBorderTop(){
        return (Position.Y)<0;
    }

    public boolean touchingBorderLeft(){
        return (Position.X)<0;
    }

    public boolean touchingBorderRight(){
        return (Position.X+Size.X)>Parent.getTotalScreenSize().X;
    }

    public boolean touchingBorderBottom(){
        return Position.Y+Size.Y>Parent.getTotalScreenSize().Y;
    }

    public boolean collidingRight(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(Position, Position.X+Size.X, bl, new Vector2(2, Size.Y-3));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;
    }


    public boolean collidingLeft(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(Position, Position.X-4, bl, new Vector2(2, Size.Y-3));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;
    }

    public boolean collidingBottom(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(Position, Position.Y+Size.Y, bl, new Vector2(Size.X-3, 1));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;
    }

    public boolean collidingTop(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(Position, Position.Y-3, bl, new Vector2(Size.X-3, 2));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;
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

    public boolean overlaps(Instance other){
        return Position.X < other.Position.X+ other.Size.X && Position.X + Size.X > other.Position.X && 
        Position.Y < other.Position.Y + other.Size.Y && Position.Y + Size.Y > other.Position.Y;
    }

    public Vector2 getCollideDirection(){
        Vector2 vect = new Vector2(0, 0);

        boolean bottom = this.collidingBottom();
        boolean top = this.collidingTop();
        boolean left = this.collidingLeft();
        boolean right = this.collidingRight();

        if (bottom) vect.Y = -1;

        if (top) vect.Y = 1;

        //if (top && bottom) vect.Y = 0;

        if (left) vect.X = 1;

        if (right) vect.X = -1;

        if (left && right) vect.X = 0;

        return vect;
    }

    public boolean isCoordinateInBounds(Vector2 coord){
        if (this.Position == null) return false;

        int x = coord.X;
        int y = coord.Y;

        int leftCorner = this.Position.X;
        int rightCorner = this.Position.X + this.Size.X;
        int top = this.Position.Y;
        int bottom = this.Position.Y + this.Size.Y;

        boolean inBounds = (leftCorner<x && x <rightCorner && y<bottom && y>top);

        return inBounds;
    }


    abstract public void paint(Graphics g);
    
    /**An internal method used by the physics handler to move instances, letting them handle the new position on their own
     * 
     * @param velPos : The position to move to
     */
    public void setPosition(Vector2 velPos){
        Position = velPos;
    };

    public void setInstanceVariableByName(String variable, Object value){
        if (variable == "Position") Position = (Vector2) value;
        if (variable == "Size") Position = ((Vector2)value);
        if (variable == "FillColor") FillColor = (Color) value;
        if (variable == "Name") Name = (String) value;
        if (variable == "Velocity") Velocity = (Vector2) value;
        if (variable == "Solid") Solid = (boolean) value;
        if (variable == "Anchored") Anchored = (boolean) value;
    }

    @Override
    public String toString(){
        return Name != null ? Name : "";
    }

    @Override
    public boolean equals(Object other){
        if (other==this) return true;

        if (!(other instanceof Instance) || this.Name == null) return false;

        Instance obj = (Instance) other;


        return Name.equals(obj.Name) && Position.equals(obj.Position) && Size.equals(obj.Size) && FillColor.equals(obj.FillColor);
    }
}
