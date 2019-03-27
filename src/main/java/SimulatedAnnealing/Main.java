package SimulatedAnnealing;

import SimulatedAnnealing.Factories.MinFunction3DFactory;
import SimulatedAnnealing.Factories.MinFunctionFactory;
import SimulatedAnnealing.Factories.SAProblemsAbstractFactory;
import SimulatedAnnealing.Factories.TSPFactory;
import SimulatedAnnealing._MinFunction.MinFunction;
import SimulatedAnnealing._MinFunction.MinFunction3D;
import SimulatedAnnealing._TravelingSalesmanProblem.City;
import SimulatedAnnealing._TravelingSalesmanProblem.TourManager;

import java.util.ArrayList;

public class Main {

    private static boolean TSP_LSA = false;

    private static boolean MiF_LSA = false;
    private static boolean MiF_DSA = true;

    private static boolean MiF3D_LSA = false;
    private static boolean MiF3D_DSA = false;



    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/src/main/java/SimulatedAnnealing/GraphData/";

        /**  TSP  */
        //LSA
        ArrayList<City> tour = TourManager.problemInit();
        SAProblemsAbstractFactory tsp = new TSPFactory(tour);
        String titre = path+"LSA_TSP.txt";
        if(TSP_LSA)
            Optimization.optimizationLSA(100000, 0.0005, 0.0005, new ArrayList<>(tour), tsp, titre);//*/



        /**  MinFunction  */
        //LSA
        ArrayList<Double> x_only = MinFunction.problemInit();
        SAProblemsAbstractFactory factory = new MinFunctionFactory(x_only.get(0));
        String title = path+"LSA_MinFunction.txt";
        //initial temp + final temp = small when typical increase of the objective function is small, and the objective function results are small
        if(MiF_LSA)
            Optimization.optimizationLSA(10, 0.005, 0.001, new ArrayList<>(x_only), factory, title); //

        //DSA
        ArrayList<Double> x_only1 = MinFunction.problemInit();
        SAProblemsAbstractFactory factory1 = new MinFunctionFactory(x_only1.get(0));
        String title1 = path+"DSA_MinFunction.txt";
        if(MiF_DSA)
            Optimization.optimizationDSA(10e3, 0.005, 0.05, 1, new ArrayList<>(x_only1), factory1, title1);  //*/

        //todo

        /**  MinFunction3D  */
        //LSA
        ArrayList<Double> x_y = MinFunction3D.problemInit();
        SAProblemsAbstractFactory factory2 = new MinFunction3DFactory(x_y.get(0), x_y.get(1));
        String title3Da = path+"LSA_MinFunction3D.txt";
        //initial temp + final temp = small when typical increase of the objective function is small, and the objective function results are small
        if(MiF3D_LSA)
            Optimization.optimizationLSA(10, 0.005, 0.001, new ArrayList<>(x_y), factory2, title3Da); //*/

        //DSA
        ArrayList<Double> x_y1 = MinFunction3D.problemInit();
        SAProblemsAbstractFactory factory3 = new MinFunction3DFactory(x_y1.get(0), x_y1.get(1));
        String title3Db = path+"DSA_MinFunction3D.txt";
        if(MiF3D_DSA)
            Optimization.optimizationDSA(10e3, 0.002, 0.05, 1, new ArrayList<>(x_y1), factory3, title3Db);  //*/

    }



}
