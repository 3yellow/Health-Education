package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class consent extends AppCompatActivity {
    String nurseID,id,pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        Intent i=this.getIntent();
        nurseID =i.getStringExtra("nurseID");
        id=i.getStringExtra("id");
        pname=i.getStringExtra("patientname");
    }
    public void onclick(View v){

        Intent i=new Intent(consent.this,signature.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("patientname",pname);
        startActivity(i);
        finish();
    }
}
