package JGame.Instances;

import java.awt.Color;
import java.awt.Graphics;

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

    public void setParent(JGame newParent){
        Parent = newParent;
        Parent.addInstance(this);
    }


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
        RaycastResult r = Parent.RaycastX(Position, Position.X+(Size.X), bl);

        return r != null ? true : false; 
    }


    public boolean collidingLeft(){
        Instance[] bl = {this};
        RaycastResult r = Parent.RaycastX(Position, Position.X-1, bl);

        return r != null ? true : false; 
    }

    abstract public void render(Graphics g);
}
