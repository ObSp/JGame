package JGamePackage.lib;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class BiSignal<T extends Object, U extends Object> {
    
    public ArrayList<BiConnection<T,U>> _connections = new ArrayList<>();
    public ArrayList<BiConnection<T,U>> _onces = new ArrayList<>();

    /**Connects the given callback to this Signal's event and returns a {@code BiConnection} object representing it.
     * 
     * @param callback : The function to connect to this Signal's event
     * @return A new BiConnection representing it
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BiConnection<T,U> Connect(BiConsumer<T,U> callback){
        BiConnection<T,U> con = new BiConnection(callback, this);
        _connections.add(con);
        return con;
    }

    /**Connects the given callback to this Signal's event and returns a {@code BiConnection} that will be disconnected after the next time this Signal is fired.
     * 
     * @param callback : The function to connect to this Signal's event
     * @return A new BiConnection representing it
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BiConnection<T,U> Once(BiConsumer<T,U> callback){
        BiConnection<T,U> con = new BiConnection(callback, this);
        _onces.add(con);
        return con;
    }

    /**Calls all connected functions with the given arguments and disconnects all Connections connected with {@code Signal.Once()}.
     * 
     * @param arg1
     * @param arg2
     */
    public void Fire(T arg1, U arg2){
        for (BiConnection<T,U> con : _connections){
            con._call(arg1, arg2);
        }

        for (int i = _onces.size()-1; i > -1; i--){
            _onces.get(i)._call(arg1, arg2);
            _onces.get(i).Disconnect();
        }
    }


}


/**A class representing a BiConnection between a {@code Signal} and a function
 * 
 */
class BiConnection<T,U> {
    private BiConsumer<T,U> callback;
    private BiSignal<T,U> parent;

    /**A boolean representing whether or not this BiConnection is currently connected to a Signal.
     * 
     */
    public boolean Connected = true;

    /**Constructs a new {@code BiConnection} with the given callback and parent {@code Signal}.
     * 
     * @param callback
     */
    public BiConnection(BiConsumer<T,U> callback, BiSignal<T,U> parent){
        this.callback = callback;
        this.parent = parent;
    }

    public void _call(T arg1, U arg2){
        if (!Connected) {
            throw new Error("Unable to call an already disconnected BiConnection");
        }
        callback.accept(arg1, arg2);
    }

    /**Disconnects this BiConnection from the parent signal so it won't be called again
     * 
     */
    public void Disconnect(){
        if (parent._connections.contains(this)){
            parent._connections.remove(this);
        }

        if (parent._onces.contains(this)){
            parent._onces.remove(this);
        }

        Connected = false;
    }

}