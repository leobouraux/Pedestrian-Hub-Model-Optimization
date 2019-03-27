package SimulatedAnnealing._MinFunction;


import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.*;

public class MinFunction extends SAProblem {

    private double x;

    private final static double start = -3;
    private final static double end = 3;

    private final static double intervalForLocalSearch = (end-start)/20.0;

    public MinFunction(double x){
        this.x = x;
    }


    public double getX() {
        return x;
    }

    //liste des paramètres défénissant entièrement le problème : ici une seule variable
    public static ArrayList<Double> problemInit() {
        double x = Utils.randomDouble(start, end);
        ArrayList<Double> minFunction = new ArrayList<>();
        minFunction.add(x);
        return minFunction;
    }

    @Override
    public ArrayList<Object> getParams() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(x);
        return new ArrayList<>(list);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {

        ArrayList<SAProblem> X = new ArrayList<>(length);
        ArrayList<Double> Y = new ArrayList<>(length);
        double newX;
        for (int i = 0; i < length; i++) {
            newX = getRandomX();
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
        System.out.println("For x = " + x + ", y = " + this.objectiveFunction());
    }

    @Override
    public SAProblem transformSolutionLSA() {
        double w = Utils.randomProba();
        double nextX;
        if(w<0.75) {
            do {
                nextX = getRandomX();
            } while(x==nextX);
        }
        else {
            //local search
            double min = Math.max(start, x-intervalForLocalSearch);
            double max = Math.min(x+intervalForLocalSearch, end);
            do{
                nextX = Utils.randomDouble(min, max);
            } while(x==nextX);
        }
        return new MinFunction(nextX);
    }

    @Override
    public SAProblem transformSolutionDSA(ArrayList<SAProblem> CGListX, int n) {
        double w = Utils.randomProba();

        double nextX;
        if(w<0.75) {
            //Uniform distribution
            do {
                nextX = getRandomX();
            } while(x==nextX);
        }
        else {
            //Controlled generation
            boolean check, stuck = false;
            int i = 0;
            do {
                nextX = getNextX(CGListX, n);
                check = nextX >= start && nextX <= end;
                //on est bloqué
                if(i>=7*(n+1)) {
                    check = true;
                    stuck = true;
                }
                i++;
            } while(!check);

            //quand on est bloqué dans le CG : deux mini locaux sont trop près de xMin et xMax et quand n = 1 --> nextX = 2 * xMin - xMax = OUT
            //local best search
            if(stuck) {
                double bestX = ((MinFunction)CGListX.get(0)).getX();
                double min = Math.max(start, bestX-intervalForLocalSearch);
                double max = Math.min(bestX+intervalForLocalSearch, end);
                nextX = Utils.randomDouble(min, max);
                System.out.println("local best search");
            }
        }
        return new MinFunction(nextX);
    }

    private double getNextX(ArrayList<SAProblem> CGListX, int n) {
        double nextX;

        //get n random elements in CGList
        List<SAProblem> CGcopy = new ArrayList<>(CGListX);
        CGcopy.remove(0);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, n);

        //compute nextX
        double G = ((MinFunction) (CGListX.get(0))).getX();
        for (int i = 0; i < n-1 ; i++) {
            G+= ((MinFunction) (CGcopy.get(i))).getX();
        }
        G = G / ((double) n);
        nextX = 2 * G - ((MinFunction) (CGcopy.get(n-1))).getX();

        return nextX;

    }

    private double getRandomX() {
        return Utils.randomDouble(start, end);
    }


    @Override
    public double objectiveFunction() {
        return getObjectiveFunction(x);

    }

    private double getObjectiveFunction(Double x) {
        //easy one
        //return Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;

        //hard one
        return Math.log(0.1*Math.sin(30*x)+0.01*Math.pow(x, 4)-0.1*Math.pow(x, 2) + 1)+1;
    }

    public void writeDataCurrX(String title, double bestX, double currX) {
        String data = "";
        data += Utils.format(bestX, 23);
        data += Utils.format(currX, 23);

        Utils.dataToTxt(title, data, true);
    }



}
