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
    /**The size of the Instance in 2D space.
     * 
     */
    public Vector2 Size = new Vector2(100, 100);

    public CFrame CFrame = new CFrame();

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
        CFrame = null;
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
        return false;
        //return (CFrame.Position.Y)<0;
    }

    public boolean touchingBorderLeft(){
        return false;
        //return (CFrame.Position.X)<0;
    }

    public boolean touchingBorderRight(){
        return false;
        //return (CFrame.Position.X+Size.X)>Parent.getTotalScreenSize().X;
    }

    public boolean touchingBorderBottom(){
        return false;
        //return CFrame.Position.Y+Size.Y>Parent.getTotalScreenSize().Y;
    }

    public boolean collidingRight(){
        return false;
        /**Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(CFrame.Position, CFrame.Position.X+Size.X, bl, new Vector2(2, Size.Y-3));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;*/
    }


    public boolean collidingLeft(){
        return false;
        /**Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(CFrame.Position, CFrame.Position.X-4, bl, new Vector2(2, Size.Y-3));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;*/
    }

    public boolean collidingBottom(){
        return false;
        /**
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(CFrame.Position.add(new Vector2(0, Size.Y)), (CFrame.Position.Y+(Size.Y/2))+15, bl, new Vector2(Size.X-3, 1));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;*/
    }

    public boolean collidingTop(){
        return false;
        /**Instance[] bl = {this};
        RaycastResult r = Parent.RaycastY(CFrame.Position.subtract(new Vector2(0, Size.Y)), (CFrame.Position.Y-(Size.Y/2))-3, bl, new Vector2(Size.X-3, 2));

        if (r!= null && r.HitInstance.Solid && this.Solid){
            return true;
        }

        return false;*/
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
        if (other==null) return false;
        Vector2 Position = this.CFrame.Position;
        return Position.X < other.CFrame.Position.X+ other.Size.X && Position.X + Size.X > other.CFrame.Position.X && 
        Position.Y < other.CFrame.Position.Y + other.Size.Y && Position.Y + Size.Y > other.CFrame.Position.Y;
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
        return false;
        /**if (this.CFrame == null) return false;

        int x = coord.X;
        int y = coord.Y;

        int leftCorner = this.CFrame.Position.X;
        int rightCorner = this.CFrame.Position.X + this.Size.X;
        int top = this.CFrame.Position.Y;
        int bottom = this.CFrame.Position.Y + this.Size.Y;

        boolean inBounds = (leftCorner<x && x <rightCorner && y<bottom && y>top);

        return inBounds;*/
    }


    abstract public void paint(Graphics g);
    
    /**An internal method used by the physics handler to move instances, letting them handle the new position on their own
     * 
     * @param velPos : The position to move to
     */
    public void setPosition(Vector2 velPos){
        CFrame.Position = velPos;
    };

    public void setInstanceVariableByName(String variable, Object value){
        if (variable.equals("CFrame")) CFrame = (CFrame) value;
        if (variable.equals("Size")) Size = ((Vector2)value);
        if (variable.equals("FillColor")) FillColor = (Color) value;
        if (variable.equals("Name")) Name = (String) value;
        if (variable.equals("Velocity")) Velocity = (Vector2) value;
        if (variable.equals("Solid")) Solid = (boolean) value;
        if (variable.equals("Anchored")) Anchored = (boolean) value;
    }

    public Object getInstanceVariableByName(String variable){
        if (variable.equals("CFrame")) return CFrame;
        if (variable.equals("Size")) return Size;
        if (variable.equals("FillColor")) return FillColor;
        if (variable.equals("Name")) return Name;
        if (variable.equals("Velocity")) return Velocity;
        if (variable.equals("Solid")) return Solid;
        if (variable.equals("Anchored")) return Anchored;
        return null;
    }

    //--TWEENING--//
    public Tween TweenPosition(Vector2 goal, TweenInfo tweenInfo){
        return Parent.Services.TweenService.TweenVector2(this, "Position", goal, tweenInfo);
    }

    public Tween TweenSize(Vector2 goal, TweenInfo tweenInfo){
        return Parent.Services.TweenService.TweenVector2(this, "Size", goal, tweenInfo);
    }

    public Tween TweenRotation(Double goal, TweenInfo tweenInfo){
        return Parent.Services.TweenService.TweenDoubleProperty(this, "Rotation", goal, tweenInfo);
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


        return Name.equals(obj.Name) && CFrame.equals(obj.CFrame) && Size.equals(obj.Size) && FillColor.equals(obj.FillColor);
    }
}
