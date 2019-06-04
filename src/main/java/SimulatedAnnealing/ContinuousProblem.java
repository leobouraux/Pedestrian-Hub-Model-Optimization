package SimulatedAnnealing;

import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ContinuousProblem extends SAProblem {

    private List<Double> X;

    private static final double FROM_CG = 1.0;
    private static final double FROM_U = 0.0;

    private static final int BASIC_NB_OBJ_ITER = 30;
    private static final int MIN_NB_OBJ_ITER = 20;
    private static final int MAX_NB_OBJ_ITER = 80;
    private static final int MAX_NB_SUP_ITER = 60;


    private static int dim;
    private static ArrayList<Double> starts;
    private static ArrayList<Double> ends;
    private static ArrayList<Double>  intervalsForLocalSearch = new ArrayList<>(dim);


    // Initialisation

    public ContinuousProblem(List<Double> param) {
        this.X = param;
    }

    /**
     *
     * initialize all continuous pb variables : @dim, @starts, @ends, @intervalsForLocalSearch
     *
     * @param dimension : dimension of the continuous problem
     * @param startRanges : start of the range for each axis (size = dimension)
     * @param endRanges : end of the range for each axis (size = dimension)
     * @return initial values for each axis
     */
    public static ArrayList<Double> problemInit(int dimension, List<Double> startRanges, List<Double> endRanges) {
        if(startRanges.size() != dimension || endRanges.size() != dimension) {
            System.err.println("Parameter dimension should be equal to the size of both lists");
        }
        ContinuousProblem.dim = dimension;
        ContinuousProblem.starts = new ArrayList<>(startRanges);
        ContinuousProblem.ends = new ArrayList<>(endRanges);

        ArrayList<Double> initialValues = new ArrayList<>();
        double Xi;
        for (int i = 0; i < dimension; i++) {
            double start = startRanges.get(i);
            double end = endRanges.get(i);
            Xi = Utils.randomDouble(start, end);
            initialValues.add(Xi);
            //initialize intervalFor
            ContinuousProblem.intervalsForLocalSearch.add((end-start)/20.0);
        }

        return initialValues;
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

    /**
     *
     * @param i : the axis
     * @return the current value of axis i
     */
    private double getXi(int i) {
        return X.get(i);
    }

    /**
     *
     * @param start : first possible value
     * @param end : last possible value
     * @return a random double in [start, end]
     */
    private double getRandomXi(double start, double end) {
        return Utils.randomDouble(start, end);
    }

    /**
     *
     * @return start of range for each axis (size = dim)
     */
    private ArrayList<Double> getStarts() {
        return starts;
    }

    /**
     *
     * @return end of range for each axis (size = dim)
     */
    private ArrayList<Double> getEnds() {
        return ends;
    }



    // Algorithms

    public double objectiveFunction(int nb_iter, boolean newSimulation) {
        List<Double> d = getXs().stream().map(object -> (double) object).collect(Collectors.toList());
        return getObjectiveFunction(new ArrayList<>(d),nb_iter,newSimulation);
    }



    private static double[] getSupIterNb(double sol, double bestSol, double typicalIncrease, double xMax){
        double x = (sol-bestSol)/typicalIncrease;
        if(x>xMax) xMax = x;
        int supIter;

        //System.out.print("x = " + x + "     ");
        if(x<0)
            supIter = MAX_NB_OBJ_ITER;
        else if(typicalIncrease == Double.NEGATIVE_INFINITY)
            supIter = BASIC_NB_OBJ_ITER;
        else {

            //supIter = (int) ((MAX_NB_SUP_ITER) * Math.exp(-5/xMax * x));        // interpolation exponentielle
            supIter = (int) (-MAX_NB_SUP_ITER/xMax* x + (MAX_NB_SUP_ITER));     //  interpolation linéaire
        }
        //System.out.print("supIter = " + supIter+ "       ");
        //System.out.println("xMax = " + xMax);
        double[] tab =  {supIter, xMax};

        return tab;
    }

    /**
     *
     * @param i : the axis
     * @param X : the value around which we search nextXi
     * @return next value of axis i that shoud be next added to nextX
     */
    private double localSearchAroundX(int i, double X) {
        double nextXi;
        double min = Math.max(starts.get(i), X - intervalsForLocalSearch.get(i));
        double max = Math.min(X + intervalsForLocalSearch.get(i), ends.get(i));
        do{
            nextXi = Utils.randomDouble(min, max);
        } while(getXi(i) == nextXi);
        return nextXi;
    }

    /**
     *
     * Fills next values for each axis in nextX directly
     * @param nextX : next values for each
     */
    private void uniformDistribution(ArrayList<Double> nextX) {
        double nextXi;
        for (int i = 0; i < dim ; i++) {
            do {
                nextXi = getRandomXi(ends.get(i), starts.get(i));
            } while (getXi(i) == nextXi);
            nextX.add(nextXi);
        }
    }

    private ControlledGestionLists CGInit(int length) {
        ArrayList<ContinuousProblem> X = new ArrayList<>(length);
        ArrayList<Double> Y = new ArrayList<>(length);
        int dimension = getDimension();
        for (int i = 0; i < length; i++) {
            ArrayList<Double> newX = new ArrayList<>(0);
            for (int d = 0; d < dimension; d++) {
                newX.add(getRandomXi(getStarts().get(d), getEnds().get(d)));
            }
            ContinuousProblem pb = pbWithGoodType(newX);
            X.add(pb);
            Y.add(getObjectiveFunction(newX, BASIC_NB_OBJ_ITER, true));
        }
        ControlledGestionLists.reorderCGs(X, Y);
        return new ControlledGestionLists(X,Y);
    }


    /**
     *
     * @param CGListXs : the CGList for each axis
     * @param i : the axis
     * @return nextXi that should be next added to nextX (uses the local search if CG is stuck)
     */
    private double controlledGeneration(ArrayList<ContinuousProblem> CGListXs, int i) {
        boolean check, stuck = false;
        int counter = 0;
        double nextXi;
        do {
            nextXi = getNextXiForCG(CGListXs, i);

            check = nextXi >= starts.get(i) && nextXi <= ends.get(i);
            //to keep stuck not infinitely
            if(counter>=5*(dim+1)) {
                check = true;
                stuck = true;
            }
            counter++;
        } while(!check);

        // When we are stuck in CGList (occurs when 2 mini locals are near of the range border).
        // Example for n = 1 --> nextX = 2 * xMin - xMax => OUT OF RANGE
        // Local best search
        if(stuck) {
            double bestXi = CGListXs.get(0).getXi(i);
            nextXi = localSearchAroundX(i, bestXi);
        }
        return nextXi;
    }

    /**
     *
     * @param CGListXs : the CGList for each axis
     * @param i : the axis
     * @return nextXi that shoud be next added to nextX if it's not stuck
     */
    private double getNextXiForCG(ArrayList<ContinuousProblem> CGListXs, int i) {
        //CGListXs is equivalent to an ArrayList<ArrayList<Double>>

        //get n random elements in CGList
        List<ContinuousProblem> CGcopy = new ArrayList<>(CGListXs);
        CGcopy.remove(0);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, dim+1);

        //compute nextX
        double G = CGListXs.get(0).getXi(i);
        for (int j = 0; j < dim ; j++) {
            ContinuousProblem pb = CGcopy.get(j);
            G+= pb.getXi(i);
        }
        G = G / ((double) dim);

        return 2 * G -  CGcopy.get(dim-1).getXi(i);
    }

    /**
     *
     * @return next values for each axis (size = dim)
     */
    public List<Object> transformSolutionLSA() {
        double w = Utils.randomProba();

        ArrayList<Double> nextX = new ArrayList<>(dim);
        if(w<0.1) {
            //Uniform distribution
            uniformDistribution(nextX);
        }
        else {
            //Local search
            for (int i = 0; i < dim ; i++) {
                double nextXi = localSearchAroundX(i, getXi(i));
                nextX.add(nextXi);
            }

        }
        return new ArrayList<>(nextX);
    }

    /**
     *
     * @return next values for each axis (size = dim)
     */
    private List<Object> transformSolutionDSA(ArrayList<ContinuousProblem> CGListXs) {
        double w = Utils.randomProba();
        ArrayList<Double> nextXs = new ArrayList<>(dim);
        if(w<0.75) {
            //Uniform distribution
            //uniformDistribution(nextXs);
            List<Object> o = transformSolutionLSA();
            List<Double> d = o.stream()
                    .map(object -> (Double) object)
                    .collect(Collectors.toList());
            nextXs = new ArrayList<>(d);
            //in order to now if we use controlled generation or not. it will be deleted on optimizationDSA function
            nextXs.add(FROM_U);
        }
        else {
            //Controlled generation
            for (int i = 0; i < dim; i++) {
                double nextXi = controlledGeneration(CGListXs, i);
                nextXs.add(nextXi);
            }
            nextXs.add(FROM_CG);
        }
        return new ArrayList<>(nextXs);
    }

    /**
     * @param temperature
     * @param final_temp : final temperature
     * @param parameters : list that defines the problem
     * @param factory : type of problem used
     * @param title : title for data.txt file
     * @param runMultipleTimes
     * @param final_CG_density : objective indicator of the density of the CGList
     */
    public static void optimizationDSA(double temperature, double final_temp, ArrayList<Object> parameters, SAProblemsAbstractFactory factory, String title, boolean runMultipleTimes, final double final_CG_density) {
        double initTemp = temperature;
        int CGListLength = 5*(dim+1);//*/ 7*(dim+1);   /*10*(n+1);*/
        ContinuousProblem currentSolution = (ContinuousProblem) factory.createSAProblem(parameters);
        double currentObjective = currentSolution.objectiveFunction(BASIC_NB_OBJ_ITER, true);

        long startTime = 0;

        if(!runMultipleTimes) {
            currentSolution.printSolution("The initial solution: ", currentObjective);
            //Overwrite the previous .txt file ?
            String names = "              ACCEPT PB|Control-G?|            TEMPERATURE|                DENSITY|ACTUAL#MARKOV|                 BEST y|                 CURR y|";
            names += SAProblem.Helper.getNamesForXi(dim, currentSolution);
            System.out.println(dim);
            startTime = SAProblem.Helper.TXT_Titles(title, names, runMultipleTimes);
        }

        //Initialize list for CG
        ControlledGestionLists CGs = currentSolution.CGInit(CGListLength);
        ArrayList<ContinuousProblem> CGListX = CGs.getX();
        ArrayList<Double> CGListY = CGs.getY();
        ControlledGestionLists.reorderCGs(CGListX, CGListY);
        double CG_density = 1;
        double xMax = 0;

        //Outer loop parameters
        int loop_nb =0; //t
        boolean stopCriterion = false;

        //Inner loop param
        double comeFromCG = FROM_U;
        double factor = 0.85, factorMin = 0.8, factorMax = 0.9;
        int prevIterInner = -1;
        double acceptanceProba = 1.0;
        int iterInner = 1, Lt = 10*dim, maxIterInner =Lt;

        //Keep track if the best solution
        ContinuousProblem bestSolution = (currentObjective < CGListY.get(0)) ? currentSolution : CGListX.get(0);

        //write data for initial solution
        if(!runMultipleTimes) {
            currentSolution.writeDataDSA(title, acceptanceProba, comeFromCG, temperature, CG_density, iterInner,
                    CGListY.get(0), currentObjective, bestSolution, currentSolution);
        }

        //Loop until system has cooled
        while (!stopCriterion) {
            //Markov chain param
            boolean check = false;
            iterInner = 0;

            while (!check && !stopCriterion) {

                //Create the neighbour solution
                ArrayList<Object> transformedSolution = new ArrayList<>(currentSolution.transformSolutionDSA(CGListX));
                comeFromCG = transformedSolution.remove(transformedSolution.size()-1).equals(FROM_CG) ? FROM_CG : FROM_U;
                ContinuousProblem newSolution = (ContinuousProblem) factory.createSAProblem(transformedSolution);

                //Get energy of new solution and worst solution of CGListX
                double worstCGObjective = CGListY.get(CGListLength-1);
                double neighbourObjective = newSolution.objectiveFunction(MIN_NB_OBJ_ITER, true);

                double[] tab = getSupIterNb(neighbourObjective, CGListY.get(0), -initTemp*Math.log(0.99), xMax);
                int sup_iter = (int)tab[0];
                xMax = tab[1];

                neighbourObjective = newSolution.objectiveFunction(sup_iter, false);


                //Decide if we should accept the neighbour (not only when it's better)
                double rand = Utils.randomProba();
                acceptanceProba = Utils.acceptanceProbability(worstCGObjective, neighbourObjective, temperature);

                //Solution is accepted
                if (acceptanceProba > rand) {
                    CGListX.set(CGListLength-1, newSolution);
                    CGListY.set(CGListLength-1, neighbourObjective);
                    currentSolution = newSolution;
                    currentObjective = neighbourObjective;
                }

                //Update inner loop param
                iterInner++;
                check = (iterInner>=maxIterInner);



                //Keep track of the best solution found (=CGListX[0])
                if (neighbourObjective < CGListY.get(0)) {
                    bestSolution = currentSolution;
                    check = true;
                    //Control param CASE 1
                    factor = factorMax;     ///---> zone intéressante, pq slow cooling ? on a encore des nouveaux a découvrir prudence !
                }

                /*System.out.println("CGListY = " + CGListY);
                for (ContinuousProblem pb:CGListX) {
                    System.out.println("X"+pb.X);
                }*/

                ControlledGestionLists.reorderCGs(CGListX, CGListY);



                //Compute length of the markov chains
                double fh = CGListY.get(CGListLength-1);
                double fl = CGListY.get(0);
                double F = 1-Math.exp(fl-fh);
                maxIterInner = Lt + (int)(Lt*F);


                //Stopping criterion
                CG_density = CGListY.get(CGListLength-1)-CGListY.get(0); //Math.abs(1 - CGListY.get(0)/CGListY.get(CGListLength-1));
                stopCriterion = temperature <= final_temp && CG_density <= final_CG_density;


                if(!runMultipleTimes) {
                    //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, DENSITY, MARKOV LENGTH, BEST y, CURR y, BESTxi, CURRxi
                    currentSolution.writeDataDSA(title, acceptanceProba, comeFromCG, temperature, CG_density, iterInner,
                            CGListY.get(0), currentObjective, bestSolution, currentSolution);
                }
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

            //Cool system
            temperature *= factor;
            loop_nb++;
        }
        if(!runMultipleTimes) {
            SAProblem.Helper.TXT_End_Print(title, startTime, bestSolution, CGListY.get(0), loop_nb, "Nbr d'outer loop: ");
        }
        else {
            bestSolution.writeDataDSA(title, acceptanceProba, comeFromCG, temperature, CG_density, iterInner,
                    CGListY.get(0), currentObjective, bestSolution, currentSolution);
        }

    }


    /**
     * @param m0 : number of iteration until end
     * @param parameters : list that defines the problem
     * @param factory : type of problem used
     */
    public static double findInitTemp(int m0, ArrayList<Object> parameters, SAProblemsAbstractFactory factory) {
        //Create a random initial problem
        SAProblem currentSolution = factory.createSAProblem(parameters);
        double currentObjective = ((ContinuousProblem) currentSolution).objectiveFunction(BASIC_NB_OBJ_ITER, true);

        int m2 = 0;
        double sum_delta_xy_pos = 0;

        for (int i = 0; i < m0; i++) {
            SAProblem newSolution = factory.createSAProblem(currentSolution.transformSolutionLSA());
            // Get energy of both solutions
            double neighbourObjective = ((ContinuousProblem) newSolution).objectiveFunction(BASIC_NB_OBJ_ITER, true);

            double delta_xy = neighbourObjective - currentObjective;
            if (delta_xy > 0) {
                m2++;
                sum_delta_xy_pos += delta_xy;
            }
            currentSolution = newSolution;
            currentObjective = neighbourObjective;
        }
        double typical_increase = sum_delta_xy_pos / (double) m2;
        return - typical_increase/Math.log(0.99);
    }



    // Abstract functions


    /**
     * This function is left abstract because it must use the objective
     * function which is problem defined so defined in a subclass.
     */
    public abstract ContinuousProblem pbWithGoodType(ArrayList<Double> newX);

    public abstract void printSolution(String s, double currObjective);

    public abstract double getObjectiveFunction(ArrayList<Double> x, int nb_iter, boolean newSimulation);



    //DataVisualization functions for Continuous problems

    private void writeDataDSA(String title, double acceptanceProba, double comeFromCG, double temp, double density, int markovLen,
                              double bestSolution, double currentSolution, SAProblem bestXi_here, SAProblem currXi_here) {
        //ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, DENSITY, MARKOV LENGTH, BEST y, CURR y, BESTxi, CURRxi
        String data = "";
        data += Utils.format(acceptanceProba, 23);
        data += Utils.format(comeFromCG, 10);
        data += Utils.format(temp, 23);
        data += Utils.format(density, 23);
        data += Utils.format(markovLen, 13);
        data += Utils.format(bestSolution, 23);
        data += Utils.format(currentSolution, 23);

        //BESTxi, CURRxi
        data += SAProblem.Helper.getDataForXi(bestXi_here, currXi_here);

        Utils.dataToTxt(title, data, true);
    }
}
