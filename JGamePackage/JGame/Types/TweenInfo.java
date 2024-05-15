package JGamePackage.JGame.Types;

public class TweenInfo {
    public final double Speed;

    public TweenInfo(double tweenSpeed){
        if (tweenSpeed == 0){
            this.Speed = .00001;
            return;
        }
        this.Speed = tweenSpeed;
    }

    public TweenInfo(){
        this.Speed = 1;
    }
}
