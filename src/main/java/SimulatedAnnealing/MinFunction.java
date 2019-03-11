package SimulatedAnnealing;


import java.util.ArrayList;
import java.util.Random;

public class MinFunction extends SAProblem{

    private ArrayList<Double> range = new ArrayList<>();
    private double y = Double.POSITIVE_INFINITY;

    public MinFunction(ArrayList<Double> range){
        this.range = new ArrayList<>(range);
    }

    public static ArrayList<Double> problemInit() {
        ArrayList<Double> range = new ArrayList<>();
        double start = -10;
        double end = 10;
        double step = 0.0001;

        while(start<=end) {
            range.add(start);
            start+=step;
        }
        //last index of range is the current solution
        Random rand = new Random();
        int index = rand.nextInt(range.size());
        double currentSolution = range.get(index);
        range.add(currentSolution);
        return range;
    }

    @Override
    public ArrayList<Object> getList() {
        return new ArrayList<>(range);
    }

    @Override
    public void printSolution(String s) {
        System.out.println(s);
        double x = range.get(range.size()-1);
        System.out.println("For x = " + x + ", y = " + this.objectiveFunction());
    }

    @Override
    public SAProblem initialSolution() {
        return new MinFunction(range);
    }

    private double getRandomX() {
        Random rand = new Random();
        int index = rand.nextInt(range.size()-2);
        return range.get(index);
    }


    @Override
    public SAProblem transformSolution() {
        double currX = range.get(range.size()-1);
        double nextX = getRandomX();
        while(currX==nextX) {
            nextX = getRandomX();
        }
        range.set(range.size()-1, nextX);
        return new MinFunction(range);
    }

    @Override
    public double objectiveFunction() {
        double x = range.get(range.size()-1);
        y = 0.1*Math.sin(30*x) + Math.pow(x, 4) + 2*Math.pow(x,3) - x;
        return y;
    }
}
