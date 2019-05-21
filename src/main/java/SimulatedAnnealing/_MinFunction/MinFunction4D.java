package SimulatedAnnealing._MinFunction;

import SimulatedAnnealing.ContinuousProblem;

import java.util.ArrayList;
import java.util.List;

public class MinFunction4D extends ContinuousProblem {

    private double curr_x1 = (double) super.getXs().get(0);
    private double curr_x2 = (double) super.getXs().get(1);
    private double curr_x3 = (double) super.getXs().get(2);

    public MinFunction4D(List<Double> params){
        super(params);
    }



    public MinFunction4D pbWithGoodType(ArrayList<Double> newX) {
        return new MinFunction4D(newX);
    }

    @Override
    public void printSolution(String s, double currObjective) {
        System.out.println(s);
        System.out.println("For x1 = " + curr_x1 + ", x2 = " + curr_x2 + ", x3 = " + curr_x3 + " ---> z = " + currObjective);
    }

    public double getObjectiveFunction(ArrayList<Double> X) {
        double x = X.get(0);// +randx ;
        double y = X.get(1);// +randy ;
        double z = X.get(2);// +randz ;

        return 0.5*(Math.sin(2*x)+Math.sin(2*y)+Math.sin(2*z))+0.005*(Math.pow(x,2)+Math.pow(z,2)+Math.pow(y,2))+2;
    }


}