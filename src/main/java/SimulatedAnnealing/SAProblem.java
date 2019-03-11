package SimulatedAnnealing;

import java.util.ArrayList;

public abstract class SAProblem {

    public abstract ArrayList<Object> getList();
    public abstract void printSolution(String s);
    public abstract SAProblem initialSolution();
    public abstract SAProblem transformSolution();
    public abstract int objectiveFunction();

}
