import SimulatedAnnealing.Others.Utils;

public class MainTest {


    public static void main(String[] args) throws Exception {
        int p = 1;
        int q = 2;
        int r = 5;
        r = tryPrimitives(p, q);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("r = " + r);
    }


    public static int tryPrimitives(int x, int y) {
        x = x + 10;
        y = y + 10;
        return x;
    }



}