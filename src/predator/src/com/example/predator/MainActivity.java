package com.example.predator;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


//import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.predator.ibeacon.BeaconManager;
import com.example.predator.communication.JsonDownloader;
import com.example.predator.player.Target;
import com.example.predator.player.Hunter;
import com.example.predator.map.PlayView;

public class MainActivity extends FragmentActivity
                          implements LoaderManager.LoaderCallbacks<JSONObject>{
	
	private static final String TAG = "Predator";
	
	private static final int ASYNC_ID_GET_BEACON = 0x0001;
	
	private final static int UPDATE_DELAY_MSEC = 3000;
	private final static int UPDATE_INTERVAL_MSEC = 1000;

	private Timer updateTimer = null;
	private Handler updateHandler = null;
	
	private StateManager stateManager = null;
	private BeaconManager beaconManager = null;
	
	private PlayView playView = null;
	private Target target = null;
	private Hunter hunter = null;
	
	private static final String KEY_URL_STR = "urlStr";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		target = new Target();
		hunter = new Hunter();
		
		stateManager = new StateManager(GameState.LOADING);
		//stateManager.setState( GameState.LOADING);
		beaconManager = new BeaconManager();
		
		playView = new PlayView(this, stateManager);
		playView.registPlayer(hunter, target);
		
        setContentView(playView);
        
        // iBeaconリスト取得（非同期）
        Bundle args = new Bundle(1);
        args.putString(KEY_URL_STR, "http://hoge.99blues.com/ibeacons.json");
        getSupportLoaderManager().initLoader(ASYNC_ID_GET_BEACON, args, this);
        
        // 画面更新
		updateHandler = new Handler();
		
		updateTimer = new Timer(false);
		updateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateHandler.post(new Runnable() {
					@Override
					public void run() {
						playView.invalidate();
					}
				});
			}
		}, UPDATE_DELAY_MSEC, UPDATE_INTERVAL_MSEC);
        
	}


    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) 
    {
    	Log.d(TAG, String.format("JSON Start id:%d", id));

    	String urlStr = args.getString(KEY_URL_STR);
        if (! TextUtils.isEmpty(urlStr)) {
            return new JsonDownloader(getApplication(), urlStr);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject json) 
    {
        // TODO 取得できた data で何かする
    	Log.d(TAG, "JSON Finish:" +  loader.getClass().getSimpleName());
    	beaconManager.load(json);
    	stateManager.setState( GameState.WAITING);
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) 
    {
        // 特に何もしない
    	Log.w(TAG, "JSON Reset");
    }
/*
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
*/
	
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
