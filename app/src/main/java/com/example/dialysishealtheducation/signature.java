package com.example.dialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.dialysishealtheducation.mainview.canvasBitmap;

public class signature extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnsave, btnclear,btnsavet;
    mainview mainV;
    String nurseID=null,id=null;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=this.getIntent();
        nurseID =i.getStringExtra("nurseID");
        id=i.getStringExtra("id");
        flag=i.getIntExtra("flag",0);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
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
        modify_Patient(id);
        Log.e("signature","33");
        Intent i;
        if(flag==99)
        {
            i=new Intent(signature.this,Menu.class);
        }
        else
        {
            i=new Intent(signature.this,choose_education.class);
        }
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("flag",1);//要前測
        startActivity(i);
        finish();
        Log.e("signature","43");
    }

    public void modify_Patient(String patient_id)
    {
        //Patient (patient_id char(10) NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign INT, patient_birth DATE , patient_incharge char(10) NOT NULL,change_data DATETIME,

        ContentValues cv=new ContentValues(1);
        cv.put("patient_id",id);
        //cv.put("patient_name",name);
        //cv.put("patient_gender",gender);
        //cv.put("patient_register",date);
        //cv.put("patient_birth",birth_date);
        cv.put("patient_sign",1);
        //cv.put("change_data",date_time);
        String whereClause = "patient_id = ?";
        String whereArgs[] = {patient_id};
        db.update ("Patient", cv, whereClause, whereArgs);
        //db.replace ("Answer", null,cv);
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
