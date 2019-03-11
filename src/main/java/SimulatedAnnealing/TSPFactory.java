package SimulatedAnnealing;

import SimulatedAnnealing.TravelingSalesmanProblem.City;
import SimulatedAnnealing.TravelingSalesmanProblem.TourManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TSPFactory implements SAProblemsAbstractFactory {

    //to hold a tour of cities
    private ArrayList<City> tour = new ArrayList<City>();


    public TSPFactory() {
        this.tour = new ArrayList<City>(TourManager.numberOfCities());
    }


    //another Constructor
    //starts a tour from another tour
    public TSPFactory(ArrayList<City> tour) {
        this.tour = new ArrayList<>(tour);
    }


    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<City> cities = objects.stream()
                .map(object -> (City) object)
                .collect(Collectors.toList());

        return new TSP(new ArrayList<>(cities));
    }

}
