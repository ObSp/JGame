package lib;

public class Task {
    public static void Delay(double SecondsDelay, Runnable r){
        
        new Thread(()->{
            TimeUtil.sleep(SecondsDelay);
            r.run();
        }).start();
    }
}
