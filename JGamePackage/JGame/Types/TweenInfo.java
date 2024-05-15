package JGamePackage.JGame.Types;

public class TweenInfo {
    public final double Speed;
    public final int Repeats;

    public TweenInfo(double tweenSpeed, int reps){
        if (tweenSpeed == 0){
            this.Speed = .00001;
            this.Repeats = reps;
            return;
        }
        this.Speed = tweenSpeed;
        this.Repeats = reps;
    }

    public TweenInfo(){
        this.Speed = 1;
        this.Repeats = 0;
    }
}
