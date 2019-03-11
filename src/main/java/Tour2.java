import java.util.ArrayList;
import java.util.Collections;

public class Tour2 extends SAProblem {

    //to hold a tour of cities
    private ArrayList<City> tour = new ArrayList<City>();

    //we assume initial value of distance is 0
    private int distance = 0;

    public Tour2(){
        this.tour=new ArrayList<City>(TourManager.numberOfCities());
    }


    //another Constructor
    //starts a tour from another tour
    public Tour2(ArrayList<City> tour){
        this.tour = (ArrayList<City>) tour.clone();
    }


    public ArrayList<Object> getList(){
        return new ArrayList<>(tour);
    }


    public City getCity(int index) {
        return tour.get(index);
    }

    public void setCity(int index, City city) {
        tour.set(index, city);
        // If the tour has been altered we need to reset the fitness and distance
        distance = 0;
    }

    public int tourSize() {
        return tour.size();
    }


    @Override
    public String toString() {
        String s = getCity(0).getCityName();
        for (int i = 1; i < tourSize(); i++) {
            s += " -> " + getCity(i).getCityName();
        }
        return s;
    }


    @Override
    public void printSolution(String s) {
        System.out.println(s + this.objectiveFunction());
        System.out.println("Tour: " + this);
    }

    @Override
    public SAProblem initialSolution() {
        ArrayList<City> cities = tour;
        Collections.shuffle(cities);
        return new Tour2(cities);
    }

    @Override
    public SAProblem transformSolution() {
        // Create new neighbour tour

        Tour2 newSolution = new Tour2(tour);


        // Get random positions in the tour
        int tourPos1 = Utility.randomInt(0 , newSolution.tourSize());
        int tourPos2 = Utility.randomInt(0 , newSolution.tourSize());

        //to make sure that tourPos1 and tourPos2 are different
        while(tourPos1 == tourPos2) {tourPos2 = Utility.randomInt(0 , newSolution.tourSize());}

        // Get the cities at selected positions in the tour
        City citySwap1 = newSolution.getCity(tourPos1);
        City citySwap2 = newSolution.getCity(tourPos2);

        // Swap them
        newSolution.setCity(tourPos2, citySwap1);
        newSolution.setCity(tourPos1, citySwap2);

        return newSolution;
    }

    @Override
    public int objectiveFunction(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're traveling from
                City fromCity = getCity(cityIndex);
                // City we're traveling to
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(cityIndex+1 >= tourSize()){
                    destinationCity = getCity(0);
                }
                else{
                    destinationCity = getCity(cityIndex+1);
                }
                // Get the distance between the two cities
                tourDistance += Utility.distance(fromCity, destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }
}
