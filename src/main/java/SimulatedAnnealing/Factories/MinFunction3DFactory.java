package SimulatedAnnealing.Factories;

import SimulatedAnnealing._MinFunction.MinFunction3D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunction3DFactory implements SAProblemsAbstractFactory{


    // MinFunction est definie que par un seul double
    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<Double> params = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction3D(params);
    }
}