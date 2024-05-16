package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Vector2;

public class Box2D extends Instance {

    public Box2D(){
        Name = "Box2D";
    }

    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;

        Vector2 Position = CFrame.Position;
        double Rotation = CFrame.Rotation;


        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.fillRect(Position.X+BorderSizePixel, Position.Y+BorderSizePixel, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }
        
        g2.rotate(Rotation, Position.X, Position.Y);

        //calculating offset
        int xOffset = getAnchorPointOffsetX();
        int yOffset = getAnchorPointOffsetY();

        g2.setColor(FillColor);
        g2.fillRect(Position.X-xOffset, Position.Y-yOffset, Size.X, Size.Y);
    }

    @Override
    public void setPosition(Vector2 pos){
        CFrame.Position = pos;
    }
}
