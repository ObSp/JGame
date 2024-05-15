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
            g2.fillRect(CFrame.Position.X, CFrame.Position.Y, Size.X, Size.Y);
        }

        Vector2 anchorOffset = getAnchorPointOffset();

        g2.drawImage(img, CFrame.Position.X-anchorOffset.X, CFrame.Position.Y-anchorOffset.Y, Size.X, Size.Y, null);
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
        CFrame.Position = velpos;
    }
    
}
