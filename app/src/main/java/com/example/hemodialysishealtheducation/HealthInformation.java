package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

public class HealthInformation extends AppCompatActivity {

    SQLiteDatabase db;
    String nurseID,exam_id,health_education;
    String id;
    String eduaction=null,vido=null;
    int index=0,count,score,flag9=0;
    Button vido_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent intent = this.getIntent();
        nurseID = intent.getStringExtra("nurseID");
        id = intent.getStringExtra("id");
        flag9=intent.getIntExtra("flag",0);
        exam_id = intent.getStringExtra("exam_id");
        count = intent.getIntExtra("count", -1);
        score = intent.getIntExtra("score", -1);
        health_education = intent.getStringExtra("health_education");
        // btn_3_date=findViewById(R.id.btn_3_date);
        vido_btn=findViewById(R.id.btn_vido);

        // Topic (topic_id char(10), topic_name TEXT,change_data DATETIME, vidio int
        Cursor cu=db.rawQuery("SELECT * FROM Topic WHERE topic_id='"+health_education+"'",null);
        if (cu.getCount()>0) {
            cu.moveToFirst();
            eduaction=cu.getString(1);
            vido=cu.getString(2);
        }
        PDFView pdf=findViewById(R.id.pdfView);
        pdf.fromAsset(eduaction).load();
        if(!vido.equals("0"))
        {
            vido_btn.setVisibility(View.VISIBLE);
            vido_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (health_education)
                    {
                        case "t6":
                           Intent intent=new Intent(HealthInformation.this,Vedio.class);
                            intent.putExtra("count",count);
                            intent.putExtra("score",score);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("id",id);
                            intent.putExtra("exam_id",exam_id);
                            intent.putExtra("flag",1);
                            startActivity(intent);
                            break;
                        case "t8":
                            intent=new Intent(HealthInformation.this,vedio_care.class);
                            intent.putExtra("count",count);
                            intent.putExtra("score",score);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("id",id);
                            intent.putExtra("exam_id",exam_id);
                            intent.putExtra("flag",1);
                            startActivity(intent);
                            break;
                        case "t12":
                            intent=new Intent(HealthInformation.this,vedio_weight.class);
                            intent.putExtra("count",count);
                            intent.putExtra("score",score);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("id",id);
                            intent.putExtra("exam_id",exam_id);
                            intent.putExtra("flag",1);
                            startActivity(intent);
                            break;
                        case "t13":
                            intent=new Intent(HealthInformation.this,vedio_lin.class);
                            intent.putExtra("count",count);
                            intent.putExtra("score",score);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("id",id);
                            intent.putExtra("exam_id",exam_id);
                            intent.putExtra("flag",1);
                            startActivity(intent);
                            break;
                        case "t14":
                            intent=new Intent(HealthInformation.this,vedio_ca.class);
                            intent.putExtra("count",count);
                            intent.putExtra("score",score);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("id",id);
                            intent.putExtra("exam_id",exam_id);
                            intent.putExtra("flag",1);
                            startActivity(intent);
                            break;
                    }
                }
            });

        }
    }

    public void tofronttest(View v){
        String Q_array[]=new String[5];
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id");
        String exam_id=intent.getStringExtra("exam_id");
        String health_education=intent.getStringExtra("health education");
        int score=intent.getIntExtra("score",0);
        int count=intent.getIntExtra("count",0);
        Q_array=intent.getStringArrayExtra("Q_array");
        if(flag9==1)
        {
            intent=new Intent(HealthInformation.this,choice_pdf.class);

        }
        else if(flag9==99)
        {
            intent=new Intent(HealthInformation.this,MainActivity.class);
        }
        else
        {
            intent=new Intent(HealthInformation.this,backtest.class);
        }
        intent.putExtra("count",count);
        intent.putExtra("score",score);
        intent.putExtra("nurseID",nurseID);
        intent.putExtra("id",id);
        intent.putExtra("exam_id",exam_id);
        intent.putExtra("Q_array",Q_array);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }
}
