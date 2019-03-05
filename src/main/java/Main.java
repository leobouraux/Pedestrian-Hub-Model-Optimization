
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String folderIn = "/Users/leobouraux/Desktop/EPFL/MA2/Hub-optimization.nosync/hub-simulator/tmp3/";

    public static void main(String[] args) {

        File f = new File(folderIn);
        ArrayList<String> file_names = Utils.listFilenames(f);

        for (String csvName:file_names) {
            List<Double> travelTimes = Utils.getTravelTimes(folderIn + csvName);
            double mean = Utils.listMean(travelTimes);
            System.out.println(mean);
        }
    }


}
