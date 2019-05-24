package SimulatedAnnealing._MinFunction;

import SimulatedAnnealing.ContinuousProblem;

import java.util.ArrayList;
import java.util.List;

public class MinFunction3D extends ContinuousProblem {

    private double curr_x1 = (double) super.getXs().get(0);
    private double curr_x2 = (double) super.getXs().get(1);

    public MinFunction3D(List<Double> params){
        super(params);
    }



    public MinFunction3D pbWithGoodType(ArrayList<Double> newX) {
        return new MinFunction3D(newX);
    }

    @Override
    public void printSolution(String s, double currObjective) {
        System.out.println(s);
        System.out.println("For x = " + curr_x1 + ", y = " + curr_x2 + " ---> z = " + currObjective);
    }

    @Override
    public double getObjectiveFunction(ArrayList<Double> X, int nb_iter, boolean n) {
        double x = X.get(0);
        double y = X.get(1);

        //easy one
        //return Math.sin(-0.15*(x*x+y*y))+0.05*(Math.pow(x+Math.PI, 2)+Math.pow(y-2, 2))+2;

        //hard one
        return 0.5*(Math.sin(2*x)+Math.sin(2*y))+0.005*(Math.pow(x,2)+Math.pow(y,2))+1;    }


}