package SimulatedAnnealing;

import java.util.ArrayList;

public interface SAProblemsAbstractFactory {
    SAProblem createSAProblem(ArrayList<Object> objectList);
}