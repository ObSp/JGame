package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Oval2D extends Instance {

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D ) g;

        g2.setColor(FillColor);
        g2.fillOval(Position.X-(Size.X/2), Position.Y-(Size.Y/2), Size.X, Size.Y);
    }
    
}
