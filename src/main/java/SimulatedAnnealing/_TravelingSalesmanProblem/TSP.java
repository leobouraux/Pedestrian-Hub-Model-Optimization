package SimulatedAnnealing._TravelingSalesmanProblem;

import SimulatedAnnealing.DiscreteProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP extends DiscreteProblem {

    private static int dimension;

    //to hold a tour of cities
    private static ArrayList<City> tour = new ArrayList<>();

    //we assume initial value of distance is 0
    private double distance = 0;

    //another Constructor
    //starts a tour from another tour
    public TSP(ArrayList<Object> tour) {
        super(tour);
    }

    @Override
    public int getDimension() {
        return 0;
    }

    public ArrayList<Object> getXs() {
        return new ArrayList<>(tour);
    }

    @Override
    public ControlledGestionLists CGInit(int length) {
        return null;
    }


    private City getCity(int index) {
        return tour.get(index);
    }


    private int tourSize() {
        return tour.size();
    }

    @Override
    public String toString() {
        String s = getCity(0).getCityName();
        for (int i = 1; i < tourSize(); i++) {
            s += " -> " + getCity(i).getCityName();
        }
        return s;
    }

    @Override
    public void printSolution(String s, double currObjective) {
        System.out.println(s + currObjective);
        System.out.println("Tour: " + this);
    }

    @Override
    public List<Object> transformSolutionLSA() {
        // Get random positions in the tour
        int tourPos1, tourPos2;
        do {
            tourPos1 = Utils.randomInt(0, tour.size());
            tourPos2 = Utils.randomInt(0, tour.size());
        } while(tourPos1 == tourPos2);

        // Get the cities at selected positions in the tour
        City citySwap1 = tour.get(tourPos1);
        City citySwap2 = tour.get(tourPos2);

        // Swap them
        tour.set(tourPos1, citySwap2);
        tour.set(tourPos2, citySwap1);


        return new ArrayList<>(tour);
    }


    @Override
    public double objectiveFunction() {
        if (distance == 0) {
            double tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're traveling from
                City fromCity = getCity(cityIndex);
                // SimulatedAnnealing.Main.TSP.City we're traveling to
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if (cityIndex + 1 >= tourSize()) {
                    destinationCity = getCity(0);
                } else {
                    destinationCity = getCity(cityIndex + 1);
                }
                // Get the distance between the two cities
                tourDistance += TSPUtility.distance(fromCity, destinationCity);
            }
            tourDistance = (double)Math.round(tourDistance * 100d) / 100d;
            distance = tourDistance;
        }
        return distance;
    }

    public static ArrayList<Object> problemInit(int dim) {
        dimension = dim;

        City city = new City("London", 51.5001524,-0.1262362);
        City city1 = new City("Tallinn", 59.4388619,24.7544715);
        City city2 = new City("Helsinki", 60.1698791,24.9384078);
        City city3 = new City("Paris", 48.8566667,2.3509871);
        City city4 = new City("Marseille", 43.296386,5.369954);
        City city5 = new City("Tbilisi", 41.709981,44.792998);
        City city6 = new City("Berlin", 52.5234051,13.4113999);
        City city7 = new City("Budapest", 47.4984056,19.0407578);
        City city8 = new City("Reykjavik", 64.135338,-21.89521);
        City city9 = new City("Dublin", 53.344104,-6.2674937);
        City city10 = new City("Rome", 41.8954656,12.4823243);
        City city11 = new City("Pristina", 42.672421,21.164539);
        City city12 = new City("Riga", 56.9465346,24.1048525);
        City city13 = new City("Vaduz", 47.1410409,9.5214458);
        City city14 = new City("Vilnius", 54.6893865,25.2800243);
        City city15 = new City("Luxembourg", 49.815273,6.129583);
        City city16 = new City("Skopje", 42.003812,21.452246);
        City city17 = new City("Valletta", 35.904171,14.518907);
        City city18 = new City("Chisinau", 47.026859,28.841551);
        City city19 = new City("Monaco", 43.750298,7.412841);
        City city20 = new City("Podgorica", 42.442575,19.268646);
        City city21 = new City("Amsterdam", 52.3738007,4.8909347);
        City city22 = new City("Belfast", 54.5972686,-5.9301088);
        City city23 = new City("Oslo", 59.9138204,10.7387413);
        City city24 = new City("Warsaw", 52.2296756,21.0122287);
        tour.add(city);
        tour.add(city1);
        tour.add(city2);
        tour.add(city3);
        tour.add(city4);
        tour.add(city5);
        tour.add(city6);
        tour.add(city7);
        tour.add(city8);
        tour.add(city9);
        tour.add(city10);
        tour.add(city11);
        tour.add(city12);
        tour.add(city13);
        tour.add(city14);
        tour.add(city15);
        tour.add(city16);
        tour.add(city17);
        tour.add(city18);
        tour.add(city19);
        tour.add(city20);
        tour.add(city21);
        tour.add(city22);
        tour.add(city23);
        tour.add(city24);
        Collections.shuffle(tour);
        return new ArrayList<>(tour);

    }

}
