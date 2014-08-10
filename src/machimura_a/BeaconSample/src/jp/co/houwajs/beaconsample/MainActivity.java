package jp.co.houwajs.beaconsample;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.co.houwajs.beacon.Beacon;
import jp.co.houwajs.beacon.BeaconManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity{
	private static final String TAG = MainActivity.class.getName();
	
	private BeaconManager beaconManager;
	public BeaconManager getBeaconManager(){return this.beaconManager;}
	
	private FileChannel activeFileChannel;
	private ScheduledExecutorService recordExecutor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_action_record){
			if(item.isChecked()){
				this.stopRecordData();
				item.setTitle(R.string.menu_title_rec_start);
				item.setChecked(false);
			}else{
				this.startRecordData();
				item.setTitle(R.string.menu_title_rec_stop);
				item.setChecked(true);
			}
				
			return true;
		}
		return false;
	}

	
	/** データ書き込みの開始 */
	private void startRecordData(){
		try{
			int samplingPerSec = 1;
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.getDefault());
			String fileNamePrefix = dateFormat.format(new Date());
			File file = new File(this.getExternalFilesDir(null), fileNamePrefix + ".csv");
			Log.d(TAG, "create record data :" + file.toString());

			this.activeFileChannel = (new FileOutputStream(file)).getChannel();
		
			this.recordExecutor = Executors.newSingleThreadScheduledExecutor();
			this.recordExecutor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run(){
					try{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS", Locale.getDefault());
						Date now = new Date();
						
						List<Beacon> beaconList = getBeaconManager().getScannedBeaconList();
						for(Beacon beacon : beaconList){
							StringBuilder lineStr = new StringBuilder();
							
							//測定日時
							lineStr.append(dateFormat.format(now)).append(",");
							//UUID
							lineStr.append(beacon.getUuid()).append(",");;
							//Major
							lineStr.append(beacon.getMajor()).append(",");
							//Minor
							lineStr.append(beacon.getMinor()).append(",");
							//rssi
							lineStr.append(beacon.getRssi()).append(",");
							//distance
							lineStr.append(beacon.getDistance());
							
							//改行
							lineStr.append("\n");
							
							//書き込み
							if(activeFileChannel != null && activeFileChannel.isOpen())
								activeFileChannel.write(Charset.forName("UTF-8").encode(lineStr.toString()));
						}
												
					}catch(Exception ex){
						Log.e(TAG, "データ記録時の例外", ex);
					}
				}
			}, 0, 1000/samplingPerSec, TimeUnit.MILLISECONDS);
		}catch(Exception e){
			Log.e(TAG, "記録開始に失敗", e);
			throw new RuntimeException("記録開始に失敗", e);
		}
	}
	
	/** データ記憶の終了 */
	private void stopRecordData(){		
		try{
			if(this.activeFileChannel != null){
				this.activeFileChannel.close();
				this.activeFileChannel = null;
			}
			if(this.recordExecutor != null){
				this.recordExecutor.shutdown();
				this.recordExecutor = null;
			}
		}catch (Exception e) {
			Log.e(TAG, "データ記録の終了に失敗", e);
			throw new RuntimeException("データ記録の終了に失敗", e);
		}
	}
	
	private void showBeaconListFragment(){
		Fragment fragment = new BeaconListFragment();
		FragmentTransaction tran = this.getFragmentManager().beginTransaction();
		tran.replace(R.id.main_main_contents_placeholder, fragment);
		tran.commit();
	}
	
	private void sendBeaconList(){
		List<Beacon> beaconList = new LinkedList<Beacon>();
		
	}
	
	/* (非 Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if(this.beaconManager == null){
			this.beaconManager = new BeaconManager(this);
			this.beaconManager.startBeaconScan();
		}
		
		this.showBeaconListFragment();
	}

	/* (非 Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		this.stopRecordData();
		
		if(this.beaconManager != null){
			this.beaconManager.stopBeaconScan();
			this.beaconManager = null;
		}
		
		super.onStop();
	}

	
	
}
