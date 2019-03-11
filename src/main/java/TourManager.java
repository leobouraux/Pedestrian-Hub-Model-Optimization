
//package simulated.annealing;

import java.util.ArrayList;

/*
* TourManager.java
* Holds and keeps track of the cities of a tour
*/


public class TourManager {

    // Holds our cities
    private static ArrayList<City> destinationCities = new ArrayList<City>();

    /**
	 * Adds a destination city
	 * @param city
	 */
	public static void addCity(City city) {
		destinationCities.add(city);
	}

	/**
	 * returns a city given its index
	 * @param index
	 * @return city the city at index
	 */
	public static City getCity(int index){
		return (City)destinationCities.get(index);
	}

	/**
	 * Returns the number of destination cities
	 * @return size the number of destination cities
	 */
	public static int numberOfCities(){
		return destinationCities.size();
	}

	public static ArrayList<City> problemInit() {
		// Create and add our cities
		City city = new City("Paris",60, 200);
		TourManager.addCity(city);
		City city2 = new City("Lyon",180, 200);
		TourManager.addCity(city2);
		City city3 = new City("La Rochelle",80, 180);
		TourManager.addCity(city3);
		City city4 = new City("Bordeaux",140, 180);
		TourManager.addCity(city4);
		City city5 = new City("Lens",20, 160);
		TourManager.addCity(city5);
		City city6 = new City("Nice",100, 160);
		TourManager.addCity(city6);
		City city7 = new City("Lille",200, 160);
		TourManager.addCity(city7);
		City city8 = new City("Rennes",140, 140);
		TourManager.addCity(city8);
		City city9 = new City("Brest",40, 120);
		TourManager.addCity(city9);
		City city10 = new City("Toulon",100, 120);
		TourManager.addCity(city10);
		City city11 = new City("Nancy",180, 100);
		TourManager.addCity(city11);
		City city12 = new City("Calais",60, 80);
		TourManager.addCity(city12);
		City city13 = new City("Besancon",110, 120);
		TourManager.addCity(city13);
		City city14 = new City("Tignes",130, 80);
		TourManager.addCity(city14);
		City city15 = new City("Lamoura",250, 60);
		TourManager.addCity(city15);
		City city16 = new City("Leimbach",70, 40);
		TourManager.addCity(city16);
		City city17 = new City("Lausanne",180, 40);
		TourManager.addCity(city17);
		City city18 = new City("Geneve",20, 30);
		TourManager.addCity(city18);
		City city19 = new City("Thyon",20, 10);
		TourManager.addCity(city19);
		City city20 = new City("Zinal",30, 10);
		TourManager.addCity(city20);
		City city21 = new City("Marseille",130, 10);
		TourManager.addCity(city21);
		City city22 = new City("Toulouse",240, 10);
		TourManager.addCity(city22);

		return destinationCities;
	}


}
