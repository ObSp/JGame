package lib;

import java.util.Objects;
import java.util.function.Consumer;

public class Promise {
    int state;
    private ArrayTable<varargExecutorParam> andThens;
    private ArrayTable<varargExecutorParam> catches;
    private ArrayTable<varargExecutorParam> finallies;

    private Object[] callArgs;

    //UTIL FUNCS

    /**Yields the current thread until the given promise is resolved or rejected
     * 
     * @param promise : the promise to be awaited
     */
    public static void await(Promise promise){
        while (promise.state==0){System.out.print("");}
    }


    /**Returns an immediately resolved promise
     * 
     * @return The resolved promise
     */
    public static Promise resolve(){
        return new Promise((self)-> self.resolve());
    }


    /**Resolves the given promise
     * 
     * @param resolveable - The promise to be resolved
     */
    public static void resolve(Promise resolveable){
        resolveable.state = 2;

        for (varargExecutorParam onresolve : resolveable.andThens){
            onresolve.run();
        }

        for (varargExecutorParam onfinnaly : resolveable.finallies){
            onfinnaly.run(resolveable.state==2 ? "resolved" : "rejected");
        }
    }


    /**Returns an immediately rejected promise
     * 
     * @return The rejected promise
     */
    public static Promise reject(){
        return new Promise((self)-> self.reject());
    }


    /**Rejects the given promise
     * 
     * @param resolveable - The promise to be rejected
     */
    public static void reject(Promise resolveable){
        resolveable.state = 1;

        for (varargExecutorParam onreject : resolveable.catches){
            onreject.run();
        }

        for (varargExecutorParam onfinnaly : resolveable.finallies){
            onfinnaly.run(resolveable.state==2 ? "resolved" : "rejected");
        }
    }


    /**Constructs a new promise, running the optional "executor" Consumer that can be used to resolve the promise
     * 
     * @param executor : A function used to resolve or reject the promise
     */
    public Promise(Consumer<Executor> executor){
        andThens = new ArrayTable<>();
        catches = new ArrayTable<>();
        finallies = new ArrayTable<>();

        callArgs = null;

        if (!Objects.isNull(executor)) new Thread(()-> executor.accept(new Executor(this))).start();
    }

    /**Adds the function to a list of functions that will be run when the promise is resolved. 
     * If the promise is already resolved, the onresolve function will be immediately executed.
     * 
     * @param onresolve : The function to be run on the resolve of the promise
     * @return A new promise
     */
    public Promise andThen(varargExecutorParam onresolve){
        if (state==2) {onresolve.run(callArgs); return new Promise(null); }

        andThens.add(onresolve);
        return new Promise(null); 
    }


    /**Adds the function to a list of functions that will be run when the promise is rejected. 
     * If the promise is already rejected, the onreject function will be immediately executed.
     * 
     * @param onreject : The function to be run on the resolve of the promise
     * @return A new promise
     */
    public Promise onReject(varargExecutorParam onreject){
        if (state==1) {onreject.run(callArgs); return new Promise(null); }

        catches.add(onreject);
        return new Promise(null); 
    }

    /**Adds the function to a list of functions that will be run when the promise the promise is either `resolved or rejected`. 
     * If the promise isn't active anymore, it will imeediately run the function.
     * 
     * @param onreject : The function to be run on the resolve of the promise
     * @return A new promise
     */
    public Promise after(varargExecutorParam onfinnaly){
        if (state==1 | state==2) {
            onfinnaly.run(state==2 ? "resolved" : "rejected", callArgs); 
            return new Promise(null); 
        }

        finallies.add(onfinnaly);
        return new Promise(null); 
    }


    public class Executor{

        private Promise self;

        private void callFinallies(){
            for (varargExecutorParam onfinnaly : self.finallies){
                onfinnaly.run(self.state==2 ? "resolved" : "rejected", callArgs);
            }
        }

        public Executor(Promise prom){
            self = prom;
            self.state = 0;
        }
        
        public void resolve(Object ...args){
            self.callArgs = args;
            self.state = 2;

            for (varargExecutorParam onresolve : self.andThens){
                onresolve.run(callArgs);
            }
            callFinallies();
        }

        public void reject(Object ...args){
            self.callArgs = args;
            self.state = 1;

            for (varargExecutorParam onreject : self.catches){
                onreject.run(callArgs);
            }
            callFinallies();
        }

    }

    @FunctionalInterface
    public interface varargExecutorParam{
        void run(Object ...items);
    }
}

