package JGamePackage.JGame.Instances;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Types.Vector2;

import java.awt.*;

public class Image2D extends Instance{
    public String ImagePath = "C:\\Users\\Paul W\\Documents\\GitHub\\JGame\\JGame\\Files\\IMAGEDEFAULT.png";
    public Image2D(){
        Name = "Image";
    }


    @Override
    public void paint(Graphics g) {
        if (ImagePath==null) return;

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(ImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (img == null) return;
        
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(img, Position.X, (Parent.getScreenHeight()-Position.Y)-Size.Y, Size.X, Size.Y, null);
    }

    
    @Override
    public void setPosition(Vector2 velpos){
        Position = velpos;
    }
    
}
