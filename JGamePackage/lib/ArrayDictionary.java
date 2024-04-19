package JGamePackage.lib;

import java.util.Iterator;
import java.util.function.BiConsumer;

/**A type of data structure that allows you to store values in the {@code Key = Value} format.
 * 
 * 
 */
public class ArrayDictionary<T extends Object, U extends Object> implements Iterator<Object>, Iterable<Object> {

    //INSTANCE VARIABLES
    private ArrayTable<KeyValuePair> SELF_DICT_LIST;

    //ITERATION IMPLEMENTATION
    int ci = 0;
    

    //CONSTRUCTORS
    public ArrayDictionary(){
        SELF_DICT_LIST  = new ArrayTable<>();
    }


    private int INDEX_OF(Object key){
        for (int i = 0; i < SELF_DICT_LIST.getLength(); i++) {
            if (SELF_DICT_LIST.get(i).Key.equals(key)) return i;
        }

        throw new Error("Key "+key+" not found in dictionary");
    }



    //INSTANCE METHODS


    public void put(T key, U value){
        KeyValuePair pair = new KeyValuePair(key, value);
        int indexOfItem = SELF_DICT_LIST.indexOf(pair);
        if (indexOfItem>-1){
            SELF_DICT_LIST.set(indexOfItem, pair);
            return;
        }

        SELF_DICT_LIST.add(pair);
    }


    public U get(T key){
        return SELF_DICT_LIST.get(INDEX_OF(key)).Value;
    }


    public void remove(Object key){
        SELF_DICT_LIST.remove(INDEX_OF(key));
    }

    public int getLenght(){
        return SELF_DICT_LIST.getLength();
    }

    //INDEX SUPPORT
    public Object getKeyFromIndex(int i){
        return SELF_DICT_LIST.get(i).Key;
    }



    //CUSTOM FOR LOOP
    @SuppressWarnings("unchecked")
    public void loop(BiConsumer<T,U> innerloop){
        for (Object key : this){
            innerloop.accept((T) key, get((T) key));
        }
    }



    //OVERRIDES


    @Override
    public String toString(){
        String returnstring = "{";


        for (KeyValuePair item : SELF_DICT_LIST){
            returnstring += item+", ";
        }
        if (returnstring.length()>2) returnstring = returnstring.substring(0, returnstring.length() - 2);

        returnstring+="}";

        return returnstring;
    }



    //ITERATION METHODS


    @Override
    public boolean hasNext() {
        if (ci < SELF_DICT_LIST.getLength()){
            return true;
        }
        ci = 0;
        return false;
    }



    @Override
    public Object next() {
        return SELF_DICT_LIST.get(ci++).Key;
    }

    public Iterator<Object> iterator(){
        return this;
    }

    



    //INTERFACES
    @FunctionalInterface
    public interface loopLambda{
        void run(Object key, Object value);

        default loopLambda andThen(){
            
            return (k,v) -> {
                run(k, v);  
            };
        }
    }




    //INNER CLASSES
    /**
     * InnerArrayDictionary
     */
    private class KeyValuePair {
        public T Key;
        public U Value;
        public KeyValuePair(T k, U v){
            Key = k;
            Value = v;
        }       
        
        
        @Override
        public String toString(){
            return Key+" = "+Value;
        }


        @Override
        public boolean equals(Object other){
            @SuppressWarnings("unchecked")
            KeyValuePair otherKV = (KeyValuePair) other;
            return otherKV.Key.equals(Key);
        }
    } 
}