package JGamePackage.JGame.Types;

import JGamePackage.lib.BiSignal;

public class Tween {
    public final BiSignal<Void, Void> Ended = new BiSignal<>();
    public final Object start;
    public final Object end;

    public Tween(Object start, Object end){
        this.start = start;
        this.end = end;
    }
}
