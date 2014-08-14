package com.example.predator;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.example.predator.map.MapView;
import com.example.predator.player.Target;
import com.example.predator.player.Hunter;

public class MainActivity extends Activity {

	private UpdateHandler mUpdate = new UpdateHandler(); 
	
	private com.example.predator.map.MapView mapView = null;
	private com.example.predator.player.Target target = null;
	private com.example.predator.player.Hunter hunter = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		target = new Target();
		hunter = new Hunter();
		
		mapView = new com.example.predator.map.MapView(this);
		mapView.registPlayer(hunter, target);
		
        setContentView(mapView);
        mUpdate.sleep(0);
        /*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}

	public class UpdateHandler extends Handler {
	    @Override
	    public void handleMessage(Message msg) {
	    	mapView.invalidate();
	    	sleep(500);
	    }
	    //スリープメソッド
	    public void sleep(long delayMills) {
	        removeMessages(0);
	        sendMessageDelayed(obtainMessage(0),1000);
	    }
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
