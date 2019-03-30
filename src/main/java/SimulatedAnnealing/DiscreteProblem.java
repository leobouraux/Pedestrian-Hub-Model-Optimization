package SimulatedAnnealing;

import SimulatedAnnealing.Others.ControlledGestionLists;

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
    public abstract ControlledGestionLists CGInit(int length);

    public abstract void printSolution(String s);

    public abstract double objectiveFunction();

    /**
     * This function is left abstract because each discrete problem is
     * different and has to be initialized in different ways.
     */
    public static ArrayList<Object> problemInit(int dim){
        return null;
    }

    /**
     * This function is left abstract because each discrete problem is different
     * and has to be transformed in different ways to find a new solution.
     */
    public abstract List<Object> transformSolutionLSA();



}
