package SimulatedAnnealing._MinFunction;


import SimulatedAnnealing.ContinuousProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;

import java.util.*;

public class MinFunction extends ContinuousProblem {


    public MinFunction(List<Double> param) {
        super(param);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {
        ArrayList<ContinuousProblem> X = new ArrayList<>(length);
        ArrayList<Double> Y = new ArrayList<>(length);
        ArrayList<Double> newX = new ArrayList<>(0);
        int dimension = getDimension();
        for (int i = 0; i < length; i++) {
            newX.clear();
            for (int d = 0; d < dimension; d++) {
                newX.add(super.getRandomXi(super.getStarts().get(d), super.getEnds().get(d)));
            }
            MinFunction pb = new MinFunction(newX);
            X.add(pb);
            Y.add(getObjectiveFunction(newX));
        }
        ControlledGestionLists.reorderCGs(X, Y);
        return new ControlledGestionLists(X,Y);
    }




    @Override
    public void printSolution(String s) {
        System.out.println(s);
        System.out.println("For x = " + super.getXs().get(0) + ", y = " + this.objectiveFunction());
    }


    public double getObjectiveFunction(ArrayList<Double> X) {
        //easy one
        //return Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;

        //hard one
        double x = X.get(0);
        return Math.log(0.1*Math.sin(30*x)+0.01*Math.pow(x, 4)-0.1*Math.pow(x, 2) + 1)+1;
    }




}
