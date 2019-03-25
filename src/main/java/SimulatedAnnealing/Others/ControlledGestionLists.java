package SimulatedAnnealing.Others;

import SimulatedAnnealing.Factories.SAProblem;

import java.util.ArrayList;
import java.util.Collections;

public class ControlledGestionLists {
    private ArrayList<SAProblem> X;
    private ArrayList<Double> Y;

    public ControlledGestionLists(ArrayList<SAProblem> X, ArrayList<Double> Y) {
        this.X = X;
        this.Y = Y;
    }

    public ArrayList<SAProblem> getX() {
        return X;
    }

    public ArrayList<Double> getY() {
        return Y;
    }

    public static void reorderCGs(ArrayList<SAProblem> X, ArrayList<Double> Y) {
        int minI = Y.indexOf(Collections.min(Y));
        int maxI = Y.indexOf(Collections.max(Y));

        Collections.swap(Y, 0, minI);
        Collections.swap(Y, Y.size()-1, maxI);

        Collections.swap(X, 0, minI);
        Collections.swap(X, X.size()-1, maxI);
    }
}
