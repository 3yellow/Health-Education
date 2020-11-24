package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class show_grade extends AppCompatActivity {

    Cursor cu;
    SQLiteDatabase db;
    TextView txv_1_date,txv_1_time,txv_1_nurse,txv_2_date,txv_2_time,txv_2_nurse,txv_3_date,txv_3_time,txv_3_nurse,txv_4_date,txv_4_time,txv_4_nurse,txv_5_date,txv_5_time,txv_5_nurse;
    Button btn_1_score,btn_2_score,btn_3_score,btn_4_score,btn_5_score;
    TextView nurse,patient,title,patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grade);

    }
}
