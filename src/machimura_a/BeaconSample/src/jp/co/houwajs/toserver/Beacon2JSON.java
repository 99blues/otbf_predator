package jp.co.houwajs.toserver;

import java.util.List;

import com.google.gson.Gson;

import jp.co.houwajs.beacon.Beacon;

public class Beacon2JSON {
	
	public static String TokenizeToJSON(List<Beacon> beaconList){
		Gson gson = new Gson();
		String Json = gson.toJson(beaconList);
		return Json;
	}
}
