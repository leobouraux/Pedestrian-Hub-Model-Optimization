package SimulatedAnnealing;

import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;

public abstract class SAProblem {

    public abstract ArrayList<Object> getList();

    public abstract void printSolution(String s);

    public abstract SAProblem initialSolution();

    public abstract SAProblem transformSolution();

    public abstract double objectiveFunction();

    public void writeData(String title, double temp, double currentSolution, double bestSolution, String isAccepted, double acceptanceProba) {
        //best y, current y, temperature, acceptance proba, accepted solutions?(T/F), temps de convergence
        String data = String.valueOf(bestSolution);
        data += ","+ String.valueOf(currentSolution);
        data += ","+ temp;
        data += ","+ acceptanceProba;
        data += ","+ isAccepted;

        Utils.dataToTxt(title, data, true);
    }
}
