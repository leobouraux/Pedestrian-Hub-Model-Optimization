package SimulatedAnnealing._MinFunction;


import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinFunction extends SAProblem {

    private ArrayList<Double> range = new ArrayList<>();

    private double y = Double.POSITIVE_INFINITY;

    private final static double start = -2;
    private final static double end = 2;
    private final static double step = 0.001;

    //to avoid to IndexOutOfRangeError
    private final static int intervalForLocalSearch = Math.min(20, (int)((end-step)/(step*2)-1));

    public MinFunction(ArrayList<Double> range){
        this.range = new ArrayList<>(range);
    }

    public static ArrayList<Double> problemInit() {
        ArrayList<Double> range = createRange();
        //last index of range is the current solution
        Random rand = new Random();
        int index = rand.nextInt(range.size());
        double currentX = range.get(index);
        range.add(currentX);
        return range;
    }

    private static ArrayList<Double> problemInitCG(double nextX) {
        ArrayList<Double> range = createRange();
        //last index of range is the current solution
        range.add(nextX);
        return range;
    }

    private static ArrayList<Double> createRange() {
        ArrayList<Double> range = new ArrayList<>();
        double i = start;
        while (i <= end) {
            range.add(i);
            i += step;
        }
        return range;
    }

    private double getCurrX(){
        return range.get(range.size()-1);
    }

    @Override
    public ArrayList<Object> getList() {
        return new ArrayList<>(range);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {
        if(length > range.size())
            System.err.println("Length of A too big");
        ArrayList<SAProblem> X = new ArrayList<>(length);
        ArrayList<Double> Y = new ArrayList<>(length);
        double newX;
        for (int i = 0; i < length; i++) {
            newX = getRandomX();
            MinFunction pb = new MinFunction(problemInitCG(newX));
            X.add(pb);
            Y.add(getObjectiveFunction(newX));
        }
        ControlledGestionLists.reorderCGs(X, Y);
        return new ControlledGestionLists(X,Y);
    }

    @Override
    public void printSolution(String s) {
        System.out.println(s);
        double x = getCurrX();
        System.out.println("For x = " + x + ", y = " + this.objectiveFunction());
    }

    @Override
    public SAProblem transformSolutionLSA() {
        double currX = getCurrX();
        double w = Utils.randomProba();
        double nextX;
        if(w<0.75) {
            nextX = getRandomX();
            while(currX==nextX) {
                nextX = getRandomX();
            }
        }
        else {
            //local search
            int currIndex = range.indexOf(currX);
            int minIndex = Math.max(0, currIndex-intervalForLocalSearch);
            int maxIndex = Math.min(range.size()-2, currIndex+intervalForLocalSearch);
            int nextIndex = Utils.randomInt(minIndex, maxIndex);
            nextX = range.get(nextIndex);

            while(currX==nextX) {
                nextIndex =  Utils.randomInt(minIndex, maxIndex);
                nextX = range.get(nextIndex);
            }
        }
        range.set(range.size()-1, nextX);
        return new MinFunction(range);
    }

    @Override
    public SAProblem transformSolutionDSA(ArrayList<SAProblem> CGListX, int n) {
        double currX = getCurrX();

        double w = Utils.randomProba();
        double nextX;
        if(w<0.75) {
            //Uniform distribution
            do {
                nextX = getRandomX();
            } while(currX==nextX);
        }
        else {
            //Controlled generation
            nextX = getNextX(CGListX, n);
            //boolean unavailable = isUnavailable(CGListX, currX, nextX);
            //while(nextX < start || nextX > end || unavailable) {
            //    nextX = getNextX(CGListX, n);
            //    unavailable = isUnavailable(CGListX, currX, nextX);
            //}
        }
        range.set(range.size()-1, nextX);
        return new MinFunction(range);
    }

    private boolean isUnavailable(ArrayList<SAProblem> CGListX, double currX, double nextX) {
        boolean unavailable = (nextX == currX);
        for (SAProblem pb : CGListX) {
            double xInCG = (double) pb.getList().get(pb.getList().size()-1);
            if(nextX == xInCG) {
                unavailable = true;
            }
        }
        return unavailable;
    }

    private double getNextX(ArrayList<SAProblem> CGListX, int n) {
        ArrayList<Object> o; //created to get SAPB Lists
        double nextX;

        //get n random elements in CGList
        List<SAProblem> CGcopy = new ArrayList<>(CGListX);
        CGcopy.remove(0);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, n);

        //compute nextX
        o = CGListX.get(0).getList();
        double G = (double)o.get(o.size()-1);
        for (int i = 0; i < n-1 ; i++) {
            o = CGcopy.get(i).getList();
            G+=(double)o.get(o.size()-1);
        }
        G = G / ((double) n);
        o = CGcopy.get(n-1).getList();
        nextX = 2 * G - (double)o.get(o.size()-1);

        //to avoid infinite loop when nextX already exist in CGListX when n = 1 OR delete while in transformF° CG part
        //double epsilon = 0.0000000000000001*Utils.randomInt(0,10);
        return nextX;//+epsilon;

    }

    private double getRandomX() {
        Random rand = new Random();
        int index = rand.nextInt(range.size()-2);
        return range.get(index);
    }


    @Override
    public double objectiveFunction() {
        double x = getCurrX();
        y = Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;
        return y;
    }

    public double getObjectiveFunction(Double x) {
        return Math.log(0.1*Math.sin(10*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1+0.7*x*x;
    }

    public void writeDataCurrX(String title, double currX) {
        String data = "";
        data += Utils.format(currX, 23);

        Utils.dataToTxt(title, data, true);
    }



}
