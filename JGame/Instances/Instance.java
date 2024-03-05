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


    abstract public void render(Graphics g);
}
