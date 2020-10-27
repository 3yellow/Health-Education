package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class signature extends AppCompatActivity {

    Button btnsave, btnclear;
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
    }
    public void W(View v){
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
}
