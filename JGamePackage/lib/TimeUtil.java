package JGamePackage.lib;

public class TimeUtil {
    public static void sleep(double seconds){
        double millis = seconds*1000;
        double start = System.currentTimeMillis();
        while (System.currentTimeMillis()-start<millis){
            System.out.print("");
        }
    }
}
