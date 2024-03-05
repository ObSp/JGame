package JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Paintable extends Instance {

    public Paintable(){
        Name = "Paintable";
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(FillColor);
        g2.fillRect(Position.X, Position.Y, Size.X, Size.Y);
        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.drawRect(Position.X-BorderSizePixel, Position.Y+BorderSizePixel/2, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }
    }
}
