package SimulatedAnnealing.TSP;/*
/*
* SimulatedAnnealing.TSP.City.java
* Models a city
*/

//package simulated.annealing;

public class City {
    private int x;
    private int y;
    private String cityName;            

    //Constructor
    //creates a city given its name and (x,y) location
	public City(String cityName, int x, int y){
    	this.cityName = cityName;
        this.x = x;
        this.y = y;
    }            
        
    /**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
