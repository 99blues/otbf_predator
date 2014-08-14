package com.example.predator.player;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class Target {
	private final static int UPDATE_DELAY_MSEC = 3000;
	private final static int UPDATE_INTERVAL_MSEC = 1000;
	
	private Timer updateTimer = null;
	private Handler updateHandler = null;
	
	private boolean xDirection = true;
	private boolean yDirection = true;
	private int xPos = 100;
	private int yPos = 100;

	
	public Target(){
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
	
	private void move(){
    	if(xDirection ){
    		xPos += 7;
    		if(500 < xPos){
    			xDirection = false;
    		}
    	}
    	else{
    		xPos -= 7;
    		if(xPos < 100){
    			xDirection = true;
    		}
    	}

    	if(yDirection ){
    		yPos += 5;
    		if(400 < yPos){
    			yDirection = false;
    		}
    	}
    	else{
    		yPos -= 4;
    		if(yPos < 50){
    			yDirection = true;
    		}
    	}
	}
}
