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
import android.widget.ImageView;

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
    public static Bitmap canvasBitmap;
    //坐標
    private float endX , endY;

    //private ImageView mImageView;

    public mainview(Context context){
        super(context);
        initView();
    }

    public mainview(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.e("mainview1","44");
        initView();
        Log.e("mainview1","46");
    }

    public mainview(Context context, AttributeSet attrs , int defStyleAttr){
        super(context, attrs, defStyleAttr);
        Log.e("mainview1","51");
        initView();
        Log.e("mainview1","53");
    }

    private void initView()
    {
        Log.e("mainview1","58");
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(Color.BLACK);
        Log.e("mainview1","64");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        Log.e("mainview1","70");
        canvasBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);//bmp=canvasBitmap
        mCanvas = new Canvas(canvasBitmap);//canvas=mcanvas
        mCanvas.drawColor(Color.WHITE);
        //isDrawing = false;
        Log.e("mainview1","75");
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.e("mainview1","80");
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap,0,0,mPaint);
        canvas.drawPath(mPath,mPaint);
        Log.e("mainview1","84");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.e("mainview1","89");
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
        Log.e("mainview1","115");
        invalidate();
        Log.e("mainview1","117");
        return true;
    }

    private void touchMove(MotionEvent event){
        Log.e("mainview1","122");
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
        Log.e("mainview1","138");
    }

    /*public void save(View view) throws IOException {

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
    }


    public void save(){
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
       // outputStream.flush();
       // outputStream.close();
    }*/

    //\内部共享存储空间\Screenshots
    public void onclear() //清除
    {
        Log.e("mainview1","184");
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
        Log.e("mainview1","187");
    }


    public void save() {
        Log.e("mainview1", "191");
        mCanvas.save();
        Log.e("mainview1", "193");
        mCanvas.restore();
        Log.e("mainview1", "195");

        /*try {
            File file = new File("/sdcard/1.png");
            Log.e("mainview1","200");
            FileOutputStream out = new FileOutputStream(file);
            Log.e("mainview1","202");
            canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

      /*   File file=new File("/sdcard/2.png");

       if(!file.exists()) {
            file.mkdirs();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
               // canvasBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                System.out.println("saveBmp is here");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
    }
    // File file = new File(Environment.getExternalStorageDirectory().getPath() + "/share_pic.png");// 保存到sdcard根目录下，文件名为share_pic.png
       /* Log.i("CXC", Environment.getExternalStorageDirectory().getPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
           // canvasBitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("mainview1","214");*/
}
