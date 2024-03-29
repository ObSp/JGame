package JGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import JGame.Instances.Instance;
import lib.ArrayTable;

public class draw extends JComponent {
    public ArrayTable<Instance> items = new ArrayTable<>();

    public draw(){
        items = new ArrayTable<>();
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i<items.getLength(); i++){
            items.get(i).render(g2d);
        }
    }
}