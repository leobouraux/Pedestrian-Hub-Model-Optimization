package SimulatedAnnealing;

import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SAProblem {

    public abstract int getDimension();

    public abstract List<Object> getXs();

    public abstract void printSolution(String s, double currObjective);

    public abstract List<Object> transformSolutionLSA();


    static class Helper {
        static long TXT_Titles(String title, String s2, boolean append) {
            long startTime = System.nanoTime();
            Utils.dataToTxt(title, s2, append);
            return startTime;
        }

        static void TXT_End_Print(String title, long startTime, SAProblem bestSolution, double bestObjective, int loop_nb, String s2) {
            long endTime = System.nanoTime();
            String data = "Runtime: " + (endTime - startTime) / Math.pow(10, 9) + " sec";
            data += "\n" + s2 + loop_nb;
            Utils.dataToTxt(title, data, true);
            bestSolution.printSolution("The best solution: ", bestObjective);
        }

        static String getNamesForXi(int dimension, SAProblem problem) {
            String names = "";
            if(problem instanceof DiscreteProblem) {
                return names;
            }
            names += "                 BEST x|                 CURR x|";
            for (int i = 1; i < dimension +1; i++) {
                if(dimension == 1){
                    return names;
                }
                String str_i = String.valueOf(i+1);
                names += "                BEST x"+str_i+"|                CURR x"+str_i+"|";
            }
            return names;
        }

        static String getDataForXi(SAProblem bestXi_here, SAProblem currXi_here) {
            String data = "";
            if(currXi_here instanceof DiscreteProblem) {
                return data;
            }

            //BESTxi, CURRxi
            List<Double> bestXs = bestXi_here.getXs().stream()
                    .map(object -> (Double) object)
                    .collect(Collectors.toList());
            List<Double> currXs = currXi_here.getXs().stream()
                    .map(object -> (Double) object)
                    .collect(Collectors.toList());

            for (int i = 0; i < bestXs.size() ; i++) {
                data += Utils.format(bestXs.get(i), 23);
                data += Utils.format(currXs.get(i), 23);
            }
            return data;
        }

    }

}
