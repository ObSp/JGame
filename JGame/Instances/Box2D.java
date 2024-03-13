package JGame.Instances;

import java.awt.Graphics2D;

public class Box2D extends Instance {

    public Box2D(){
        Name = "Paintable";
    }

    
    public void render(Graphics2D g2) {
        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.fillRect(Position.X+BorderSizePixel, Position.Y+BorderSizePixel, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }

        g2.setColor(FillColor);
        g2.fillRect(Position.X, Position.Y, Size.X, Size.Y);
    }


    public boolean overlaps(Box2D other){
        return Position.X < other.Position.X+ other.Size.X && Position.X + Size.X > other.Position.X && 
        Position.Y < other.Position.Y + other.Size.Y && Position.Y + Size.Y > other.Position.Y;
    }
}
