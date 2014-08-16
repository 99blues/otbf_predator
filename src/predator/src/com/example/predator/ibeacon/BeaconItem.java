package com.example.predator.ibeacon;

import com.example.predator.common.LongLat;

/**
 * ビーコン情報
 * 
 * @author miyamura
 *
 */
public class BeaconItem {

	private final String uuid;
	private final int minor;
	private final LongLat longLat;
	
	public BeaconItem(String u, int m, LongLat l ){
		uuid = u;
		minor = m;
		longLat = l;
	}

	public String getUuid() {
		return uuid;
	}

	public int getMinor() {
		return minor;
	}

	public LongLat getLongLat() {
		return longLat;
	}
	
	public double getLongitude(){
		return longLat.getLongitude();
	}
	
	public double getLatitde(){
		return longLat.getLatitude();
	}
}
