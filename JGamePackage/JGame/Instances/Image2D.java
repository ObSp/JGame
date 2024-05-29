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
    public String RealPath = new String(ImagePath);
    public BufferedImage Image;

    public boolean BackgroundTransparent = true;

    public boolean FlipHorizontally = false;
    public boolean FlipVertically = false;

    public Image2D(){
        Name = "Image";
    }


    @Override
    public void paint(Graphics g) {
        if (Image == null) return;

        Vector2 actualPos = GetRenderPosition();
        
        
        if (!Parent.Camera.areBoundsInViewport(this, actualPos) || transparency == 0.0)
            return;
        
        
        Graphics2D g2 = (Graphics2D) g;
        int centerX = actualPos.X+(Size.X/2);
        int centerY = actualPos.Y+(Size.Y/2);
        

        g2.rotate(CFrame.Rotation, centerX, centerY);

        if (!BackgroundTransparent){
            g2.setColor(this.FillColor);
            g2.fillRect(CFrame.Position.X, CFrame.Position.Y, Size.X, Size.Y);
        }

        g2.drawImage(Image, actualPos.X, actualPos.Y, FlipHorizontally ? -Size.X : Size.X, FlipVertically ? -Size.Y : Size.Y, null);
        g2.rotate(-CFrame.Rotation, centerX, centerY);
    }

    public void SetImagePath(String path){
        try {
            this.Image = ImageIO.read(new File(path));
            RealPath = path;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Updates the image path to this file to the value of the {@code ImagePath} instance variable
     * 
     */
    public void UpdateImagePath(){
        try {
            this.Image = ImageIO.read(new File(ImagePath));
            RealPath = ImagePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void setPosition(Vector2 velpos){
        CFrame.Position = velpos;
    }
    
}
