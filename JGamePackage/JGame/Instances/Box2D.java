package JGamePackage.JGame.Instances;

import java.awt.Graphics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Vector2;

public class Box2D extends Instance {

    public Box2D(){
        Name = "Box2D";
    }

    
    public void paint(Graphics g) {

        Vector2 actualPos = GetRenderPosition();
        
        if (!Parent.Camera.isInstanceInViewport(this, actualPos))
            return;



        Graphics2D g2 = (Graphics2D) g;
        if (BorderSizePixel>0){
            g2.setColor(BorderColor);
            g2.fillRect(actualPos.X+BorderSizePixel, actualPos.Y+BorderSizePixel, Size.X+BorderSizePixel, Size.Y+BorderSizePixel);
        }
        
        //always making sure to rotate around center of the object
        g2.rotate(CFrame.Rotation, actualPos.X+(Size.X/2), actualPos.Y+(Size.X/2));

        

        g2.setColor(FillColor);
        g2.fillRect(actualPos.X, actualPos.Y, Size.X, Size.Y);
    }

    @Override
    public void setPosition(Vector2 pos){
        CFrame.Position = pos;
    }
}
