package SimulatedAnnealing._MinFunction;

import SimulatedAnnealing.ContinuousProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MinFunction3D extends ContinuousProblem {

    private double curr_x1 = (double) super.getXs().get(0);
    private double curr_x2 = (double) super.getXs().get(1);

    public MinFunction3D(List<Double> params){
        super(params);
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
            MinFunction3D pb = new MinFunction3D(newX);
            X.add(pb);
            Y.add(getObjectiveFunction(newX));
        }
        ControlledGestionLists.reorderCGs(X, Y);
        return new ControlledGestionLists(X,Y);
    }

    @Override
    public void printSolution(String s) {
        System.out.println(s);
        System.out.println("For x = " + curr_x1 + ", y = " + curr_x2 + " ---> z = " + this.objectiveFunction());
    }

    @Override
    public double getObjectiveFunction(ArrayList<Double> X) {
        double x = X.get(0);
        double y = X.get(1);

        //easy one
        //return Math.sin(-0.15*(x*x+y*y))+0.05*(Math.pow(x+Math.PI, 2)+Math.pow(y-2, 2))+2;

        //hard one
        return 0.5*(Math.sin(2*x)+Math.sin(2*y))+0.005*(Math.pow(x,2)+Math.pow(y,2))+1;
    }

}