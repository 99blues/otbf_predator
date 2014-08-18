package com.example.predator.player;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

import android.util.Log;

public class Hunter {
    private static final String TAG = Hunter.class.getSimpleName();

    private final static int UPDATE_DELAY_MSEC = 3000;
	private final static int UPDATE_INTERVAL_MSEC = 1000;
	private final static int MAX_R = 160;
	private final static int DEFAULT_X = 800;
	private final static int DEFAULT_Y = 240;
	
	//private final static int MIN_R = 30;
	
	private Timer updateTimer = null;
	private Handler updateHandler = null;

	private int xPos = DEFAULT_X;
	private int yPos = DEFAULT_Y;
	//private int r = 160;
	
	private int hp = 30;
	private int enCount = 0;
	
	public Hunter(){
		updateHandler = new Handler();
		
		updateTimer = new Timer(false);
		updateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateHandler.post(new Runnable() {
					@Override
					public void run() {
						move();
					}
				});
			}
		}, UPDATE_DELAY_MSEC, UPDATE_INTERVAL_MSEC);
	}
	
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}

	public int getR(){
		return MAX_R * hp / 100;
	}
	
	private void move(){
		enCount+=3;
		hp = Math.max(30, (int)(Math.abs(Math.cos(Math.toRadians((double)enCount))) * 100.0));
		//Log.d(TAG, String.format("hunter:%d", hp));
		
		xPos = DEFAULT_X + (int)(Math.cos(Math.toRadians((double)enCount)) * 100.0);
		yPos = DEFAULT_Y + (int)(Math.cos(Math.toRadians((double)enCount+45)) * 100.0);
	}
}
