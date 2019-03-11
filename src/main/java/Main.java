import SimulatedAnnealing.*;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        /**  TSP  */
        /*ArrayList<City> tour = TourManager.problemInit();
        SAProblemsAbstractFactory factory = new TSPFactory(tour);
        optimization(10000, 0.003, new ArrayList<>(tour), factory);
        */

        /**  MinFunction  */
        ArrayList<Double> range = MinFunction.problemInit();
        SAProblemsAbstractFactory factory = new MinFunctionFactory(range);
        optimization(10000, 0.003, new ArrayList<>(range), factory);
    }


    public static void optimization(double temperature, double coolingRate,
                                    ArrayList<Object> objects, SAProblemsAbstractFactory factory) {

        //Set initial temp
        double temp = temperature;
        //Cooling rate
        double coolRate = coolingRate;

        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(objects);
        currentSolution.initialSolution();

        currentSolution.printSolution("The initial solution: ");

        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        SAProblem bestSolution = factory.createSAProblem(currentSolution.getList());

        // Loop until system has cooled
        while (temp > 1) {
            //Create the neighbour tour
            SAProblem newSolution = factory.createSAProblem(currentSolution.getList());
            newSolution = newSolution.transformSolution();

            // Get energy of both solutions
            double currentObjective = currentSolution.objectiveFunction();
            double neighbourObjective = newSolution.objectiveFunction();

            // Decide if we should accept the neighbour
            double rand = Utils.randomDouble();
            if (Utils.acceptanceProbability(currentObjective, neighbourObjective, temp) > rand) {
                currentSolution = factory.createSAProblem(newSolution.getList());
            }

            // Keep track of the best solution found
            if (currentSolution.objectiveFunction() < bestSolution.objectiveFunction()) {
                bestSolution = factory.createSAProblem(currentSolution.getList());
            }

            // Cool system
            temp *= (1 - coolRate);
        }

        bestSolution.printSolution("The best solution: ");


    }


}
