import SimulatedAnnealing.Others.Utils;
import org.apache.commons.beanutils.converters.DoubleConverter;

import javax.xml.bind.SchemaOutputResolver;
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
        double n = 5.100765244038015E-59;
        String m = "3937506";
        double nn = 0.5929150533937506;

        int length = 20;

        System.out.println(Utils.format(nn, length));
        System.out.println(Utils.format(m, length));



    }




}