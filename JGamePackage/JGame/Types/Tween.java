package JGamePackage.JGame.Types;

import JGamePackage.lib.Signal;

public class Tween {
    public final Signal<Void, Void> Ended = new Signal<>();
    public final Object start;
    public final Object end;

    public Tween(Object start, Object end){
        this.start = start;
        this.end = end;
    }
}
