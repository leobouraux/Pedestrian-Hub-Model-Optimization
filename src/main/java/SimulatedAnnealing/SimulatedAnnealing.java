package SimulatedAnnealing;

import java.util.ArrayList;
import SimulatedAnnealing.TSP.*;

public class SimulatedAnnealing {


    public static void main(String[] args) {
        // Create and add our cities
        ArrayList<City> tour = TourManager.problemInit();
        SAProblemsAbstractFactory factory = new TSPFactory(tour);

        optimization(10000, 0.003, tour, factory);
    }







   public static void optimization(double temperature, double coolingRate, ArrayList<City> tour, SAProblemsAbstractFactory factory) {

       //Set initial temp
       double temp = temperature;
       //Cooling rate
       double coolRate = coolingRate;

       //Create a random initial tour
       SAProblem currentSolution = factory.createSAProblem(new ArrayList<>(tour));
       currentSolution.initialSolution();

       currentSolution.printSolution("Total distance of initial solution: ");

       // We would like to keep track if the best solution
       // Assume best solution is the current solution
       SAProblem bestSolution = factory.createSAProblem(currentSolution.getList());

       // Loop until system has cooled
       while (temp > 1) {
           //Create the neighbour tour
           SAProblem newSolution = factory.createSAProblem(currentSolution.getList());
           newSolution = newSolution.transformSolution();

           // Get energy of both solutions
           int currentObjective = currentSolution.objectiveFunction();
           int neighbourObjective = newSolution.objectiveFunction();

           // Decide if we should accept the neighbour
           double rand = TSPUtility.randomDouble();
           if (TSPUtility.acceptanceProbability(currentObjective, neighbourObjective, temp) > rand) {
               currentSolution = factory.createSAProblem(newSolution.getList());
           }

           // Keep track of the best solution found
           if (currentSolution.objectiveFunction() < bestSolution.objectiveFunction()) {
               bestSolution = factory.createSAProblem(currentSolution.getList());
           }

           // Cool system
           temp *= (1 - coolRate);
       }

       bestSolution.printSolution("Total distance of best solution: ");


   }


}
