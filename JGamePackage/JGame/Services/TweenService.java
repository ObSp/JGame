package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Tween;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;
import JGamePackage.lib.Signal;

public class TweenService extends Service {

    //--UTIL FUNCTIONS--//

    private int lerp(double a, double b, double t){
        return (int) (a + (a-b)*t);
    }
    
    public TweenService(JGame parent){
        super(parent);
    }

    /**DOESNT WORK */
    public Tween TweenPosition(Instance obj, Vector2 goal, TweenInfo tweenInfo){
        if (tweenInfo==null){
            tweenInfo = new TweenInfo();
        }

        Tween tween = new Tween(obj.Position.clone(), goal.clone());

        Vector2 start = obj.Position.clone();

        for (double t = 0.0; t <=1; t += (tweenInfo.Speed)*0.01){

            obj.Position.X = -lerp(start.X, goal.X, t);
            obj.Position.Y = -lerp(start.Y, goal.Y, t);
            Parent.waitForTick();
        }

        obj.Position.X = goal.X;
        obj.Position.Y = goal.Y;

        return tween;
    }

}
