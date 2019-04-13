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

        //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, BEST y, CURR y
        String names = "              ACCEPT PB|  ACC-BEST|            TEMPERATURE|                 BEST y|                 CURR y|";
        names += Helper.getNamesForXi(title, currentSolution.getDimension(), currentSolution);
        long startTime = SAProblem.Helper.TXT_Titles(title, names);


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

            //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, BEST y, CURR y
            currentSolution.writeDataLSA(title, acceptanceProba, isAcceptedBest, temperature, bestObjective, currentObjective, bestSolution, currentSolution);

        }

        Helper.TXT_End_Print(title, startTime, bestSolution, bestObjective, loop_nb, "Nbr itérations: ");
    }

    //DataVisualization functions for both Continuous and Discrete problems

    private void writeDataLSA(String title, double acceptanceProba, String isAccepted, double temp, double bestSolution, double currentSolution,
        SAProblem bestXi_here, SAProblem currXi_here) {
        //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, BEST y, CURR y
        String data = "";
        data += Utils.format(acceptanceProba, 23);
        data += Utils.format(isAccepted, 10);
        data += Utils.format(temp, 23);
        data += Utils.format(bestSolution, 23);
        data += Utils.format(currentSolution, 23);
        data += Helper.getDataForXi(bestXi_here, currXi_here);

        Utils.dataToTxt(title, data, true);
    }


    static class Helper {
        static long TXT_Titles(String title, String s2) {
            long startTime = System.nanoTime();
            Utils.dataToTxt(title, s2, false);
            return startTime;
        }
        static void TXT_End_Print(String title, long startTime, SAProblem bestSolution, double bestObjective, int loop_nb, String s2) {
            long endTime = System.nanoTime();
            String data = "Runtime: " + (endTime - startTime) / Math.pow(10, 9) + " sec";
            data += "\n" + s2 + loop_nb;
            Utils.dataToTxt(title, data, true);
            bestSolution.printSolution("The best solution: ", bestObjective);
        }

        static String getNamesForXi(String title, int dimension, SAProblem problem) {
            String names = "";
            if(problem instanceof DiscreteProblem) {
                return names;
            }
            names += "                 BEST x|                 CURR x|";
            for (int i = 1; i < dimension+1; i++) {
                if(dimension == i){
                    Utils.dataToTxt(title, names, true);
                    break;
                }
                String str_i = String.valueOf(i+1);
                names += "                 BEST x"+str_i+"|                CURR x"+str_i+"|";
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
