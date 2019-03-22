package SimulatedAnnealing;

import SimulatedAnnealing.Factories.MinFunctionFactory;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Factories.TSPFactory;
import SimulatedAnnealing._MinFunction.MinFunction;
import SimulatedAnnealing._TravelingSalesmanProblem.City;
import SimulatedAnnealing._TravelingSalesmanProblem.TourManager;

import java.util.ArrayList;

public class Main {

    private static boolean TSP_LSA = false;
    private static boolean TSP_DSA = false;
    private static boolean MiF_LSA = true;
    private static boolean MiF_DSA = false;


    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/src/main/java/SimulatedAnnealing/GraphData/";

        /**  TSP  */
        //LSA
        ArrayList<City> tour = TourManager.problemInit();
        SAProblemsAbstractFactory tsp = new TSPFactory(tour);
        String titre = path+"LSA_TSP.txt";
        if(TSP_LSA)
            Optimization.optimizationLSA(100000, 0.0005, 0.0005, new ArrayList<>(tour), tsp, titre);//*/

        //DSA
        ArrayList<City> tour1 = TourManager.problemInit();
        SAProblemsAbstractFactory tsp1 = new TSPFactory(tour1);
        String titre1 = path+"DSA_TSP.txt";
        if(TSP_DSA)
            Optimization.optimizationDSA(100000, 0.05, 0.05, 25, new ArrayList<>(tour), tsp1, titre1);//*/




        /**  MinFunction  */
        //LSA
        ArrayList<Double> range = MinFunction.problemInit();
        SAProblemsAbstractFactory factory = new MinFunctionFactory(range);
        String title = path+"LSA_MinFunction.txt";
        //initial temp + final temp = small when typical increase of the objective function is small, and the objective function results are small
        if(MiF_LSA)
            Optimization.optimizationLSA(10, 0.005, 0.001, new ArrayList<>(range), factory, title); //*/

        //DSA
        ArrayList<Double> range1 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory1 = new MinFunctionFactory(range1);
        String title1 = path+"DSA_MinFunction.txt";
        if(MiF_DSA)
            Optimization.optimizationDSA(1000, 0.05, 0.05, 1, new ArrayList<>(range1), factory1, title1);  //*/
    }



}