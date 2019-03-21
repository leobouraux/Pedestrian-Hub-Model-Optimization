package SimulatedAnnealing._MinFunction;


import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinFunction extends SAProblem {

    private ArrayList<Double> range = new ArrayList<>();

    private double y = Double.POSITIVE_INFINITY;

    private final static double start = -10;
    private final static double end = 10;
    private final static double step = 0.001;

    //to avoid to IndexOutOfRangeError
    private final static int intervalForLocalSearch = Math.min(20, (int)((end-step)/(step*2)-1));

    public MinFunction(ArrayList<Double> range){
        this.range = new ArrayList<>(range);
    }

    public static ArrayList<Double> problemInit() {
        ArrayList<Double> range = new ArrayList<>();
        double i = start;
        while(i<=end) {
            range.add(i);
            i+=step;
        }
        //last index of range is the current solution
        Random rand = new Random();
        int index = rand.nextInt(range.size());
        double currentX = range.get(index);
        range.add(currentX);
        return range;
    }



    public double getCurrX(){
        return range.get(range.size()-1);
    }

    @Override
    public ArrayList<Object> getList() {
        return new ArrayList<>(range);
    }

    @Override
    public ArrayList<Double> CGInit(int length) {
        if(length > range.size())
            System.err.println("Length of A too big");
        ArrayList<Double> A = new ArrayList<>(length);
        double newX;
        for (int i = 0; i < length; i++) {
            newX = getRandomX();
            while(A.contains(newX)){
                newX = getRandomX();
            }
            A.add(objectiveFunctionCG(newX));
        }
        Utils.reorderCG(A);
        return A;
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
    public SAProblem transformSolutionDSA(ArrayList<Double> CGList, int n) {
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
            //controlled generation
            //get n random elements in CGList
            List<Double> CGcopy = new ArrayList<>(CGList);
            CGcopy.remove(0);
            Collections.shuffle(CGcopy);
            CGcopy = CGcopy.subList(0, n);

            //compute nextX
            double G = CGList.get(0);


            for (int i = 0; i < n-1 ; i++) {
                G+=CGcopy.get(i);
            }
            G = G / ((double) n);

            nextX = 2 * G - CGcopy.get(n-1);


            while(nextX < start || nextX > end || nextX == currX) {
                CGcopy = new ArrayList<>(CGList);
                CGcopy.remove(0);
                Collections.shuffle(CGcopy);
                G = CGList.get(0);
                for (int i = 0; i < n-1 ; i++) { G+=CGcopy.get(i); }
                G = G / ((double) n);
                nextX = 2 * G - CGcopy.get(n-1);
            }
        }
        range.set(range.size()-1, nextX);
        return new MinFunction(range);
    }


    private double getRandomX() {
        Random rand = new Random();
        int index = rand.nextInt(range.size()-2);
        return range.get(index);
    }


    @Override
    public double objectiveFunction() {
        double x = getCurrX();
        y = Math.log(0.1*Math.sin(30*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1;
        return y;
    }

    private double objectiveFunctionCG(double x) {
        y = Math.log(0.1*Math.sin(30*x) + 0.01*Math.pow(x, 4) - 0.1 *Math.pow(x,2) +1)+1;
        return y;
    }


}
