package com.example.predator;

import android.util.Log;


public class StateManager {
    private static final String TAG = StateManager.class.getSimpleName();

	
	private GameState state = GameState.UNKNOWN;

	public StateManager(){
	}
	
	
	public StateManager(GameState s) {
		setState(s);
	}



	public GameState getState(){
		return state;
	}
	
	public void setState(GameState newState ){
		Log.d(TAG, "[" + state + "]-->[" + newState + "]");
		state = newState;
	}


}
