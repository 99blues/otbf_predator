package com.example.predator;

public enum GameState {
	UNKNOWN,		// 初期状態
	LOADING,		// 準備中
	WAITING,		// 待機中 (ゲーム開始待ち)
	PLAYING,		// プレイ中
	WIN,			// 勝ち
	WON,			// 負け
	END				// 終了	

}
