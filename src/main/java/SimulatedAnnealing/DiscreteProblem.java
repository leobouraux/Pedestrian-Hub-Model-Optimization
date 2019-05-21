package SimulatedAnnealing;

import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscreteProblem extends SAProblem {



    private List<Object> X;

    private static int dim;

    // Initialisation

    public DiscreteProblem(List<Object> param) {
        this.X = param;
    }

    // Getters
    /**
     *
     * @return dimension of the problem
     */
    public int getDimension(){
        return dim;
    }

    /**
     *
     * @return the value of current Xi for each axis
     */
    public ArrayList<Object> getXs() {
        return new ArrayList<>(X);
    }


    // Algorithms



    // Abstract functions

    /**
     * This function is left abstract because it must use the objective
     * function which is problem defined so defined in a subclass.
     */

    public abstract void printSolution(String s, double currObjective);

    public abstract double objectiveFunction();



    /**
     * This function is left abstract because each discrete problem is different
     * and has to be transformed in different ways to find a new solution.
     */
    public abstract List<Object> transformSolutionLSA();


    static void optimizationLSA(double temperature, double coolingRate, final double final_temp,
                                ArrayList<Object> parameters, SAProblemsAbstractFactory factory, String title) {
        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(parameters);
        double currentObjective = ((DiscreteProblem)currentSolution).objectiveFunction();
        currentSolution.printSolution("The initial solution: ", currentObjective);

        //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, BEST y, CURR y
        String names = "              ACCEPT PB|  ACC-BEST|            TEMPERATURE|                 BEST y|                 CURR y|";
        names += Helper.getNamesForXi(currentSolution.getDimension(), currentSolution);
        long startTime = SAProblem.Helper.TXT_Titles(title, names, false);


        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        SAProblem bestSolution = currentSolution;
        double bestObjective = currentObjective;

        int loop_nb =0;
        String isAcceptedBest = "TT";
        double acceptanceProba = 1.0;

        ((DiscreteProblem)currentSolution).writeDataLSA(title, acceptanceProba, isAcceptedBest, temperature, bestObjective, currentObjective, bestSolution, currentSolution);


        // Loop until system has cooled
        while (temperature > final_temp) {
            //Create the neighbour tour
            SAProblem newSolution = factory.createSAProblem(currentSolution.transformSolutionLSA());

            isAcceptedBest = "FF";


            // Get energy of both solutions
            double neighbourObjective = ((DiscreteProblem)newSolution).objectiveFunction();


            // Decide if we should accept the neighbour (not only when it's better)
            double rand = Utils.randomProba();
            acceptanceProba = Utils.acceptanceProbability(currentObjective, neighbourObjective, temperature);
            if (acceptanceProba > rand) {
                currentSolution = newSolution;
                currentObjective = neighbourObjective;
                isAcceptedBest="TF";
            }

            // Keep track of the best solution found
            if (((DiscreteProblem)currentSolution).objectiveFunction() < ((DiscreteProblem)bestSolution).objectiveFunction()) {
                bestSolution = currentSolution;
                bestObjective = currentObjective;
                isAcceptedBest="TT";
            }

            // Cool system
            temperature *= (1 - coolingRate);
            loop_nb++;

            //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, BEST y, CURR y
            ((DiscreteProblem)currentSolution).writeDataLSA(title, acceptanceProba, isAcceptedBest, temperature, bestObjective, currentObjective, bestSolution, currentSolution);

        }

        Helper.TXT_End_Print(title, startTime, bestSolution, bestObjective, loop_nb, "Nbr itérations: ");
    }


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



}
