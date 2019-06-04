package SimulatedAnnealing;

import SimulatedAnnealing.Factories.*;
import SimulatedAnnealing._MinFunction.MinFunction;
import SimulatedAnnealing._MinFunction.MinFunction3D;
import SimulatedAnnealing._MinFunction.MinFunction4D;
import SimulatedAnnealing._TravelingSalesmanProblem.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Main {

    private static boolean TSP_LSA = false;

    private static boolean MiF_LSA = false;
    private static boolean MiF_DSA = false;

    private static boolean MiF3D_DSA = true;
    private static boolean MiF4D_DSA = false;

    private static boolean MiF4D_DSA_Stocha = false;



    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/src/main/java/SimulatedAnnealing/GraphData/";
        int dimension;

        /**DISCRETE PROBLEMS*/
        /**  TSP  */
        //LSA
        if(TSP_LSA) {
            dimension = 25;
            ArrayList<Object> tour = TSP.problemInit(dimension);
            SAProblemsAbstractFactory tsp = new TSPFactory();
            String titre = path+"LSA_TSP.txt";
            DiscreteProblem.optimizationLSA(10e3, 0.0005, 0.005, new ArrayList<>(tour), tsp, titre);//*/
        }


        /**CONTINUOUS PROBLEMS*/
        /**  MinFunction  */
        //LSA
        if(MiF_LSA) {
            dimension = 1;
            ArrayList<Double> x_only = MinFunction.problemInit(dimension, Collections.singletonList(-5.0), Collections.singletonList(5.0));
            SAProblemsAbstractFactory factory = new MinFunctionFactory();
            String title = path+"LSA_MinFunction.txt";
            //initial temp + final temp = small when typical increase of the objective function is small, and the objective function results are small
            DiscreteProblem.optimizationLSA(10, 0.005, 0.001, new ArrayList<>(x_only), factory, title); //
        }

        //DSA
        if(MiF_DSA) {
            dimension = 1;
            ArrayList<Double> x_only1 = MinFunction.problemInit(dimension, Collections.singletonList(-5.0), Collections.singletonList(5.0));
            SAProblemsAbstractFactory factory1 = new MinFunctionFactory();
            String title1 = path + "DSA_MinFunction.txt";

            double initTemp = 0;
            for (int i = 0; i < 10; i++) {
                 initTemp += ContinuousProblem.findInitTemp(25*dimension, new ArrayList<>(x_only1), factory1);
            }
            initTemp /= 10.0;

            ContinuousProblem.optimizationDSA(initTemp, 0.05, new ArrayList<>(x_only1), factory1, title1, false, 0.005);  //*/
        }

        /**  MinFunction3D  */

        if(MiF3D_DSA) {
            dimension = 2;
            ArrayList<Double> x_y1 = MinFunction3D.problemInit(dimension, Arrays.asList(-10.0, -10.0), Arrays.asList(10.0, 10.0));
            SAProblemsAbstractFactory factory3 = new MinFunction3DFactory();
            String title3Db = path+"DSA_MinFunction3D.txt";

            double initTemp = 0;
            for (int i = 0; i < 10; i++) {
                initTemp += ContinuousProblem.findInitTemp(25*dimension, new ArrayList<>(x_y1), factory3);
            }

            ContinuousProblem.optimizationDSA(initTemp, 0.05, new ArrayList<>(x_y1), factory3, title3Db, false, 0.008);  //*/
        }

        /**  MinFunction4D  */

        //DSA
        if(MiF4D_DSA_Stocha || MiF4D_DSA) {
            dimension = 3;
            ArrayList<Double> x_y1 = MinFunction4D.problemInit(dimension, Arrays.asList(-5.0, -5.0, -5.0), Arrays.asList(5.0, 5.0, 5.0));
            SAProblemsAbstractFactory factory4 = new MinFunction4DFactory();
            String title4D = path+"DSA_MinFunction4D.txt";

            double initTemp3 = 0;
            for (int i = 0; i < 10; i++) {
                initTemp3 += ContinuousProblem.findInitTemp(25*dimension, new ArrayList<>(x_y1), factory4);
            }
            initTemp3 /= 10.0;

            if(MiF4D_DSA) {
                ContinuousProblem.optimizationDSA(initTemp3, 0.02, new ArrayList<>(x_y1), factory4, title4D, false, 0.02);  //*/
            }
            if(MiF4D_DSA_Stocha) {
                int nb_iter = 500;
                title4D = path+"DSA_MinFunction4D_Stocha.txt";

                String names = "              ACCEPT PB|Control-G?|            TEMPERATURE|                DENSITY|ACTUAL#MARKOV|                 BEST y|                 CURR y|";
                names += SAProblem.Helper.getNamesForXi(dimension-1, new MinFunction4D(Arrays.asList(-5.0, -5.0, -5.0)));
                SAProblem.Helper.TXT_Titles(title4D, names, false);

                for (int i = 0; i < nb_iter; i++) {
                    ContinuousProblem.optimizationDSA(initTemp3, 0.02, new ArrayList<>(x_y1), factory4, title4D, true, 0.001);  //*/
                    System.out.print((i+1)+" ");
                }
            }
        }
    }



}
