package com.example.predator.common;

/**
 * 座標情報
 * 
 * @author miyamura
 *
 */
public class Location {
	private double latitude;	/** 緯度 */
	private double longitude;	/** 経度 */

	public Location(){
		latitude = 0.0;
		longitude = 0.0;
	}

	public Location(double lat, double lng){
		latitude = lat;
		longitude = lng;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}	

	public String toString()
	{
		return String.format("Latitude:%f, Longitude:%f", latitude, longitude);
	}
}



