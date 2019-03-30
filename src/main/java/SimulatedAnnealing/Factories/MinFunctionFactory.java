package SimulatedAnnealing.Factories;


import SimulatedAnnealing.SAProblem;
import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunctionFactory implements SAProblemsAbstractFactory {

    // MinFunction est definie que par un seul double
    @Override
    public SAProblem createSAProblem(List<Object> objects) {

        List<Double> param = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction(param);
    }

}
