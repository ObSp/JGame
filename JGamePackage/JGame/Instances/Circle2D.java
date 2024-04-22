package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle2D extends Instance {

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D ) g;

        g2.setColor(FillColor);
        g2.fillOval(Position.X, Position.Y, Size.X, Size.Y);
    }
    
}
