import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tour2Factory implements SAProblemsAbstractFactory {

    //to hold a tour of cities
    private ArrayList<City> tour = new ArrayList<City>();


    public Tour2Factory(){
        this.tour=new ArrayList<City>(TourManager.numberOfCities());
    }


    //another Constructor
    //starts a tour from another tour
    public Tour2Factory(ArrayList<City> tour){
        this.tour = (ArrayList<City>) tour.clone();
    }



    @Override
    public SAProblem createSAProblem(ArrayList<Object> objects) {

        List<City> cities = objects.stream()
                .map(object -> (City) object)
                .collect(Collectors.toList());

        return new Tour2(new ArrayList<>(cities));
    }
}
