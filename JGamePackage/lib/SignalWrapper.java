package JGamePackage.lib;

import java.util.function.Consumer;

/**A wrapper class for the {@code Signal} class. This class is designed to be used on the developer-end,
 * giving them a more developer friendly object to work with. This class excludes the {@code Fire()} method
 * in favor of it being a one-way communication class.
 * 
 */
public class SignalWrapper<T> {
    private Signal<T> sig;

    public SignalWrapper(){
        sig = new Signal<>();
    }

    public SignalWrapper(Signal<T> signal){
        sig = signal;
    }


    public Signal<T>.Connection Connect(Consumer<T> callback){
        return sig.Connect(callback);
    }

    public Signal<T>.Connection Once(Consumer<T> callback){
        return sig.Once(callback);
    }
}
