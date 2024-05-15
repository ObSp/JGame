package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Tween;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;

public class TweenService extends Service {

    //--UTIL FUNCTIONS--//

    private double lerp(double a, double b, double t){
        return -(a + (a-b)*t);
    }
    
    public TweenService(JGame parent){
        super(parent);
    }

    public Tween TweenVector2(Instance obj, String propertyName, Vector2 goal, TweenInfo tweenInfo){
        Object p = obj.getInstanceVariableByName(propertyName);
        if (p==null || !(p instanceof Vector2))
            throw new Error("Unable to tween non Vector2 property "+propertyName+" of Instance "+obj);
        
        Vector2 property = (Vector2) p;

        if (tweenInfo==null){
            tweenInfo = new TweenInfo();
        }

        Vector2 start = property.clone();

        Tween tween = new Tween(start, goal.clone());

        for (double t = 0.0; t <=1; t += (tweenInfo.Speed)*0.01){

            property.X = (int) lerp(start.X, goal.X, t);
            property.Y = (int) lerp(start.Y, goal.Y, t);
            Parent.waitForTick();
        }

        property.X = goal.X;
        property.Y = goal.Y;

        return tween;
    }

    public Tween TweenIntegerProperty(Instance obj, String propertyName, Integer goal, TweenInfo tweenInfo){
        Object p = obj.getInstanceVariableByName(propertyName);
        if (p==null || !(p instanceof Integer))
            throw new Error("Unable to tween non Integer property "+propertyName+" of Instance "+obj);

        if (tweenInfo==null){
            tweenInfo = new TweenInfo();
        }

        Integer property = (Integer) p;

        Tween tween = new Tween(property, goal);

        for (double t = 0.0; t <= 1.0; t += (tweenInfo.Speed)*.01){
            obj.setInstanceVariableByName(propertyName, lerp(property, goal, t));
            Parent.waitForTick();
        }

        obj.setInstanceVariableByName(propertyName, goal);

        return tween;
    }

    public Tween TweenDoubleProperty(Instance obj, String propertyName, Double goal, TweenInfo tweenInfo){
        Object p = obj.getInstanceVariableByName(propertyName);
        if (p==null || !(p instanceof Double))
            throw new Error("Unable to tween non Double property "+propertyName+" of Instance "+obj);

        if (tweenInfo==null){
            tweenInfo = new TweenInfo();
        }

        Double property = (Double) p;

        Tween tween = new Tween(property, goal);

        for (double t = 0.0; t <= 1.0; t += tweenInfo.Speed*.01){
            obj.setInstanceVariableByName(propertyName, lerp(property, goal, t));
            Parent.waitForTick();
        }

        obj.setInstanceVariableByName(propertyName, goal);

        return tween;
    }

}
