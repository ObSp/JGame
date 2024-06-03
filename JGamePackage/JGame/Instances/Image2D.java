package JGamePackage.JGame.Instances;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Types.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;

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

        Vector2 actualPos = RenderPosition != null ? RenderPosition : GetRenderPosition();
        
        
        if (!Parent.Camera.areBoundsInViewport(this, actualPos) || transparency == 0.0)
            return;
        
        
        Graphics2D g2 = (Graphics2D) g;
        int centerX = actualPos.X+(Size.X/2);
        int centerY = actualPos.Y+(Size.Y/2);

        AffineTransform previous = g2.getTransform();
        AffineTransform rotated = new AffineTransform();
        rotated.rotate(CFrame.Rotation, centerX, centerY);

        g2.transform(rotated);

        if (!BackgroundTransparent){
            g2.setColor(FillColor);
            g2.fillRect(actualPos.X, actualPos.Y, Size.X, Size.Y);
        }

        g2.drawImage(Image, actualPos.X, actualPos.Y, FlipHorizontally ? -Size.X : Size.X, FlipVertically ? -Size.Y : Size.Y, null);

        if (RenderPosition != null){
            RenderPosition = null;
        }

        g2.setTransform(previous);
    }

    public void SetImagePath(String path){
        try {
            this.Image = ImageIO.read(new File(path));
            RealPath = path;
            ImagePath = path;
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

    @Override
    public Image2D clone(){
        Image2D b = new Image2D();

        b.CFrame = this.CFrame.clone();
        b.FillColor = new Color(FillColor.getRed(), FillColor.getGreen(), FillColor.getBlue(), FillColor.getAlpha());
        b.AnchorPoint = this.AnchorPoint.clone();
        b.Size = this.Size.clone();
        b.Anchored = this.Anchored;
        b.Associate = this.Associate;
        b.MoveWithCamera = this.MoveWithCamera;
        b.Name = new String(this.Name);
        b.transparency = this.transparency;
        b.Tags = this.Tags.clone();
        b.Solid = this.Solid;
        b.WeightPercentage = this.WeightPercentage;
        b.ZIndex = this.ZIndex;
        b.SetImagePath(ImagePath);
        b.FlipHorizontally = FlipHorizontally;
        b.FlipVertically = FlipVertically;
        b.BackgroundTransparent = BackgroundTransparent;

        return b;
    }
    
}
