package SimulatedAnnealing._MinFunction;

import SimulatedAnnealing.ContinuousProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinFunction3D extends ContinuousProblem {

    private double curr_x1 = (double) super.getXs().get(0);
    private double curr_x2 = (double) super.getXs().get(1);

    public MinFunction3D(List<Double> params){
        super(params);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {
        ArrayList<ContinuousProblem> XY = new ArrayList<>(length);
        ArrayList<Double> Z = new ArrayList<>(length);
        double newX1, newX2;
        for (int i = 0; i < length; i++) {
            newX1 =  super.getRandomXi(super.getStarts().get(0), super.getEnds().get(0));
            newX2 =  super.getRandomXi(super.getStarts().get(1), super.getEnds().get(1));

            MinFunction3D pb = new MinFunction3D(Arrays.asList(newX1, newX2));
            XY.add(pb);
            Z.add(getObjectiveFunction(newX1, newX2));
        }
        ControlledGestionLists.reorderCGs(XY, Z);
        return new ControlledGestionLists(XY,Z);
    }

    @Override
    public void printSolution(String s) {
        System.out.println(s);
        System.out.println("For x = " + curr_x1 + ", y = " + curr_x2 + " ---> z = " + this.objectiveFunction());
    }


    @Override
    public double objectiveFunction() {
        return getObjectiveFunction(curr_x1, curr_x2);
    }

    private double getObjectiveFunction(double x, double y) {
        //easy one
        //return Math.sin(-0.15*(x*x+y*y))+0.05*(Math.pow(x+Math.PI, 2)+Math.pow(y-2, 2))+2;

        //hard one
        return 0.5*(Math.sin(2*x)+Math.sin(2*y))+0.005*(Math.pow(x,2)+Math.pow(y,2))+1;
    }
}