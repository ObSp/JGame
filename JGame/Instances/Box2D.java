package JGame.Instances;

import java.awt.Graphics2D;

import JGame.Types.Vector2;

public class Box2D extends Instance {

    public Box2D(){
        Name = "Box2D";
    }

    
    public void render(Graphics2D g2) {
        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.fillRect(Position.X+BorderSizePixel, Position.Y+BorderSizePixel, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }

        g2.setColor(FillColor);
        g2.fillRect(Position.X, Position.Y, Size.X, Size.Y);
    }

    @Override
    public void setPosition(Vector2 pos){
        Position = pos;
    }
}
