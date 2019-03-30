package SimulatedAnnealing.Factories;

import SimulatedAnnealing.SAProblem;

import java.util.List;

public interface SAProblemsAbstractFactory {

    SAProblem createSAProblem(List<Object> objectList);
}