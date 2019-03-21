package SimulatedAnnealing._TravelingSalesmanProblem;

public class TSPUtility {


    /**
     * Computes and returns the Euclidean distance between two cities
     *
     * @param city1 the first city
     * @param city2 the second city
     * @return distance the dist between city1 and city2
     */
    public static double distance(City city1, City city2) {

        /*double lat = Math.toRadians(city1.getX() - city2.getX());
        double longi = Math.toRadians(city1.getY() - city2.getY());
        double a = Math.pow(Math.sin(lat/2), 2)
            + Math.cos(Math.toRadians(city1.getX())) * Math.cos(Math.toRadians(city2.getX())) * Math.pow(Math.sin(longi/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radius_earth = 6378.7;
        return radius_earth * c*/

        double xDistance = Math.abs(city1.getX() - city2.getX());
        double yDistance = Math.abs(city1.getY() - city2.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

}
