package com.example.predator.ibeacon;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.predator.common.Location;

/**
 * 1つのビーコン情報
 * 
 * @author miyamura
 *
 */
public class BeaconItem {
    private static final String TAG = BeaconItem.class.getSimpleName();

	private final String uuid;										// UUID
	private final int minor;										// マイナー番号
	private final com.example.predator.common.Location location;	// 座標情報(緯度、経度)
	
	public BeaconItem(String uuid, int minor, com.example.predator.common.Location location )
	{
		this.uuid = uuid;
		this.minor = minor;
		this.location = location;
	}

	public BeaconItem(String uuid, int minor, double lat, double lng )
	{
		this.uuid = uuid;
		this.minor = minor;
		this.location = new com.example.predator.common.Location(lat, lng);
	}


	public String getUuid() {
		return uuid;
	}

	public int getMinor() {
		return minor;
	}

	public Location getLocation() {
		return location;
	}
	
	public double getLongitude(){
		return location.getLongitude();
	}
	
	public double getLatitde(){
		return location.getLatitude();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("uuid:").append(uuid)
		  .append(", minor:").append(minor)
		  .append(", Location{").append(location).append("}");
		
		return sb.toString();
	}

	
}
