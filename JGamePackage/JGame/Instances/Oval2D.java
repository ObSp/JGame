package JGamePackage.JGame.Instances;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.Vector2;

public class Oval2D extends Instance {

    public boolean HasOutline = false;
    public Color OutlineColor = Color.white;

    public boolean BackgroundTransparent = false;

    @Override
    public void paint(Graphics g) {
        Vector2 actualPos = GetRenderPosition();
        
        if (!Parent.Camera.areBoundsInViewport(this, actualPos) || opacity == 0.0)
            return;

        Graphics2D g2 = (Graphics2D) g;
        int rotationX = (int) (actualPos.X + (RotationAnchorPoint.X/100.0)*Size.X);
        int rotationY = (int) (actualPos.Y + (RotationAnchorPoint.Y/100.0)*Size.Y);

        AffineTransform previous = g2.getTransform();
        AffineTransform rotated = new AffineTransform();
        rotated.rotate(CFrame.Rotation, rotationX, rotationY);

        if (HasOutline){
            g2.setColor(BorderColor);
            g2.drawOval((int) actualPos.X, (int) actualPos.Y, (int) Size.X, (int) Size.Y);
        }

        if (!BackgroundTransparent){
            g2.setColor(FillColor);
            g2.fillOval((int) actualPos.X, (int) actualPos.Y, (int) Size.X, (int) Size.Y);
        }

        g2.setTransform(previous);
    }

    @Override
    public Oval2D clone(){
        return null;
    }


    @Override
    public boolean isCoordinateInBounds(Vector2 coord){
        if (this.CFrame == null) return false;

        if (coord.equals(CFrame.Position)) return true;

        /*Get the distance between the circle's center and the coord */
        Vector2 center = this.GetCenterPosition();

        double centerMag = center.Magnitude();
        double coordMag = coord.Magnitude();
        
                        /*First get top, then subtract center.Y from top.Y*/
        double bottomY = this.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).Y;
        double radius = bottomY-center.Y;

        double diff = Math.abs(centerMag - coordMag);

        return diff <= radius;
    }
    
}
