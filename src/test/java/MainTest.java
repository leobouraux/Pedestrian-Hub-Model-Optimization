import SimulatedAnnealing.Factories.MinFunctionFactory;
import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Others.Utils;
import SimulatedAnnealing._MinFunction.MinFunction;
import SimulatedAnnealing._TravelingSalesmanProblem.City;
import SimulatedAnnealing._TravelingSalesmanProblem.TSPUtility;
import SimulatedAnnealing._TravelingSalesmanProblem.TourManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainTest {


    public static void main(String[] args) throws InterruptedException {

        double s=0;
        double ite = 100000000;
        for (int i = 0; i <ite ; i++) {
            double o = Utils.randomDouble(-2, 2);
            s+=o;
            //System.out.println(o);
        }
        System.out.println("mean:"+s/ite);
    }

}