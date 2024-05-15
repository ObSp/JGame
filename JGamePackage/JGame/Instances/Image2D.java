package JGamePackage.JGame.Instances;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Types.Vector2;

import java.awt.*;

public class Image2D extends Instance{
    /**The path to the Image's file location. Note that changing this doesn't affect anything, to 
     * chane the Image please call the SetImagePath function.
     * 
     */
    public String ImagePath = "JGamePackage\\JGame\\Files\\IMAGEDEFAULT.png";
    public boolean BackgroundTransparent = true;
    private BufferedImage img;

    public Image2D(){
        Name = "Image";
    }


    @Override
    public void paint(Graphics g) {
        if (img == null) return;
        
        Graphics2D g2 = (Graphics2D) g;

        if (!BackgroundTransparent){
            g2.setColor(this.FillColor);
            g2.fillRect(Position.X, Position.Y, Size.X, Size.Y);
        }

        g2.drawImage(img, Position.X-(Size.X/2), Position.Y-(Size.Y/2), Size.X, Size.Y, null);
    }

    public void SetImagePath(String path){
        try {
            this.img = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Updates the image path to this file to the value of the {@code ImagePath} instance variable
     * 
     */
    public void UpdateImagePath(){
        try {
            this.img = ImageIO.read(new File(ImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void setPosition(Vector2 velpos){
        Position = velpos;
    }
    
}
