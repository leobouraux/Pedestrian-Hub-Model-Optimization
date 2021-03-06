package SimulatedAnnealing.Others;

import java.io.*;
import java.util.*;

public class Utils {


    /**
     * @param folder : folder in which we have pictures
     * @return list of names of all the pictures contained in the folder
     * @throws NullPointerException if no files in Folder ?
     */
    public static ArrayList<String> listFilenames(final File folder) throws NullPointerException {
        ArrayList<String> files_names = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (final File fileEntry : files) {
                if (fileEntry.isDirectory()) {
                    listFilenames(fileEntry);
                } else {
                    files_names.add(fileEntry.getName());
                }
            }
        }
        return files_names;
    }

    public static double listSum(List<Double> list) {
        if (list.size() > 0) {
            int sum = 0;
            for (Double i : list) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static double listMean(List<Double> list) {
        double mean = 0;
        mean = listSum(list) / (float) (list.size());
        return mean;
    }

    public double listMedian(List<Double> list) {
        int middle = list.size() / 2;
        return (list.size() % 2 == 1) ? list.get(middle) : (list.get(middle - 1) + list.get(middle)) / 2.0;
    }

    public static double listStd(List<Double> list) {
        int standardDeviation = 0;
        double mean = listMean(list);

        for (Double i : list)
            standardDeviation += Math.pow((i - mean), 2);
        return Math.sqrt(standardDeviation / (float) list.size());
    }

    /**
     * Calculates the acceptance probability
     *
     * @param currentDistance the total distance of the current tour
     * @param newDistance     the total distance of the new tour
     * @param temperature     the current temperature
     * @return value the probability of whether to accept the new tour
     */
    public static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        // If the new solution is better, accept it
        if (newDistance < currentDistance) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp(-(newDistance - currentDistance) / temperature);
    }


    /**
     * this method returns a random number n such that
     * 0.0 <= n <= 1.0
     *
     * @return random such that 0.0 <= random <= 1.0
     */
    public static double randomProba() {
        return randomDouble(0,1);
    }

    /**
     * returns a random int value within a given range
     * min inclusive .. max not inclusive
     *
     * @param min the minimum value of the required range (int)
     * @param max the maximum value of the required range (int)
     * @return rand a random int value between min and max [min,max)
     */
    public static int randomInt(int min, int max) {
        return (int) (min + randomProba() * Math.abs(max - min));
    }

    public static double randomDouble(double min, double max) {
        if(min>max) {
            double temp = min;
            min = max;
            max = temp;
        } else if(min==max) {
            return min;
        }
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;

    }

    public static void dataToTxt(String title, String data, boolean append){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(title);

            writer = new BufferedWriter(new FileWriter(logFile, append));
            writer.write(data+"\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    public static StringBuilder format(Object o, int length) {
        String str =  String.valueOf(o);
        int lengthStr = str.length();
        StringBuilder toRet = new StringBuilder();
        for (int i = 0; i < length-lengthStr; i++) {
            toRet.append(" ");
        }
        toRet.append(str);
        toRet.append("|");
        return toRet;
    }

}
