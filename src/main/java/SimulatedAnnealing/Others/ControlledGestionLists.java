package SimulatedAnnealing.Others;

import SimulatedAnnealing.ContinuousProblem;

import java.util.ArrayList;
import java.util.Collections;

public class ControlledGestionLists {
    private ArrayList<ContinuousProblem> X;
    private ArrayList<Double> Y;

    public ControlledGestionLists(ArrayList<ContinuousProblem> X, ArrayList<Double> Y) {
        this.X = X;
        this.Y = Y;
    }

    public ArrayList<ContinuousProblem> getX() {
        return X;
    }

    public ArrayList<Double> getY() {
        return Y;
    }

    public static void reorderCGs(ArrayList<ContinuousProblem> X, ArrayList<Double> Y) {
        int minI = Y.indexOf(Collections.min(Y));
        int maxI = Y.indexOf(Collections.max(Y));

        Collections.swap(Y, 0, minI);
        Collections.swap(Y, Y.size()-1, maxI);

        Collections.swap(X, 0, minI);
        Collections.swap(X, X.size()-1, maxI);
    }
}
