package com.example.predator.common;

/**
 * 座標情報
 * 
 * @author miyamura
 *
 */
public class LongLat {
	private double latitude;	/** 緯度 */
	private double longitude;	/** 経度 */

	public LongLat(){
		latitude = 0.0;
		longitude = 0.0;
	}

	public LongLat(double lat, double lng){
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
}



