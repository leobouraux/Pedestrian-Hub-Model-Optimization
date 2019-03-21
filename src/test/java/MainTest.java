import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainTest {


    public static void main(String[] args) throws InterruptedException {
        int n = 1;
        List<Integer> CGList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> CGcopy = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, n);

        System.out.println("CGcopy = " + CGcopy);

        //compute nextX
        double G = CGList.get(0);
        System.out.println("G = " + G);

        for (int i = 0; i < n-1 ; i++) {
            G+=CGcopy.get(i);
            System.out.println("G = " + G);
        }

        G = G / ((double) n);


    }
}