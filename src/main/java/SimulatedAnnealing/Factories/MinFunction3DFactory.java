package SimulatedAnnealing.Factories;

import SimulatedAnnealing._MinFunction.MinFunction;
import SimulatedAnnealing._MinFunction.MinFunction3D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunction3DFactory implements SAProblemsAbstractFactory{

    private double x;
    private double y;

    public MinFunction3DFactory(double x, double y){
        this.x = x;
        this.y = y;
    }

    // MinFunction est definie que par un seul double
    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<Double> range = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction3D(range.get(0), range.get(1));
    }
}