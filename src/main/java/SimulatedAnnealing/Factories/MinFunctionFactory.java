package SimulatedAnnealing.Factories;


import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunctionFactory implements SAProblemsAbstractFactory {

    private ArrayList<Double> range = new ArrayList<>();
    private double x;

    public MinFunctionFactory(ArrayList<Double> range){
        this.range = new ArrayList<>(range);
        this.x=x;
    }



    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<Double> range = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction(new ArrayList<>(range));
    }

}
