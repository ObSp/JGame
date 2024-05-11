package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Tween;
import JGamePackage.JGame.Types.TweenInfo;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.task;

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
        Tween tween = new Tween(obj.Position, goal);
        double stepSize = tweenInfo.StepSize;
        double totalTime = tweenInfo.Time;
        double timeStep = totalTime*stepSize;

        task.spawn(()->{
            for (double t = 0.0; t <= 1.0; t += stepSize){
                int newXCoord = lerp(((double)obj.Position.X), ((double)goal.X), t);
                int newYCoord = lerp(((double)obj.Position.Y), ((double)goal.Y), t);

                obj.Position.X = newXCoord;
                obj.Position.Y = -newYCoord;

                task.wait(timeStep);
            }
            synchronized (tween){
                tween.Ended.Fire(null, null);
            }
        });

        return tween;
    }

}
