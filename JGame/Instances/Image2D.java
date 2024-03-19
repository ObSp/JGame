package JGame.Instances;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import JGame.Types.Vector2;

public class Image2D extends Instance{
    public String ImagePath;

    public Image2D(){
        Name = "Image";
    }


    @Override
    public void render(Graphics2D g) {
        if (ImagePath==null) return;

        ImageIcon image = new ImageIcon(ImagePath);

        Parent.getContentPane().add(new JLabel(image));
    }

    
    @Override
    public void setPosition(Vector2 velpos){
        Position = velpos;
    }
    
}
