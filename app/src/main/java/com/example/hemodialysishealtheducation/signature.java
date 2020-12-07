package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.hemodialysishealtheducation.mainview.canvasBitmap;

public class signature extends AppCompatActivity {

    Button btnsave, btnclear,btnsavet;
    mainview mainV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainV = new mainview(this);
        Log.e("signature","20");
        setContentView(R.layout.activity_signature);
        Log.e("signature","22");
        mainV = (mainview) findViewById(R.id.mainview);
        Log.e("signature","24");
        btnsave = (Button) findViewById(R.id.btn_save);
        btnclear = (Button) findViewById(R.id.btn_clear);
        Log.e("signature","25");

    }
    public void clear(View view) {
        Log.e("signature","28");
        mainV.onclear();
        Log.e("signature","30");
        mainV = new mainview(this);
        setContentView(R.layout.activity_signature);
        mainV = (mainview) findViewById(R.id.mainview);
        btnsave = (Button) findViewById(R.id.btn_save);
        btnclear = (Button) findViewById(R.id.btn_clear);
    }
    public void W(View v){
        save(v);
        Log.e("signature","33");
        Intent i=this.getIntent();
        String nurseID =i.getStringExtra("nurseID");
        String id=i.getStringExtra("id");
        i=new Intent(signature.this,choose_education.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("flag",1);//要前測
        startActivity(i);
        finish();
        Log.e("signature","43");
    }


    public void save(View view){
        try {
            Log.e("signature","66");
            mainV.save();
            Log.e("signature","68");
            try {
                Log.e("signature","70");
                String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取sdcard的根路径
                //String sdpath = Environment.DIRECTORY_PICTURES.toString();
                Log.e("sd",sdpath);
                Intent intent = getIntent();
                // 通過key得到值 值為字串型別
                String pid = intent.getStringExtra("id");
                //String pname = intent.getStringExtra("patientname");
                //String filename = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));// 产生时间戳，称为文件名
                Log.e("signature","76");
                //File file = new File(sdpath +  File.separator + "Pictures/" + pname + "-" + filename + ".jpg");
                File file = new File(sdpath +  File.separator + "Pictures/" + pid + ".jpg");
                Log.e("signature","78");
                file.createNewFile();
                Log.e("signature","82");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Log.e("signature","83");
                canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);// 以100%的品质创建png

                Log.e("signature","86");
                fileOutputStream.flush();
                fileOutputStream.close();
                Log.e("signature","89");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
