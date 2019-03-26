package SimulatedAnnealing._TravelingSalesmanProblem;

import java.util.ArrayList;
import java.util.Collections;

/*
 * SimulatedAnnealing.Main.TSP.TourManager.java
 * Holds and keeps track of the cities of a tour
 */


public class TourManager {

    // Holds our cities
    public static ArrayList<City> destinationCities = new ArrayList<City>();

    /**
     * Adds a destination city
     *
     * @param city
     */
    public static void addCity(City city) {
        destinationCities.add(city);
    }

    /**
     * returns a city given its index
     *
     * @param index
     * @return city the city at index
     */
    public static City getCity(int index) {
        return (City) destinationCities.get(index);
    }

    /**
     * Returns the number of destination cities
     *
     * @return size the number of destination cities
     */
    public static int numberOfCities() {
        return destinationCities.size();
    }

    public static ArrayList<City> problemInit() {
        //add all cities

        City city = new City("London", 51.5001524,-0.1262362);
        City city1 = new City("Tallinn", 59.4388619,24.7544715);
        City city2 = new City("Helsinki", 60.1698791,24.9384078);
        City city3 = new City("Paris", 48.8566667,2.3509871);
        City city4 = new City("Marseille", 43.296386,5.369954);
        City city5 = new City("Tbilisi", 41.709981,44.792998);
        City city6 = new City("Berlin", 52.5234051,13.4113999);
        City city7 = new City("Budapest", 47.4984056,19.0407578);
        City city8 = new City("Reykjavik", 64.135338,-21.89521);
        City city9 = new City("Dublin", 53.344104,-6.2674937);
        City city10 = new City("Rome", 41.8954656,12.4823243);
        City city11 = new City("Pristina", 42.672421,21.164539);
        City city12 = new City("Riga", 56.9465346,24.1048525);
        City city13 = new City("Vaduz", 47.1410409,9.5214458);
        City city14 = new City("Vilnius", 54.6893865,25.2800243);
        City city15 = new City("Luxembourg", 49.815273,6.129583);
        City city16 = new City("Skopje", 42.003812,21.452246);
        City city17 = new City("Valletta", 35.904171,14.518907);
        City city18 = new City("Chisinau", 47.026859,28.841551);
        City city19 = new City("Monaco", 43.750298,7.412841);
        City city20 = new City("Podgorica", 42.442575,19.268646);
        City city21 = new City("Amsterdam", 52.3738007,4.8909347);
        City city22 = new City("Belfast", 54.5972686,-5.9301088);
        City city23 = new City("Oslo", 59.9138204,10.7387413);
        City city24 = new City("Warsaw", 52.2296756,21.0122287);
        City city25 = new City("Lisbon", 38.7071631,-9.135517);
        City city26 = new City("Bucharest", 44.430481,26.12298);
        City city27 = new City("Moscow", 55.755786,37.617633);
        City city28 = new City("San Marino", 43.94236,12.457777);
        City city29 = new City("Edinburgh", 55.9501755,-3.1875359);
        City city30 = new City("Belgrade", 44.802416,20.465601);
        City city31 = new City("Bratislava", 48.1483765,17.1073105);
        City city32 = new City("Ljubljana", 46.0514263,14.5059655);
        City city33 = new City("Madrid", 40.4166909,-3.7003454);
        City city34 = new City("Stockholm", 59.3327881,18.0644881);
        City city35 = new City("Bern", 46.9479986,7.4481481);
        City city36 = new City("Sarajevo", 43.85643,18.41342);
        City city37 = new City("Sofia", 42.6976246,23.3222924);
        City city38 = new City("Zagreb", 45.8150053,15.9785014);
        City city39 = new City("Nicosia", 35.167604,33.373621);
        City city40 = new City("Prague", 50.0878114,14.4204598);
        TourManager.addCity(city);
        TourManager.addCity(city1);
        TourManager.addCity(city2);
        TourManager.addCity(city3);
        TourManager.addCity(city4);
        TourManager.addCity(city5);
        TourManager.addCity(city6);
        TourManager.addCity(city7);
        TourManager.addCity(city8);
        TourManager.addCity(city9);
        TourManager.addCity(city10);
        TourManager.addCity(city11);
        TourManager.addCity(city12);
        TourManager.addCity(city13);
        TourManager.addCity(city14);
        TourManager.addCity(city15);
        TourManager.addCity(city16);
        TourManager.addCity(city17);
        TourManager.addCity(city18);
        TourManager.addCity(city19);
        TourManager.addCity(city20);
        TourManager.addCity(city21);
        TourManager.addCity(city22);
        TourManager.addCity(city23);
        TourManager.addCity(city24);
        //TourManager.addCity(city25);
        /*TourManager.addCity(city26);
        TourManager.addCity(city27);
        TourManager.addCity(city28);
        TourManager.addCity(city29);
        TourManager.addCity(city30);
        TourManager.addCity(city31);
        TourManager.addCity(city32);
        TourManager.addCity(city33);
        TourManager.addCity(city34);
        TourManager.addCity(city35);
        TourManager.addCity(city36);
        TourManager.addCity(city37);
        TourManager.addCity(city38);
        TourManager.addCity(city39);
        TourManager.addCity(city40);//*/


        Collections.shuffle(destinationCities);

        return destinationCities;
    }


}
