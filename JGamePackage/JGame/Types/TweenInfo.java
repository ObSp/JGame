package JGamePackage.JGame.Types;

public class TweenInfo {
    public final double StepSize;
    public final double Time;
    public TweenInfo(double stepSize, double time){
        this.StepSize = stepSize;
        this.Time = time;
    }

    public TweenInfo(){
        this.StepSize = .1;
        this.Time = 1;
    }
}
