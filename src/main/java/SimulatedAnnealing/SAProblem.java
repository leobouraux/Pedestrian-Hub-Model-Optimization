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

    public abstract double objectiveFunction();

    public abstract List<Object> transformSolutionLSA();



    // Optimization algorithm for both Continuous and Discrete problems

    static void optimizationLSA(double temperature, double coolingRate, final double final_temp,
                                ArrayList<Object> parameters, SAProblemsAbstractFactory factory, String title) {
        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(parameters);
        double currentObjective = currentSolution.objectiveFunction();
        currentSolution.printSolution("The initial solution: ", currentObjective);

        long startTime = Helper.dataVisuForGraphs1(title, "                 BEST y|                 CURR y|              ACCEPT PB|  ACC-BEST|            TEMPERATURE|");
        SAProblem.Helper.dataVisuForDrawing1(title, currentSolution.getDimension());


        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        SAProblem bestSolution = currentSolution;
        double bestObjective = currentObjective;

        int loop_nb =0;
        String isAcceptedBest;

        // Loop until system has cooled
        while (temperature > final_temp) {
            //Create the neighbour tour
            SAProblem newSolution = factory.createSAProblem(currentSolution.transformSolutionLSA());

            isAcceptedBest = "FF";


            // Get energy of both solutions
            double neighbourObjective = newSolution.objectiveFunction();


            // Decide if we should accept the neighbour (not only when it's better)
            double rand = Utils.randomProba();
            double acceptanceProba = Utils.acceptanceProbability(currentObjective, neighbourObjective, temperature);
            if (acceptanceProba > rand) {
                currentSolution = newSolution;
                currentObjective = neighbourObjective;
                isAcceptedBest="TF";
            }

            // Keep track of the best solution found
            if (currentSolution.objectiveFunction() < bestSolution.objectiveFunction()) {
                bestSolution = currentSolution;
                bestObjective = currentObjective;
                isAcceptedBest="TT";
            }

            // Cool system
            temperature *= (1 - coolingRate);
            loop_nb++;

            //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
            currentSolution.writeDataLSA(title, bestObjective, currentObjective,
                    acceptanceProba, isAcceptedBest, temperature);

            SAProblem.Helper.dataVisuForDrawing2(title, currentSolution, bestSolution);

        }

        Helper.dataVisuForGraphs2(title, startTime, bestSolution, bestObjective, loop_nb, "Nbr itérations: ");
    }

    //DataVisualization functions for both Continuous and Discrete problems

    private void writeDataLSA(String title, double bestSolution, double currentSolution, double acceptanceProba, String isAccepted, double temp) {
        //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
        String data = Helper.getString(bestSolution, currentSolution, acceptanceProba, isAccepted, temp);

        Utils.dataToTxt(title, data, true);
    }

    private void writeDataCurrX(String title, List<Double> bestXs, List<Double> currXs) {
        String data = "";
        for (int i = 0; i < bestXs.size() ; i++) {
            data += Utils.format(bestXs.get(i), 23);
            data += Utils.format(currXs.get(i), 23);
        }
        Utils.dataToTxt(title, data, true);
    }

    static class Helper {
        static long dataVisuForGraphs1(String title, String s2) {
            long startTime = System.nanoTime();
            Utils.dataToTxt(title, s2, false);
            return startTime;
        }
        static void dataVisuForGraphs2(String title, long startTime, SAProblem bestSolution,  double bestObjective, int loop_nb, String s2) {
            long endTime = System.nanoTime();
            String data = "Runtime: " + String.valueOf((endTime - startTime) / Math.pow(10, 9)) + " sec";
            data += "\n" + s2 + loop_nb;
            Utils.dataToTxt(title, data, true);

            bestSolution.printSolution("The best solution: ", bestObjective);
        }

        static void dataVisuForDrawing1(String title, int dimension) {
            if(dimension == 1){
                String s = title.replace(".txt", "_currX.txt");
                Utils.dataToTxt(s, "                 BEST x|                 CURR x|", false);
            }
            else if(dimension == 2){
                String s = title.replace(".txt", "_currX.txt");
                Utils.dataToTxt(s, "                BEST x1|                CURR x1|                BEST x2|                CURR x2|", false);
            }
        }
        static void dataVisuForDrawing2(String title, SAProblem currentSolution, SAProblem bestSolution) {
            if(currentSolution.getDimension() != 1 && currentSolution.getDimension() != 2) {
                return;
            }
            String s = title.replace(".txt", "_currX.txt");

            List<Double> bestXs = bestSolution.getXs().stream()
                    .map(object -> (Double) object)
                    .collect(Collectors.toList());
            List<Double> currXs = currentSolution.getXs().stream()
                    .map(object -> (Double) object)
                    .collect(Collectors.toList());
            currentSolution.writeDataCurrX(s, bestXs, currXs);
        }

        static String getString(double bestSolution, double currentSolution, double acceptanceProba, String isAccepted, double temp) {
            //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
            String data = "";
            data += Utils.format(bestSolution, 23);
            data += Utils.format(currentSolution, 23);
            data += Utils.format(acceptanceProba, 23);
            data += Utils.format(isAccepted, 10);
            data += Utils.format(temp, 23);
            return data;
        }
    }

}
