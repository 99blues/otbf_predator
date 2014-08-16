package com.example.predator.ibeacon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


public class BeaconManager {

	
	Map<Integer,String> map = null;
			
	public BeaconManager(){
	}
	
	public boolean isLoaded(){
		return (map != null && !map.isEmpty());
	}
	
	public boolean load(String url){
		map = null;
		 
		DefaultHttpClient cli = new DefaultHttpClient();

		HttpGet req = new HttpGet(url);//"http://hoge.99blues.com/ibeacons.json");
		HttpResponse resp = null;
		  
		try {
			resp = cli.execute(req);
		}
		catch (ClientProtocolException e) {
			Log.e("JSON", e.toString());
		    return false;
		}
		catch (IOException e) {
		    Log.e("JSON", e.toString());
		    return false;
		}
		catch (Exception e) {
		    Log.e("JSON", e.toString());
		    return false;
		}
		 
		int status = resp.getStatusLine().getStatusCode();
		if (HttpStatus.SC_OK == status) {
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			    resp.getEntity().writeTo(outputStream);
			    
			    String str = outputStream.toString(); // JSONデータ
			    Log.d("JSON", str);
				
			    //map = new HashMap<Integer,String>();
			}
			catch (Exception e) {
				Log.e("JSON", "Error");
				return false;
			}
		}
		else {
			Log.e("JSON", "Status" + status);
			return false;
		}
		return true;
	}
}
