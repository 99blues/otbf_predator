package com.example.predator.map;

//import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import com.example.predator.R;


public class MapView extends View {

	int mCount = 0;
	
	boolean xDirection = true;
	boolean yDirection = true;
	int xPos = 100;
	int yPos = 100;

	
    public MapView(Context context) {
        super(context);
    }
    
    
    private void moveTarget(){
    	int xOffset = (int)(Math.sin(Math.toRadians((double)(mCount%360)) * 100.0)) ;
    	
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
    @SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas) {
    	
    	
    	drawCanvas(canvas);
    	
    	moveTarget();
    	drawTarget(canvas, xPos, yPos, 160, Color.argb(255, 255, 0, 255), Color.argb(100, 255, 0, 255));

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
    	
    	drawMyPosition(canvas, 880, 240, 50, Color.GREEN);
    	

    }

    private void drawCanvas(Canvas canvas){
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.rgb(0xcf, 0xcf, 0xcf));
        canvas.drawRect(0,0,1280,1280,paint);

        paint.setColor(Color.rgb(255,255,255));
        canvas.drawCircle(930, 260, 200, paint);
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
        canvas.drawText(String.format("%dsec", mCount++ ), 30, 30, paint);
       
        paint.setColor(Color.argb(75, 128, 128, 255));
        
        paint.setStrokeWidth(1);
        
        for (int y = 0; y < 1280; y = y + 25) {
            canvas.drawLine(0, y, 1279, y, paint);
        }
        
        for (int x = 0; x < 1280; x = x + 25) {
            canvas.drawLine(x, 0, x, 1279, paint);
        }
    }

    private void drawTarget(Canvas canvas, int x, int y, int r, int ic, int oc){
        Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.FILL);

                
        paint.setColor(oc);
        canvas.drawCircle(x, y, r, paint);

        paint.setColor(ic);
        canvas.drawCircle(x, y, r/2, paint);
    }

    private void drawMyPosition(Canvas canvas, int x, int y, int r, int c){
    	
		Paint paint = new Paint();
    	
		Resources res = this.getContext().getResources();
		Bitmap myImage = BitmapFactory.decodeResource(res, R.drawable.bsd);
		
		canvas.drawBitmap(myImage, x, y,paint);
	}

    
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
    
