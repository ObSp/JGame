package JGamePackage.JGame.Instances;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import JGamePackage.JGame.Types.Vector2;

@Deprecated
public class Text2D extends Instance {
    public boolean BackgroundTransparent = true;

    private JLabel label = new JLabel();

    private Color TextColor = Color.black;

    private Text2D(){
        label.setForeground(TextColor);
    }


    public void SetText(String text){
        if (label == null)
            label = new JLabel();

        label.setText(text);
    }

    public void SetTextColor(Color c){
        label.setForeground(c);
    }
    
    public void SetTextSize(){

    }

    

    @Override
    public void paint(Graphics g){
        if (label.getText().equals("")) return;

        Graphics2D g2 = (Graphics2D) g;

        Vector2 pos = GetRenderPosition();
        if (!BackgroundTransparent){
            g2.setColor(FillColor);
            g2.fillRect(pos.X, pos.Y, Size.X, Size.Y);
        }
    }
}
