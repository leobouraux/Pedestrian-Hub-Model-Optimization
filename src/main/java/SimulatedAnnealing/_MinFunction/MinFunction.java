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
        double newX;
        for (int i = 0; i < length; i++) {
            newX = super.getRandomXi(super.getStarts().get(0), super.getEnds().get(0));
            MinFunction pb = new MinFunction(Collections.singletonList(newX));
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


    @Override
    public double objectiveFunction() {
        return getObjectiveFunction((double) super.getXs().get(0));

    }


    private double getObjectiveFunction(Double x) {
        //easy one
        //return Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;

        //hard one
        return Math.log(0.1*Math.sin(30*x)+0.01*Math.pow(x, 4)-0.1*Math.pow(x, 2) + 1)+1;
    }




}
