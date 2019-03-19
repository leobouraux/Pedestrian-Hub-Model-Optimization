import SimulatedAnnealing.*;
import SimulatedAnnealing.Others.Utils;
import SimulatedAnnealing.TravelingSalesmanProblem.City;
import SimulatedAnnealing.TravelingSalesmanProblem.TourManager;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/src/main/java/SimulatedAnnealing/GraphData/";

        /**  TSP  */
        /*ArrayList<City> tour = TourManager.problemInit();
        SAProblemsAbstractFactory factory = new TSPFactory(tour);
        String title = path+"LSA_TSP.txt";
        optimizationLSA(10000, 0.003, 1, new ArrayList<>(tour), factory, title);//*/


        /**  MinFunction  */
        ArrayList<Double> range = MinFunction.problemInit();
        SAProblemsAbstractFactory factory = new MinFunctionFactory(range);
        String title = path+"LSA_MinFunction.txt";
        //initial temp + final temp = small when typical increase of the objective function is small, and the objective function results are small
        optimizationLSA(10, 0.005, 0.001, new ArrayList<>(range), factory, title);//*/
    }

    //TODO: faire une fonction qui modifie au lieu de créer des instances à chque fois !

    public static void optimizationLSA(double temperature, double coolingRate, double final_temp,
                                    ArrayList<Object> objects, SAProblemsAbstractFactory factory, String title) {
        long startTime = System.nanoTime();
        Utils.dataToTxt(title, "BEST y, CURR y, TEMP, ACCEPT PB, ACC-BEST Sol(TT/TF/FF)", false);

        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(objects);
        currentSolution.initialSolution();

        currentSolution.printSolution("The initial solution: ");

        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        SAProblem bestSolution = factory.createSAProblem(currentSolution.getList());

        int loop_nb =0;
        String isAcceptedBest;

        // Loop until system has cooled
        while (temperature > final_temp) {
            //Create the neighbour tour
            SAProblem newSolution = factory.createSAProblem(currentSolution.getList());
            newSolution = newSolution.transformSolution();
            isAcceptedBest = "FF";


            // Get energy of both solutions
            double currentObjective = currentSolution.objectiveFunction();
            double neighbourObjective = newSolution.objectiveFunction();


            // Decide if we should accept the neighbour (not only when it's better)
            double rand = Utils.randomProba();
            double acceptanceProba = Utils.acceptanceProbability(currentObjective, neighbourObjective, temperature);
            if (acceptanceProba > rand) {
                currentSolution = factory.createSAProblem(newSolution.getList());
                isAcceptedBest="TF";
            }

            // Keep track of the best solution found
            if (currentSolution.objectiveFunction() < bestSolution.objectiveFunction()) {
                bestSolution = factory.createSAProblem(currentSolution.getList());
                isAcceptedBest="TT";
            }

            // Cool system
            temperature *= (1 - coolingRate);
            loop_nb++;

            //best x, best y, current x, current y, temperature, acceptance proba, accepted solutions?-new best solution?(TT/TF/FF), temps de convergence
            currentSolution.writeData(title, temperature, currentSolution.objectiveFunction(),
                    bestSolution.objectiveFunction(), isAcceptedBest, acceptanceProba);
        }

        long endTime   = System.nanoTime();
        String data = "Runtime: " + String.valueOf((endTime - startTime)/Math.pow(10, 9)) + " sec";
        data += "\n" + "Nbr itérations: " + loop_nb;
        Utils.dataToTxt(title, data, true);

        bestSolution.printSolution("The best solution: ");
    }


}
