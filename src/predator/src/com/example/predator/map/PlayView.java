package com.example.predator.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.predator.R;
import com.example.predator.GameState;
import com.example.predator.StateManager;

import com.example.predator.player.Hunter;
import com.example.predator.player.Target;

/**
 * プレイ画面
 * 
 * @author miyamura
 *
 */
public class PlayView extends View {

	private StateManager stateManager = null;
	private Target target = null;
	private Hunter hunter = null;
	
	private int mCount = 0;
	
    public PlayView(Context context, StateManager sm) {
        super(context);
        stateManager = sm;
    }
    
    public void registPlayer(Hunter h, Target t){
    	hunter = h;
    	target = t;
    }
    
    
    /**
     * 画面描画
     * 
     * 状態遷移毎に表示内容を切り替える。
     * 
     * @param canvas
     */
    @SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas)
    {
    	switch(stateManager.getState()){
    	case UNKNOWN:			// 初期状態
    	case LOADING:			// 準備中
    		drawLoading(canvas);
    		break;

    	case WAITING:			// 待機中 (ゲーム開始待ち)
    	case PLAYING:			// プレイ中
    		drawPlaying(canvas);
    		break;
    		
    	case WIN:				// 勝ち
    	case WON:				// 負け
    	case END:				// 終了	
    		drawGameOver(canvas);
    		break;
    	}
    	
    }
    
    /**
     * 初期画面を描画
     */
    private void drawLoading(Canvas canvas){
    	clearCanvas(canvas);

    	Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setTextSize(32);
        canvas.drawText("Loading...", 100, 100, paint);
    }
    
    /**
     * 終了画面を描画
     */
    private void drawGameOver(Canvas canvas){
    	clearCanvas(canvas);

    	Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setTextSize(32);
        canvas.drawText("GAME OVER", 100, 100, paint);
    }
      
    /**
     * プレイ中画面を描画
     */
    private void drawPlaying(Canvas canvas){
    	clearCanvas(canvas);
    	drawHunterRange(canvas);
   		drawTarget(canvas);
    	
    	drawGrid(canvas);
    	
    	int r = 25;
    	
    	int c1 = Color.argb(50, 0, 0, 255);
    	int c2 = Color.argb(100, 0, 0, 255);
    	int c3 = Color.argb(255, 0, 0, 255);

    	drawBeacon(canvas,  100, 100, r, c1);
    	drawBeacon(canvas,  300, 130, r, c1);
    	drawBeacon(canvas,  550, 120, r, c2);
    	drawBeacon(canvas,  800, 120, r, c3);
    	drawBeacon(canvas, 1000, 120, r, c3);
    	drawBeacon(canvas, 1150, 120, r, c2);

    	drawBeacon(canvas,  100, 500, r, c1);
    	drawBeacon(canvas,  300, 530, r, c2);
    	drawBeacon(canvas,  550, 520, r, c2);
    	drawBeacon(canvas,  800, 520, r, c3);
    	drawBeacon(canvas, 1010, 490, r, c2);
    	drawBeacon(canvas, 1190, 520, r, c1);
    	
    	drawHunter(canvas);//, 880, 240, 50, Color.GREEN);
    	

    }

    private void clearCanvas(Canvas canvas){
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.rgb(0xcf, 0xcf, 0xcf));
        canvas.drawRect(0,0,1280,1280,paint);
    }
    
    /**
     * グリッド線を描く
     * 
     * @param canvas
     */
    private void drawGrid(Canvas canvas){
        Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setTextSize(32);
        canvas.drawText(String.format("frame:%d", mCount++ ), 30, 30, paint);
       
        paint.setColor(Color.argb(75, 128, 128, 255));
        
        paint.setStrokeWidth(1);
        
        for (int y = 0; y < 1280; y = y + 25) {
            canvas.drawLine(0, y, 1279, y, paint);
        }
        
        for (int x = 0; x < 1280; x = x + 25) {
            canvas.drawLine(x, 0, x, 1279, paint);
        }
    }
    
    /**
     *  ハンターの射程範囲を描画
     *  
     * @param canvas
     */
    private void drawHunterRange(Canvas canvas ){
    	if(hunter == null){
    		return;
    	}

    	final int x = hunter.getX();
    	final int y = hunter.getY();
    	final int r = hunter.getR();
    	
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.rgb(255,255,255));
        canvas.drawCircle(x,y,r,paint);//930, 260, 200, paint);
    }
    
    
    /**
     * ターゲットを描く
     * 
     * @param canvas
     */
    private void drawTarget(Canvas canvas ){
    	if(target == null){
    		return;
    	}
    	
    	final int r = 160;
    	final int ic = Color.argb(128, 255, 0, 255);
    	final int oc = Color.argb(32, 255, 0, 255);
    	final int x = target.getX();
    	final int y = target.getY();
       	final int size = 42;
            	
        Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.FILL);

                
        paint.setColor(oc);
        canvas.drawCircle(x, y, r, paint);

        paint.setColor(ic);
        canvas.drawCircle(x, y, r/2, paint);

        Resources res = this.getContext().getResources();
		Bitmap myImage = BitmapFactory.decodeResource(res, R.drawable.target);
		canvas.drawBitmap(myImage, x-size, y-size, paint);
   }

    /**
     *  ユーザーの位置を描画
     */
    private void drawHunter(Canvas canvas){//, int x, int y, int r, int c){
    	//final int r = 50;
    	//final int c = Color.GREEN;
    	final int x = hunter.getX();
    	final int y = hunter.getY();
    	final int size = 42;
    	
		Paint paint = new Paint();
    	
		Resources res = this.getContext().getResources();
		Bitmap myImage = BitmapFactory.decodeResource(res, R.drawable.predator);
		
		canvas.drawBitmap(myImage, x-size, y-size, paint);
	}


    /**
     * ビーコンを描画
     * 
     * @TODO ビーコンマネージャから取得して描画する。
     */
    private void drawBeacon(Canvas canvas, int x, int y, int r, int c){
        Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setColor(c);

        // 円を書く
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawCircle(x, y, r, paint);
        
        // 円を書く
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, r-5, paint);

        // 中心店の確認用の線
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(2);
        
        int r2 = (r/2);// + 20;
        int yTop = y - r2;
        int yBottom = y + r2;
        int xLeft = x - r2;
        int yRight = x + r2;
        
        canvas.drawLine(xLeft, y, yRight, y, paint);
        canvas.drawLine(x, yTop, x, yBottom, paint);
   
    }
}
    
