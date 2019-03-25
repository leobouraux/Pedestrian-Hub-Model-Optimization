import SimulatedAnnealing.Factories.MinFunctionFactory;
import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainTest {


    public static void main(String[] args) throws InterruptedException {

        ArrayList<Double> range1 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory1 = new MinFunctionFactory(range1);
        SAProblem pb1 = factory1.createSAProblem(new ArrayList<>(range1));

        ArrayList<Double> range2 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory2 = new MinFunctionFactory(range2);
        SAProblem pb2 = factory2.createSAProblem(new ArrayList<>(range2));

        ArrayList<Double> range3 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory3 = new MinFunctionFactory(range3);
        SAProblem pb3 = factory3.createSAProblem(new ArrayList<>(range3));

        ArrayList<Double> range4 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory4 = new MinFunctionFactory(range4);
        SAProblem pb4 = factory4.createSAProblem(new ArrayList<>(range4));

        ArrayList<Double> range5 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory5 = new MinFunctionFactory(range5);
        SAProblem pb5 = factory5.createSAProblem(new ArrayList<>(range5));

        ArrayList<SAProblem> CGListX = new ArrayList<>();

        CGListX.add(pb1);
        CGListX.add(pb2);
        CGListX.add(pb3);
        CGListX.add(pb4);
        CGListX.add(pb5);

        int n = 1;

       /* double nextX;
        //Controlled generation
        nextX = getNextX(CGListX, n);
        while (nextX < -10 || nextX > 10 || nextX == currX) {
            nextX = getNextX(CGListX, n);
        }
        System.out.println("TransfoF° : ControledGeneration ::: nextX =" + nextX + "   nextY =" + getObjectiveFunction(nextX));

        range.set(range.size() - 1, nextX);*/


    }

    public static double getNextX(ArrayList<SAProblem> CGListX, int n) {
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
        return nextX;
    }


}