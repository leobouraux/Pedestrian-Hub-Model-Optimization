package SimulatedAnnealing.Factories;

import SimulatedAnnealing._TravelingSalesmanProblem.TSP;
import SimulatedAnnealing._TravelingSalesmanProblem.City;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TSPFactory implements SAProblemsAbstractFactory {

    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<City> cities = objects.stream()
                .map(object -> (City) object)
                .collect(Collectors.toList());

        return new TSP(new ArrayList<>(cities));
    }

}
