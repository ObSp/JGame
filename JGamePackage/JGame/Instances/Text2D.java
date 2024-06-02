package JGamePackage.JGame.Instances;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import JGamePackage.JGame.Types.Vector2;

public class Text2D extends Instance {

    public Color TextColor = Color.black;

    public String Text = null;

    public Font Font;

    public boolean BackgroundTransparent = true;

    public double HorizontalOffsetPercentage = .5;

    public double VerticalOffsetPercentage = .5;

    @Override
    public void paint(Graphics g) {
        if (Text == null || Text.equals("")) return;

        Vector2 actualPos = GetRenderPosition();
        
        if (!Parent.Camera.areBoundsInViewport(this, actualPos) || transparency == 0.0)
            return;

        Graphics2D g2 = (Graphics2D) g;
        int centerX = actualPos.X+(Size.X/2);
        int centerY = actualPos.Y+(Size.Y/2);

        AffineTransform previous = g2.getTransform();
        AffineTransform rotated = new AffineTransform();
        rotated.rotate(CFrame.Rotation, centerX, centerY);

        g2.transform(rotated);

        if (Font != null)
            g2.setFont(Font);

        if (!BackgroundTransparent){
            g2.setColor(FillColor);
            g2.fillRect(actualPos.X, actualPos.Y, Size.X, Size.Y);
        }

        FontMetrics fm = g2.getFontMetrics();

        int pixelHeight = (int) Math.round((double) Font.getSize()*.75);

        int xStringPos = (int)(centerX-fm.stringWidth(Text)*(HorizontalOffsetPercentage));
        int yStringPos = (int)((actualPos.Y+Size.Y)-(pixelHeight*VerticalOffsetPercentage));

        g2.setColor(TextColor);
        g2.drawString(Text, xStringPos, yStringPos);


        g2.setTransform(previous);
    }

    @Override
    public Text2D clone(){
        return null;
    }

    
}