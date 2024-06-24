package JGamePackage.lib;


public class LerpFuncs {

    public static double Lerp(double a, double b, double t){
        return a + (b - a) * t;
    }

    private static double computeEase(double t){
        return t*t;
    }

    private static double computeFlip(double t){
        return 1-t;
    }

    public static double EaseIn(double a, double b,double t){
        return Lerp(a, b, computeEase(t));
    }

    public static double EaseOut(double a, double b,double t){
        return computeFlip(EaseIn(a, b, t));
    }
    public static void main(String[] args) {
        System.out.println(EaseOut(0, 1, 1));
    }

}