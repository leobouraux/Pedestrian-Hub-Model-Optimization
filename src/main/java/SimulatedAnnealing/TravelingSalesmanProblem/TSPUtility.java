package SimulatedAnnealing.TravelingSalesmanProblem;

import java.util.Random;

public class TSPUtility {


    /**
     * Computes and returns the Euclidean distance between two cities
     *
     * @param city1 the first city
     * @param city2 the second city
     * @return distance the dist between city1 and city2
     */
    public static double distance(City city1, City city2) {
        int xDistance = Math.abs(city1.getX() - city2.getX());
        int yDistance = Math.abs(city1.getY() - city2.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

}
