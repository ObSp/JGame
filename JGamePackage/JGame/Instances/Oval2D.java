package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Vector2;

public class Oval2D extends Instance {

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D ) g;

        Vector2 anchorOffset = getAnchorPointOffset();

        g2.setColor(FillColor);
        g2.fillOval(CFrame.Position.X-anchorOffset.X, CFrame.Position.Y-anchorOffset.Y, Size.X, Size.Y);
    }
    
}
