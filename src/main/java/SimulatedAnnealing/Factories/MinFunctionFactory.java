package SimulatedAnnealing.Factories;


import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunctionFactory implements SAProblemsAbstractFactory {

    private double x;

    public MinFunctionFactory(double x){
        this.x = x;
    }

    // MinFunction est definie que par un seul double
    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<Double> range = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction(range.get(0));
    }

}
