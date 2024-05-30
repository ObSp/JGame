package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import JGamePackage.JGame.Types.Vector2;

public class Oval2D extends Instance {

    @Override
    public void paint(Graphics g) {
        Vector2 actualPos = GetRenderPosition();
        
        if (!Parent.Camera.areBoundsInViewport(this, actualPos) || transparency == 0.0)
            return;

        Graphics2D g2 = (Graphics2D) g;
        int centerX = actualPos.X+(Size.X/2);
        int centerY = actualPos.Y+(Size.Y/2);

        AffineTransform previous = g2.getTransform();
        AffineTransform rotated = new AffineTransform();
        rotated.rotate(CFrame.Rotation, centerX, centerY);

        g2.setColor(FillColor);
        g2.fillOval(actualPos.X, actualPos.Y, Size.X, Size.Y);

        g2.setTransform(previous);
    }
    
}
