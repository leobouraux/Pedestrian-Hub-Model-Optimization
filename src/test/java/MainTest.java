import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MainTest {


    public static void main(String[] args) throws InterruptedException {
        double temp_init = 10000;
        double temp_final = 1;
        double coolin_rate = 0.003;
        int i = 0;
        while(temp_init>temp_final){
            temp_init*=(1-coolin_rate);
            System.out.println(i);
            i+=1;
        }

    }
}