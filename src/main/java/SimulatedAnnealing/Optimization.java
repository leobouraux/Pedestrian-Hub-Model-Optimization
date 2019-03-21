package SimulatedAnnealing;

import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;

public class Optimization {
    //TODO: faire une fonction qui modifie au lieu de créer des instances à chque fois !

    public static void optimizationLSA(double temperature, double coolingRate, final double final_temp,
                                       ArrayList<Object> objects, SAProblemsAbstractFactory factory, String title) {
        long startTime = System.nanoTime();
        Utils.dataToTxt(title, "BEST y, CURR y, TEMP, ACCEPT PB, ACC-BEST Sol(TT/TF/FF)", false);

        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(objects);

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
            newSolution = newSolution.transformSolutionLSA();
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


    /**
     * @param temperature : initial temperature
     * @param final_CG_density : objective indicator of the density of the CGList
     * @param final_temp : final temperature
     * @param n : dimension of the problem
     * @param objects : list that defines the problem
     * @param factory : type of problem used
     * @param title : title for data.txt file
     */
    public static void optimizationDSA(double temperature, final double final_CG_density, double final_temp, int n,
                                       ArrayList<Object> objects, SAProblemsAbstractFactory factory, String title) {
        long startTime = System.nanoTime();
        //Overwrite the previous .txt file
        Utils.dataToTxt(title, "BEST y, CURR y, TEMP, ACCEPT PB, ACC-BEST Sol(TT/TF/FF)", false);

        //Create a random initial problem
        int CGListLength = 5*(n+1); //7*(n+1)   10*(n+1)
        SAProblem currentSolution = factory.createSAProblem(objects);

        //Initialize list for CG
        ArrayList<Double> CGList = currentSolution.CGInit(CGListLength);
        currentSolution.printSolution("The initial solution: ");

        //Keep track if the best solution
        SAProblem bestSolution = factory.createSAProblem(currentSolution.getList());

        //Outer loop parameters
        int loop_nb =0; //t
        String isAcceptedBest = "FF";
        boolean stopCriterion = false;

        //Cooling temp param
        double factor = 0.85, factorMin = 0.8, factorMax = 0.9;
        int prevIterInner = -1;

        //Loop until system has cooled
        while (!stopCriterion) {
            //Markov chain param
            boolean check = false;
            int iterInner = 0, Lt = 10*n, maxIterInner = Lt;

            while (!check) {
                //Create the neighbour solution
                SAProblem newSolution = factory.createSAProblem(currentSolution.getList());
                newSolution = newSolution.transformSolutionDSA(CGList, n);

                //Get energy of new solution and worst solution of CGList
                double worstCGObjective = CGList.get(CGListLength-1);
                double neighbourObjective = newSolution.objectiveFunction();

                //Decide if we should accept the neighbour (not only when it's better)
                double rand = Utils.randomProba();
                double acceptanceProba = Utils.acceptanceProbability(worstCGObjective, neighbourObjective, temperature);

                //Solution is accepted
                if (acceptanceProba > rand) {
                    CGList.set(CGListLength-1, neighbourObjective);
                    currentSolution = factory.createSAProblem(newSolution.getList());
                    isAcceptedBest="TF";
                }

                //Update inner loop param
                iterInner++;
                check = (iterInner>=maxIterInner);

                //Keep track of the best solution found (=CGList[0])
                if (neighbourObjective < bestSolution.objectiveFunction()) {
                    bestSolution = factory.createSAProblem(currentSolution.getList());
                    isAcceptedBest="TT";
                    check = true;
                    //Control param CASE 1
                    factor = factorMax;
                }
                Utils.reorderCG(CGList);

                //Compute length of the markov chains
                double fh = CGList.get(CGListLength-1);
                double fl = CGList.get(0);
                double F = 1-Math.exp(-(fh-fl));
                maxIterInner = Lt + (int)(Lt*F);
            }

            //New best sol has not been found in A && at least 2nd outer loop
            if(iterInner >= maxIterInner && prevIterInner != -1){
                //Control param CASE 2
                if(prevIterInner < maxIterInner) {
                    factor = factor - (factor - factorMin)*(1 - prevIterInner/(double)maxIterInner);
                }
                else {
                    factor = factorMax - (factorMax - factor)*maxIterInner/(double)prevIterInner;
                }
            }

            prevIterInner = iterInner;

            //Stopping criterion
            double CG_density = Math.abs(1 - CGList.get(0)/CGList.get(CGListLength-1));
            stopCriterion = temperature <= final_temp && CG_density <= final_CG_density;

            //Cool system
            temperature *= factor;
            loop_nb++;

            //TODO new writeData
            //best x, best y, current x, current y, temperature, acceptance proba, accepted solutions?-new best solution?(TT/TF/FF), temps de convergence
            //currentSolution.writeData(title, temperature, currentSolution.objectiveFunction(),
            //        bestSolution.objectiveFunction(), isAcceptedBest, acceptanceProba);
        }

        long endTime   = System.nanoTime();
        String data = "Runtime: " + String.valueOf((endTime - startTime)/Math.pow(10, 9)) + " sec";
        data += "\n" + "Nbr d'outer loop: " + loop_nb;
        Utils.dataToTxt(title, data, true);

        bestSolution.printSolution("The best solution: ");
    }//*/

}
