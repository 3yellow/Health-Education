package com.example.hemodialysishealtheducation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class mainview extends View{
    //畫筆
    private final Paint mPaint = new Paint();
    //畫布
    private Canvas mCanvas;
    //路徑
    private final Path mPath = new Path();
    //承載畫布
    private static Bitmap canvasBitmap;
    //坐標
    private float endX , endY;

    public mainview(Context context){
        super(context);
        initView();
    }

    public mainview(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.e("mainview","44");
        initView();
        Log.e("mainview","46");
    }

    public mainview(Context context, AttributeSet attrs , int defStyleAttr){
        super(context, attrs, defStyleAttr);
        Log.e("mainview","51");
        initView();
        Log.e("mainview","53");
    }

    private void initView()
    {
        Log.e("mainview","58");
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(Color.BLACK);
        Log.e("mainview","64");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        Log.e("mainview","70");
        canvasBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(canvasBitmap);
        mCanvas.drawColor(Color.WHITE);
        //isDrawing = false;
        Log.e("mainview","75");
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.e("mainview","80");
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap,0,0,mPaint);
        canvas.drawPath(mPath,mPaint);
        Log.e("mainview","84");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.e("mainview","89");
        float eventX = event.getX();
        float eventY = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(eventX,eventY);
                endX = eventX;
                endY = eventY;
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(endX, endY, eventX, eventY);
                endX = eventX;
                endY = eventY;
                break;

            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
        }
        invalidate();
        return true;
    }

    private void touchMove(MotionEvent event){
        Log.e("mainview","116");
        final float x = event.getX();
        final float y = event.getY();
        final float previousX = endX;
        final float previousY = endY;
        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        if(dx >= 3 || dy >= 3)
        {
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;
            mPath.quadTo( previousX, previousY, cX, cY );
            endX = x;
            endY = y;
        }
    }

    /*public void save() throws IOException {

        Bitmap bitmap = canvasBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG,80,bos);
        byte[] buffer = bos.toByteArray();
        String pathName = Environment.DIRECTORY_PICTURES.toString();
        File savefile = new File(pathName, "test");
        if (!savefile.exists()) {
            savefile.mkdir();
        }
        String loc =savefile.getPath()+ "/"+String.valueOf(System.currentTimeMillis())+".png";
        if(buffer != null){
            OutputStream outputstream = new FileOutputStream(savefile);
            outputstream.write(buffer);
            outputstream.close();
        }
    }*/

    /*public void save(){
        OutputStream outputStream;
        Bitmap bitmap = canvasBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/Demo/");
        dir.mkdir();
        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        outputStream.flush();
        outputStream.close();
    }*/

    //\内部共享存储空间\Screenshots
    public void onclear()
    {
        Log.e("mainview","175");
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
        Log.e("mainview","178");
    }
}
