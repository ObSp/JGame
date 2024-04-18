package JGamePackage.lib;

import java.util.function.BiConsumer;

/**A wrapper class for the {@code Signal} class. This class is designed to be used on the developer-end,
 * giving them a more developer friendly object to work with. This class excludes the {@code Fire()} method
 * in favor of it being a one-way communication class.
 * 
 */
public class SignalWrapper<T,U> {
    private Signal<T,U> sig;

    public SignalWrapper(){
        sig = new Signal<>();
    }

    public SignalWrapper(Signal<T,U> signal){
        sig = signal;
    }


    public Connection<T,U> Connect(BiConsumer<T,U> callback){
        return sig.Connect(callback);
    }

    public Connection<T,U> Once(BiConsumer<T,U> callback){
        return sig.Once(callback);
    }

}
