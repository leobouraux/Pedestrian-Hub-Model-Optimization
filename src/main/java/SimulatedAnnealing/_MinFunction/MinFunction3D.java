package SimulatedAnnealing._MinFunction;

import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinFunction3D extends SAProblem {

    private final static double startX = -10;
    private final static double endX = 10;
    private final static double startY = -10;
    private final static double endY = 10;

    //to avoid to IndexOutOfRangeError
    private final static double intervalForLocalSearchX = (endX-startX)/20.0;
    private final static double intervalForLocalSearchY = (endY-startY)/20.0;

    private double curr_x;
    private double curr_y;



    public MinFunction3D(double curr_x, double curr_y){
        this.curr_x = curr_x;
        this.curr_y = curr_y;
    }

    public double getX() {
        return curr_x;
    }

    public double getY() {
        return curr_y;
    }


    //liste des paramètres défénissant entièrement le problème : ici une seule variable
    public static ArrayList<Double> problemInit() {
        ArrayList<Double> minFunction = new ArrayList<>();
        double x = Utils.randomDouble(startX, endX);
        double y = Utils.randomDouble(startY, endY);
        minFunction.add(x);
        minFunction.add(y);

        return minFunction;
    }


    @Override
    public ArrayList<Object> getParams() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(curr_x);
        list.add(curr_y);
        return new ArrayList<>(list);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {
        ArrayList<SAProblem> XY = new ArrayList<>(length);
        ArrayList<Double> Z = new ArrayList<>(length);
        double newX, newY;
        for (int i = 0; i < length; i++) {
            newX = getRandomX();
            newY = getRandomY();
            MinFunction3D pb = new MinFunction3D(newX, newY);
            XY.add(pb);
            Z.add(getObjectiveFunction(newX, newY));
        }
        ControlledGestionLists.reorderCGs(XY, Z);
        return new ControlledGestionLists(XY,Z);
    }

    @Override
    public void printSolution(String s) {
        System.out.println(s);
        System.out.println("For x = " + curr_x + ", y = " + curr_y + " ---> z = " + this.objectiveFunction());
    }

    @Override
    public SAProblem transformSolutionLSA() {
        double w = Utils.randomProba();
        double nextX, nextY;
        if(w<0.75) {
            do {
                nextX = getRandomX();
                nextY = getRandomY();
            } while(curr_x==nextX || curr_y==nextY);
        }
        else {
            //local search
            double minX = Math.max(startX, curr_x-intervalForLocalSearchX);
            double maxX = Math.min(curr_x+intervalForLocalSearchX, endX);
            double minY = Math.max(startY, curr_y-intervalForLocalSearchY);
            double maxY = Math.min(curr_y+intervalForLocalSearchY, endY);
            do{
                nextX = Utils.randomDouble(minX, maxX);
                nextY = Utils.randomDouble(minY, maxY);
            } while(curr_x==nextX || curr_y==nextY);
        }
        return new MinFunction3D(nextX, nextY);
    }

    @Override
    public SAProblem transformSolutionDSA(ArrayList<SAProblem> CGListXY, int n) {

        double w = Utils.randomProba();
        double nextX;
        double nextY;
        if(w<0.75) {
            //Uniform distribution
            do {
                nextX = getRandomX();
                nextY = getRandomY();
            } while(curr_x==nextX || curr_y==nextY);
        }
        else {
            //Controlled generation
            boolean check, stuck = false;
            int i = 0;
            do {
                nextX = getNextCGx(CGListXY, n);
                nextY = getNextCGy(CGListXY, n);
                check = nextX >= startX && nextX <= endX
                        && nextY >= startY && nextY <= endY;
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
                double bestX = ((MinFunction3D)CGListXY.get(0)).getX();
                double bestY = ((MinFunction3D)CGListXY.get(0)).getY();
                double minX = Math.max(startX, bestX-intervalForLocalSearchX);
                double maxX = Math.min(bestX+intervalForLocalSearchX, endX);
                double minY = Math.max(startY, bestY-intervalForLocalSearchY);
                double maxY = Math.min(bestY+intervalForLocalSearchY, endY);
                nextX = Utils.randomDouble(minX, maxX);
                nextY = Utils.randomDouble(minY, maxY);
                System.out.println("local best search");
            }
        }
        return new MinFunction3D(nextX, nextY);
    }

    private double getNextCGx(ArrayList<SAProblem> CGList, int n) {
        //get n random elements in CGList
        List<SAProblem> CGcopy = new ArrayList<>(CGList);
        CGcopy.remove(0);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, n);

        //compute nextX
        double G = ((MinFunction3D) (CGList.get(0))).getX();
        for (int i = 0; i < n-1 ; i++) {
            G+= ((MinFunction3D) (CGcopy.get(i))).getX();
        }
        G = G / ((double) n);
        return 2 * G - ((MinFunction3D) (CGcopy.get(n-1))).getX();

    }

    private double getNextCGy(ArrayList<SAProblem> CGList, int n) {
        //get n random elements in CGList
        List<SAProblem> CGcopy = new ArrayList<>(CGList);
        CGcopy.remove(0);
        Collections.shuffle(CGcopy);
        CGcopy = CGcopy.subList(0, n);

        //compute nextX
        double G = ((MinFunction3D) (CGList.get(0))).getY();
        for (int i = 0; i < n-1 ; i++) {
            G+= ((MinFunction3D) (CGcopy.get(i))).getY();
        }
        G = G / ((double) n);
        return 2 * G - ((MinFunction3D) (CGcopy.get(n-1))).getY();
    }

    private double getRandomX() {
        return Utils.randomDouble(startX, endX);
    }

    private double getRandomY() {
        return Utils.randomDouble(startY, endY);
    }


    @Override
    public double objectiveFunction() {
        return getObjectiveFunction(curr_x, curr_y);
    }

    private double getObjectiveFunction(double x, double y) {
        return Math.sin(-0.15*(x*x+y*y))+0.05*(Math.pow(x+Math.PI, 2)+Math.pow(y-2, 2))+2;
    }

    public void writeDataCurrXY(String title, double bestX, double currX, double bestY, double currY) {
        String data = "";
        data += Utils.format(bestX, 23);
        data += Utils.format(currX, 23);
        data += Utils.format(bestY, 23);
        data += Utils.format(currY, 23);

        Utils.dataToTxt(title, data, true);
    }


}