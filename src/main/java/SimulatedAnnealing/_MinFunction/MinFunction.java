package SimulatedAnnealing._MinFunction;


import SimulatedAnnealing.ContinuousProblem;

import java.util.*;

public class MinFunction extends ContinuousProblem {


    public MinFunction(List<Double> param) {
        super(param);
    }

    @Override
    public ContinuousProblem pbWithGoodType(ArrayList<Double> newX) {
        return new MinFunction(newX);
    }


    @Override
    public void printSolution(String s, double currObjective) {
        System.out.println(s);
        System.out.println("For x = " + super.getXs().get(0) + ", y = " + currObjective);
    }

    @Override
    public double getObjectiveFunction(ArrayList<Double> X, int nb_iter, boolean n) {
        //easy one
        //return Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;

        //hard one
        double x = X.get(0);
        return Math.log(0.1*Math.sin(30*x)+0.01*Math.pow(x, 4)-0.1*Math.pow(x, 2) + 1)+1;
    }
}
