package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Vector2;

public class Box2D extends Instance {

    public Box2D(){
        Name = "Box2D";
    }

    
    public void paint(Graphics g) {
        
        //if (!Parent.Camera.isInstanceInViewport(this)) return;

        Vector2 Position = CFrame.Position;
        double Rotation = CFrame.Rotation;

        Vector2 actualPos = Parent.Camera.GetInstancePositionRelativeToCameraPosition(this);
        actualPos = actualPos.subtract(getAnchorPointOffset());



        Graphics2D g2 = (Graphics2D) g;
        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.fillRect(Position.X+BorderSizePixel, Position.Y+BorderSizePixel, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }
        
        g2.rotate(Rotation, Position.X, Position.Y);

        

        g2.setColor(FillColor);
        g2.fillRect(actualPos.X, actualPos.Y, Size.X, Size.Y);
    }

    @Override
    public void setPosition(Vector2 pos){
        CFrame.Position = pos;
    }
}
