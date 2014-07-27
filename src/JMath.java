public class JMath {

    public static int max(int i, int j) {
        return i > j ? i : j;
    }

    public static int min(int i, int j) {
        return i < j ? i : j;
    }

    public static int ceil(double d) {
        return (int) Math.ceil(d);
    }

    public static long abs(long l) {
        return l < 0 ? -l : l;
    }

    public static int diff(int a, int b) {
        return Math.abs(a - b);
    }

}