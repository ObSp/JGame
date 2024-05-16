package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.Enum;
import JGamePackage.JGame.Types.RaycastParams;
import JGamePackage.JGame.Types.RaycastResult;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;

public class PhysicsTesting {
    static JGame game = new JGame();



    public static void main(String[] args) {


        Box2D b = new Box2D();
        b.CFrame.Position = new Vector2(700);
        b.AnchorPoint = new Vector2(100);
        b.FillColor = Color.red;
        game.addInstance(b);
    }
}
