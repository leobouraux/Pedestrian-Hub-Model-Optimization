package SimulatedAnnealing;

import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;
import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;

public class Optimization {
    //TODO: faire une fonction qui modifie au lieu de créer des instances à chque fois !

    public static void optimizationLSA(double temperature, double coolingRate, final double final_temp,
                                       ArrayList<Object> parameters, SAProblemsAbstractFactory factory, String title) {
        //Create a random initial tour
        SAProblem currentSolution = factory.createSAProblem(parameters);
        currentSolution.printSolution("The initial solution: ");

        long startTime = dataVisualizationGeneral1(title, "                 BEST y|                 CURR y|              ACCEPT PB|  ACC-BEST|            TEMPERATURE|");
        if(currentSolution instanceof MinFunction){ String s = title.replace("LSA_MinFunction", "LSA_MF_currX"); Utils.dataToTxt(s, "                 BEST x|                 CURR x|", false); }


        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        SAProblem bestSolution = factory.createSAProblem(currentSolution.getParams());

        int loop_nb =0;
        String isAcceptedBest;

        // Loop until system has cooled
        while (temperature > final_temp) {
            //Create the neighbour tour
            SAProblem newSolution = factory.createSAProblem(currentSolution.getParams());
            newSolution = newSolution.transformSolutionLSA();
            isAcceptedBest = "FF";


            // Get energy of both solutions
            double currentObjective = currentSolution.objectiveFunction();
            double neighbourObjective = newSolution.objectiveFunction();


            // Decide if we should accept the neighbour (not only when it's better)
            double rand = Utils.randomProba();
            double acceptanceProba = Utils.acceptanceProbability(currentObjective, neighbourObjective, temperature);
            if (acceptanceProba > rand) {
                currentSolution = factory.createSAProblem(newSolution.getParams());
                isAcceptedBest="TF";
            }

            // Keep track of the best solution found
            if (currentSolution.objectiveFunction() < bestSolution.objectiveFunction()) {
                bestSolution = factory.createSAProblem(currentSolution.getParams());
                isAcceptedBest="TT";
            }

            // Cool system
            temperature *= (1 - coolingRate);
            loop_nb++;

            //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
            currentSolution.writeDataLSA(title, bestSolution.objectiveFunction(), currentSolution.objectiveFunction(), acceptanceProba, isAcceptedBest, temperature
            );
        }

        dataVisualizationGeneral2(title, startTime, bestSolution, loop_nb, "Nbr itérations: ");
    }


    /**
     * @param temperature : initial temperature
     * @param final_CG_density : objective indicator of the density of the CGList
     * @param final_temp : final temperature
     * @param n : dimension of the problem
     * @param parameters : list that defines the problem
     * @param factory : type of problem used
     * @param title : title for data.txt file
     */
    public static void optimizationDSA(double temperature, final double final_CG_density, double final_temp, int n,
                                       ArrayList<Object> parameters, SAProblemsAbstractFactory factory, String title) {
        //Create a random initial problem
        int CGListLength = /*5*(n+1);*/ 7*(n+1);   /*10*(n+1);*/
        SAProblem currentSolution = factory.createSAProblem(parameters);

        //Overwrite the previous .txt file
        long startTime = dataVisualizationGeneral1(title, "                 BEST y|                 CURR y|              ACCEPT PB|  ACC-BEST|            TEMPERATURE|                DENSITY|ACTUAL#MARKOV|");
        if(currentSolution instanceof MinFunction){ String s = title.replace("DSA_MinFunction", "DSA_MF_currX"); Utils.dataToTxt(s, "                 BEST x|                 CURR x|", false); }

        //Initialize list for CG
        ControlledGestionLists CGs = currentSolution.CGInit(CGListLength);
        ArrayList<SAProblem> CGListX = CGs.getX();
        ArrayList<Double> CGListY = CGs.getY();
        ControlledGestionLists.reorderCGs(CGListX, CGListY);
        currentSolution.printSolution("The initial solution: ");

        //Keep track if the best solution
        SAProblem bestSolution = factory.createSAProblem(currentSolution.getParams());

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
            int iterInner = 0, Lt = 10*n, maxIterInner =Lt;
            double acceptanceProba = 0;

            while (!check) {
                //Create the neighbour solution
                SAProblem newSolution = factory.createSAProblem(currentSolution.getParams());
                newSolution = newSolution.transformSolutionDSA(CGListX, n);

                System.out.println("currX = " + newSolution.getParams().get(0));

                //Get energy of new solution and worst solution of CGListX
                double worstCGObjective = CGListY.get(CGListLength-1);
                double neighbourObjective = newSolution.objectiveFunction();

                //Decide if we should accept the neighbour (not only when it's better)
                double rand = Utils.randomProba();
                acceptanceProba = Utils.acceptanceProbability(worstCGObjective, neighbourObjective, temperature);

                //Solution is accepted
                if (acceptanceProba > rand) {
                    CGListX.set(CGListLength-1, newSolution);
                    CGListY.set(CGListLength-1, newSolution.objectiveFunction());
                    currentSolution = factory.createSAProblem(newSolution.getParams());
                    isAcceptedBest="TF";
                }

                //Update inner loop param
                iterInner++;
                check = (iterInner>=maxIterInner);


                //Keep track of the best solution found (=CGListX[0])
                if (neighbourObjective < bestSolution.objectiveFunction()) {
                    bestSolution = factory.createSAProblem(currentSolution.getParams());
                    isAcceptedBest="TT";
                    check = true;
                    //Control param CASE 1
                    factor = factorMax;
                }

                ControlledGestionLists.reorderCGs(CGListX, CGListY);


                //Compute length of the markov chains
                double fh = CGListY.get(CGListLength-1);
                double fl = CGListY.get(0);
                double F = 1-Math.exp(fl-fh);
                maxIterInner = Lt + (int)(Lt*F);
            }
            System.out.println();



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
            double CG_density = CGListY.get(CGListLength-1)-CGListY.get(0); //Math.abs(1 - CGListY.get(0)/CGListY.get(CGListLength-1));
            System.out.println("CG_density = " + CG_density);
            System.out.println("temperature = " + temperature);
            System.out.print("CGListX = [");
            for (SAProblem pb : CGListX) {
                System.out.print(pb.getParams().get(0)+", ");
            }
            System.out.println("]");
            System.out.println("CGListY = " + CGListY);

            stopCriterion = temperature <= final_temp && CG_density <= final_CG_density;

            //Cool system
            temperature *= factor;
            loop_nb++;

            //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, DENSITY, MARKOV LENGTH
            currentSolution.writeDataDSA(title, bestSolution.objectiveFunction(), currentSolution.objectiveFunction(),
                    acceptanceProba, isAcceptedBest, temperature, CG_density, iterInner);

            if(currentSolution instanceof MinFunction){
                String s = title.replace("DSA_MinFunction", "DSA_MF_currX");
                ((MinFunction) currentSolution).writeDataCurrX(s, ((MinFunction) bestSolution).getX(),((MinFunction) currentSolution).getX());
            }

        }

        dataVisualizationGeneral2(title, startTime, bestSolution, loop_nb, "Nbr d'outer loop: ");
    }//*/


    private static long dataVisualizationGeneral1(String title, String s2) {
        long startTime = System.nanoTime();
        Utils.dataToTxt(title, s2, false);
        return startTime;
    }
    private static void dataVisualizationGeneral2(String title, long startTime, SAProblem bestSolution, int loop_nb, String s2) {
        long endTime = System.nanoTime();
        String data = "Runtime: " + String.valueOf((endTime - startTime) / Math.pow(10, 9)) + " sec";
        data += "\n" + s2 + loop_nb;
        Utils.dataToTxt(title, data, true);

        bestSolution.printSolution("The best solution: ");
    }

}
