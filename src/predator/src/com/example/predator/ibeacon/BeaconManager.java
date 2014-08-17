package com.example.predator.ibeacon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import android.util.Log;

/**
 * iBeaconリストの管理
 * 
 * @author miyamura
 *
 */
public class BeaconManager {

    private static final String TAG = BeaconManager.class.getSimpleName();
	
	Map<Integer,BeaconItem> map = null;
			
	public BeaconManager(){
	}
	
	public boolean isLoaded(){
		return (map != null && !map.isEmpty());
	}
	
	/**
	 * iBeaconリストの読み込み
	 * 
	 * @param json   iBeaconリスト定義ファイル
	 * @return
	 */
	public boolean load(JSONObject json)
	{
	    map = new HashMap<Integer,BeaconItem>();
		
	    try {
			final JSONArray items = json.getJSONArray("ibeacons");
			final int count = items.length();
			
			for(int i=0; i < count; i++){
				try{
					final JSONObject o = items.getJSONObject(i);
					
					final String uuid = o.getString("uuid");
					final int minor = o.getInt("minor");
					final double lat = o.getDouble("latitude");
					final double lng = o.getDouble("longitude");
				
					BeaconItem beacon = new BeaconItem(uuid, minor, lat, lng);
					
					Log.d(TAG, String.format("[%d] iBeacon:{%s}", i, beacon.toString()));
					
					map.put(new Integer(minor), beacon);
				}
				catch (JSONException e ){
					// １件だけのエラーなら、該当レコードのみスキップ
					Log.e(TAG, String.format("Skip rec:%d ...invalid format", i ));
		            Log.e(TAG, e.toString());
				}
			}
			Log.d(TAG, String.format("regist %d beacons", map.size()));
		}
	    catch (JSONException e) {
			// 全体の書式エラーなら失敗
	    	map = null;
            Log.e(TAG, e.toString());
		}
	    
	    return isLoaded();
	}
}
