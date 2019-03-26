package SimulatedAnnealing.Factories;

import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;

public abstract class SAProblem {

    public abstract ArrayList<Object> getList();

    public abstract ControlledGestionLists CGInit(int length);

    public abstract void printSolution(String s);

    public abstract SAProblem transformSolutionLSA();

    public abstract SAProblem transformSolutionDSA(ArrayList<SAProblem> CGListX, int problem_dimension);

    public abstract double objectiveFunction();



    public void writeDataLSA(String title, double bestSolution, double currentSolution, double acceptanceProba, String isAccepted, double temp) {
        //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
        String data = getString(bestSolution, currentSolution, acceptanceProba, isAccepted, temp);

        Utils.dataToTxt(title, data, true);
    }

    public void writeDataDSA(String title, double bestSolution, double currentSolution, double acceptanceProba, String isAccepted, double temp, double density, int markovLen) {
        //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°, DENSITY, MARKOV LENGTH
        String data = getString(bestSolution, currentSolution, acceptanceProba, isAccepted, temp);
        data += Utils.format(density, 23);
        data += Utils.format(markovLen, 13);

        Utils.dataToTxt(title, data, true);
    }

    private String getString(double bestSolution, double currentSolution, double acceptanceProba, String isAccepted, double temp) {
        //BEST y, CURR y, ACCEPT PB, ACC-BEST Sol(TT/TF/FF), TEMPER°
        String data = "";
        data += Utils.format(bestSolution, 23);
        data += Utils.format(currentSolution, 23);
        data += Utils.format(acceptanceProba, 23);
        data += Utils.format(isAccepted, 10);
        data += Utils.format(temp, 23);
        return data;
    }

}
