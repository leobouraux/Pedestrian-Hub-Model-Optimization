
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {

    private static List<List<String>> csvToList(String csvName) {

        String csvLoca = "/Users/leobouraux/Desktop/EPFL/MA2/Hub-optimization/hub-simulator/tmp3/";
        BufferedReader br = null;
        String line = "";
        String separator = ",";
        ArrayList<List<String>> csvList = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvLoca+csvName));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] csvLine = line.split(separator);
                List<String> csvLineList = new ArrayList<>(Arrays.asList(csvLine));
                csvList.add(csvLineList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return csvList;
    }

    static List<Double> getTravelTimes(String csvName) {
        List<List<String>> csvfile = Utility.csvToList(csvName);
        List<Double> travelTimes = new ArrayList<>();

        for (List<String> line :csvfile) {
            travelTimes.add(Double.valueOf(line.get(2)));
        }
        return travelTimes;
    }


    public static double listSum (List<Double> list){
        if (list.size() > 0) {
            int sum = 0;
            for (Double i : list) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }


    public static double listMean (List<Double> list){
        double mean = 0;
        mean = listSum(list) / (float)(list.size());
        return mean;
    }


    public double listMedian (List<Double> list){
        int middle = list.size()/2;
        return (list.size() % 2 == 1) ? list.get(middle) :  (list.get(middle-1) + list.get(middle)) / 2.0;
    }


    public double listStd (List<Double> list){
        int standardDeviation = 0;
        double mean = listMean(list);

        for (Double i : list)
            standardDeviation += Math.pow((i - mean), 2);
        return Math.sqrt(standardDeviation / (float)list.size());
    }

}
