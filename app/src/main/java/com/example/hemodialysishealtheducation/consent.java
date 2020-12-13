package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class consent extends AppCompatActivity {
    String nurseID,id,pname,health_education,eduaction;
    SQLiteDatabase db;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent i=this.getIntent();
        nurseID =i.getStringExtra("nurseID");
        id=i.getStringExtra("id");
        pname=i.getStringExtra("patientname");
        PDFView pdf=findViewById(R.id.pdfView);
        pdf.fromAsset("門診血液透析患者須知-109年.pdf").load();//壹．腎臟功能簡介.doc.pdf
    }


    public void onclick(View v){

        Intent i;
        if(flag==99)
        {
            i=new Intent(consent.this,Menu.class);
        }
        else
        {
            i=new Intent(consent.this,signature.class);
        }
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("patientname",pname);
        startActivity(i);
        finish();
    }
}
