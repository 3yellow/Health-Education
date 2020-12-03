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
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class choice_vedio extends AppCompatActivity {

    String nurseID,count,score,exam_id;
    int pad=0;
    String id;
    Cursor cu;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_vedio);

        TextView nurse=findViewById(R.id.tex_nurse_name);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent i=this.getIntent();
        count=i.getStringExtra("count");
        score=i.getStringExtra("score");
        id=i.getStringExtra("id");
        exam_id=i.getStringExtra("exam_id");
        nurseID=i.getStringExtra("nurseID");
        pad=i.getIntExtra("pad",-1);
        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" 登入");
        }
        TextView patient = findViewById(R.id.tex_patient_name);
        id=i.getStringExtra("id");
        cu.close();
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
        }
        cu.close();
    }

    public  void  back(View v){
        Intent i=new Intent(this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public  void  totheme(View v){
        Intent i=new Intent(choice_vedio.this,choose_education.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void go_pdf(View v)
    {
        Intent i=new Intent( this,choice_pdf.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void go_test(View v)
    {
        Intent i=new Intent( this,choice_test.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved(View v)
    {
        Intent i=new Intent( this,Vedio.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved_ca(View v)
    {
        Intent i=new Intent( this,vedio_ca.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved_care(View v)
    {
        Intent i=new Intent( this,vedio_care.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved_lin(View v)
    {
        Intent i=new Intent( this,vedio_lin.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved_washhand(View v)
    {
        Intent i=new Intent( this,vedio_washhand.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void ved_weight(View v)
    {
        Intent i=new Intent( this,vedio_weight.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",count);
        i.putExtra("score",score);
        i.putExtra("nurseID",nurseID);
        i.putExtra("exam_id",exam_id);
        startActivity(i);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choice_vedio.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choice_vedio.this,MainActivity.class);
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
}
