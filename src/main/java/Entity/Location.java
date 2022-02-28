package Entity;

/**
 * Entity class for {@code Location}.
 */
public class Location {

	/**
	 * String containing the name of the {@code Location}.
	 */
	private String name;
	
	/**
	 * Double containing the latitude of the {@code Location}.
	 */
	private double latitude;
	
	/**
	 * Double containing the longitude of the {@code Location}.
	 */
	private double longitude;
	
	/**
	 * Creates a new {@code Location} with a specified name, latitude
	 * and longitude.
	 * 
	 * @param name 		name of the {@code Location}.
	 * @param latitude	latitude of the {@code Location}.
	 * @param longitude longitude of the {@code Location}.
	 */
	public Location(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Location() {
	}

	/**
	 * Returns the name of the {@code Location}.
	 * @return {@code Location} name in {@code String}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the latitude of the {@code Location}.
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	/*
	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}	
	*/
	public void print(){
		System.out.println("Name: "+ this.name+ " Longitude: "+ this.longitude+" Latitude: "+this.latitude);
	}
}
