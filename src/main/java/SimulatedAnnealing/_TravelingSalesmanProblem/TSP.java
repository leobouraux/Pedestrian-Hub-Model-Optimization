package SimulatedAnnealing._TravelingSalesmanProblem;

import SimulatedAnnealing.Factories.SAProblem;
import SimulatedAnnealing.Others.ControlledGestionLists;
import SimulatedAnnealing.Others.Utils;

import java.util.ArrayList;

public class TSP extends SAProblem {

    //to hold a tour of cities
    private ArrayList<City> tour = new ArrayList<>();

    //we assume initial value of distance is 0
    private double distance = 0;

    //another Constructor
    //starts a tour from another tour
    public TSP(ArrayList<City> tour) {
        this.tour = new ArrayList<>(tour);
    }

    public ArrayList<Object> getParams() {
        return new ArrayList<>(tour);
    }


    @Override
    public ControlledGestionLists CGInit(int length) {
        return null;
    }


    private City getCity(int index) {
        return tour.get(index);
    }

    private void setCity(int index, City city) {
        tour.set(index, city);
        // If the tour has been altered we need to reset the fitness and distance
        distance = 0;
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
    public void printSolution(String s) {
        System.out.println(s + this.objectiveFunction());
        System.out.println("Tour: " + this);
    }

    @Override
    public SAProblem transformSolutionLSA() {
        // Create new neighbour tour

        TSP newSolution = new TSP(tour);

        // Get random positions in the tour
        int tourPos1, tourPos2;
        do {
            tourPos1 = Utils.randomInt(0, newSolution.tourSize());
            tourPos2 = Utils.randomInt(0, newSolution.tourSize());
        } while(tourPos1 == tourPos2);

        // Get the cities at selected positions in the tour
        City citySwap1 = newSolution.getCity(tourPos1);
        City citySwap2 = newSolution.getCity(tourPos2);

        // Swap them
        newSolution.setCity(tourPos1, citySwap2);
        newSolution.setCity(tourPos2, citySwap1);

        return newSolution;
    }

    @Override
    public SAProblem transformSolutionDSA(ArrayList<SAProblem> CGListX, int n) {
        return null;
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



    private double getObjectiveFunction(ArrayList<City> cities) {
        double distance = 0;
        // Loop through our tour's cities
        for (int cityIndex = 0; cityIndex < cities.size(); cityIndex++) {
            // Get city we're traveling from
            City fromCity = cities.get(cityIndex);
            // City we're traveling to
            City destinationCity;
            // Check we're not on our tour's last city, if we are set our
            // tour's final destination city to our starting city
            if (cityIndex + 1 >= cities.size()) {
                destinationCity = cities.get(0);
            } else {
                destinationCity = cities.get(cityIndex + 1);
            }
            // Get the distance between the two cities
            distance += TSPUtility.distance(fromCity, destinationCity);
        }
        distance = (double)Math.round(distance * 100d) / 100d;
        return distance;
    }


}
