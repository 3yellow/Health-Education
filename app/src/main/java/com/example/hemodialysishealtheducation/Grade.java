package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Grade extends AppCompatActivity {

    String nurseID=null,id=null,ed_name_chinese=null,ed_name_ec=null;
    Cursor cu;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"

        TextView nurse=findViewById(R.id.nurse_id);
        TextView patient=findViewById(R.id.patient_id);
        TextView title=findViewById(R.id.textView14);
        TextView patient_id=findViewById(R.id.tev_id);
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id"); ;
        ed_name_chinese=intent.getStringExtra("ed_name_chinese");
        ed_name_ec=intent.getStringExtra("ed_name_ec");

        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" 登入");
            nurse.setTextSize(25);
        }
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
            patient_id.setTextSize(25);
        }
        patient_id.setText(id);
        patient_id.setTextSize(25);
        title.setText(ed_name_chinese);
        title.setTextSize(40);

    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Grade.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Grade.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("取消",null).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(26);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(26);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(32);
            mTitleView.setTextColor(Color.RED);
            //通过反射修改message字体大小和颜色
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public  void  back(View v){
        Intent i=new Intent( this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        startActivity(i);
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
