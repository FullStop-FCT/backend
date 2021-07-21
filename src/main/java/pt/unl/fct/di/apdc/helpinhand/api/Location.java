package pt.unl.fct.di.apdc.helpinhand.api;

public class Location {

	private String lat;
	private String lon;
	
	
	public Location() {
		
	}
	
	public Location(String lat, String lon) {
		this.setLat(lat);
		this.setLon(lon);
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
	
}
