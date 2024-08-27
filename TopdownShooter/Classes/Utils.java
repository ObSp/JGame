package TopdownShooter.Classes;

public class Utils {
    public static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }

    public static double random(double min, double max){
        return (Math.random()*(max-min))+min;
    }

    public static boolean randBoolean(){
        return random(0, 1.1) > .55;
    }

    public static double clamp(double n, double min, double max){
        if (n > max) return max;
        if (n < min) return min;
        return n;
    }

    /**Returns true if a random number between 0 and 1 is <= chance.
     * 
     * @param chance
     * @return
     */
    public static boolean booleanFromChance(double chance){
        return Math.random()<=chance;
    }
}
