package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.RaycastResult;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {

        Box2D box = new Box2D();
        box.CFrame.Position = new Vector2(300);
        box.FillColor = Color.black;
        box.AnchorPoint = new Vector2(100);

        Box2D c = new Box2D();
        c.CFrame.Position = box.GetCornerPosition(Enum.InstanceCornerType.TopRight);
        c.AnchorPoint = new Vector2(50);
        c.Size = new Vector2(15);
        c.FillColor = Color.red;

        game.addInstance(box);
        game.addInstance(c);
    }
}
