/*package SimulatedAnnealing.Factories;

import SimulatedAnnealing._MinFunction.MinFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinFunction3DFactory implements SAProblemsAbstractFactory{


    private ArrayList<Double> rangeX = new ArrayList<>();
    private ArrayList<Double> rangeY = new ArrayList<>();
    private double x;
    private double y;

    public MinFunction3DFactory(ArrayList<Double> rangeX, ArrayList<Double> rangeY, double x, double y) {
        this.rangeX = new ArrayList<>(rangeX);
        this.rangeX = new ArrayList<>(rangeY);
        this.x = x;
        this.y = y;
    }


    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<Double> range = objects.stream()
                .map(object -> (Double) object)
                .collect(Collectors.toList());

        return new MinFunction3(new ArrayList<>(range));
    }

}*/