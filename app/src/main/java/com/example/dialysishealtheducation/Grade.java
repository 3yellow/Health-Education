package com.example.dialysishealtheducation;

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
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Grade extends AppCompatActivity {

    String nurseID=null,id=null ,ed_name_chinese=null,ed_name_ec=null;
    String ex_id_1=null,ex_id_2=null,ex_id_3=null,ex_id_4=null,ex_id_5=null;
    Cursor cu=null;
    SQLiteDatabase db;
    Button btn_1_date=null,btn_2_date=null,btn_3_date=null,btn_4_date=null,btn_5_date=null;
    TextView txt_1_time=null,txt_2_time=null,txt_3_time=null,txt_4_time=null,txt_5_time=null;
    TextView txt_1_score=null,txt_2_score=null,txt_3_score=null,txt_4_score=null,txt_5_score=null;
    TextView txt_1_nurse=null,txt_2_nurse=null,txt_3_nurse=null,txt_4_nurse=null,txt_5_nurse=null;
    TextView nurse=null,patient=null,title=null,patient_id=null;
    TableRow linearLayout2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        String exam_id=null;
        int count_exam=0;
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        init_element();
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id");
        ed_name_ec=intent.getStringExtra("ed_name_ec");
        ed_name_chinese=intent.getStringExtra("ed_name_chinese");

        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" 登入");
            nurse.setTextSize(30);
        }
        cu.close();
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
            patient_id.setTextSize(30);
        }
        cu.close();
        //Topic (topic_id char(10), topic_name TEXT,change_data DATETIME

        //String[] tokens=ed_name_chinese.split(".");
        title.setText(ed_name_chinese);
        title.setTextSize(45);
        patient.setTextSize(30);
        patient_id.setText(id);
        patient_id.setTextSize(30);
        title.setText(ed_name_chinese);
        title.setTextSize(45);

        exam_id=ed_name_ec+id;
        Cursor c = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%' AND exam_score!=-1",null);//要找總共有幾張考卷 才知道要顯示的是哪幾張
        if(c.getCount()>0) {
            c.moveToFirst();
            count_exam=c.getCount();
            count_exam-=1;
            show(exam_id,count_exam);
        }
        else
        {
            title.setVisibility(View.VISIBLE);
            btn_1_date.setVisibility(View.INVISIBLE); // 隱藏
            linearLayout2.setVisibility(View.INVISIBLE);
            patient.setVisibility(View.INVISIBLE);
            title.setText("您還沒有做過任何測驗");
            btn_5_date.setVisibility(View.INVISIBLE); // 隱藏
            btn_4_date.setVisibility(View.INVISIBLE); // 隱藏
            btn_3_date.setVisibility(View.INVISIBLE); // 隱藏
            btn_2_date.setVisibility(View.INVISIBLE); // 隱藏
        }
        c.close();


        btn_1_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Grade.this,show_grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("examid",ex_id_1);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_ec",ed_name_ec);
                intent.putExtra("ed_name_chinese",ed_name_chinese);
                startActivity(intent);
                finish();
            }
        });
        btn_2_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Grade.this,show_grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("examid",ex_id_2);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_ec",ed_name_ec);
                intent.putExtra("ed_name_chinese",ed_name_chinese);
                startActivity(intent);
                finish();
            }
        });
        btn_3_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Grade.this,show_grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("examid",ex_id_3);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_ec",ed_name_ec);
                intent.putExtra("ed_name_chinese",ed_name_chinese);
                startActivity(intent);
                finish();
            }
        });
        btn_4_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Grade.this,show_grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("examid",ex_id_4);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese",ed_name_chinese);
                intent.putExtra("ed_name_ec",ed_name_ec);
                startActivity(intent);
                finish();
            }
        });
        btn_5_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Grade.this,show_grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("examid",ex_id_5);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_ec",ed_name_ec);
                intent.putExtra("ed_name_chinese",ed_name_chinese);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void show(String exam_id,int count_exam)
    {
        // cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);

        //Answer (answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data DATETIME,
        String date=null,nurse=null,nurse_name=null;
        int score=0,i=2,count=4;
        String exam=null;
        while (count>count_exam)
        {

            if(count==4)
                btn_5_date.setVisibility(View.INVISIBLE); // 隱藏
            else if(count==3)
                btn_4_date.setVisibility(View.INVISIBLE); // 隱藏
            else if(count==2)
                btn_3_date.setVisibility(View.INVISIBLE); // 隱藏
            else if(count==1)
                btn_2_date.setVisibility(View.INVISIBLE); // 隱藏
            count--;
        }
        //Nurse (nurse_id char(10) NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL,change_data DATETIME

        exam=exam_id+"0";
        Cursor cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam+"'",null);
        if (cu.getCount()>0)
        {
            cu.moveToFirst();
            score=cu.getInt(2);
            date=cu.getString(1);
            nurse=cu.getString(4);
            Cursor  c=db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurse+"'",null);
            if(c.getCount()>0)
            {
                c.moveToFirst();
                nurse_name=c.getString(1);
            }
            c.close();
            ex_id_1=exam;
            btn_1_date.setText(date);
            txt_1_score.setText(""+score);
            txt_1_time.setText("前測");
            txt_1_nurse.setText(nurse_name);
            count--;
        }
        count+=1;
        cu.close();
        while (count>0)
        {
             exam=exam_id+count_exam;
            cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam+"'",null);
            if (cu.getCount()>0)
            {
                cu.moveToFirst();
                score=cu.getInt(2);
                if(score==-1)
                {
                    count_exam-=1;
                    if(count==4)
                        btn_5_date.setVisibility(View.INVISIBLE); // 隱藏
                    else if(count==3)
                        btn_4_date.setVisibility(View.INVISIBLE); // 隱藏
                    else if(count==2)
                        btn_3_date.setVisibility(View.INVISIBLE); // 隱藏
                    else if(count==1)
                        btn_2_date.setVisibility(View.INVISIBLE); // 隱藏
                    count--;
                }
                else
                {
                    date=cu.getString(1);
                    nurse=cu.getString(4);
                    if(i==2)
                    {
                        ex_id_2=exam;
                        btn_2_date.setText(date);
                        txt_2_score.setText(""+score);
                        txt_2_time.setText("後測第"+count_exam+"次");
                        txt_2_nurse.setText(nurse_name);
                    }
                    else if(i==3)
                    {
                        ex_id_3=exam;
                        btn_3_date.setText(date);
                        txt_3_score.setText(""+score);
                        txt_3_time.setText("後測第"+count_exam+"次");
                        txt_3_nurse.setText(nurse_name);
                    }
                    else if(i==4)
                    {
                        ex_id_4=exam;
                        btn_4_date.setText(date);
                        txt_4_score.setText(""+score);
                        txt_4_time.setText("後測第"+count_exam+"次");
                        txt_4_nurse.setText(nurse_name);
                    }
                    else if(i==5)
                    {
                        ex_id_5=exam;
                        btn_5_date.setText(date);
                        txt_5_score.setText(""+score);
                        txt_5_time.setText("後測第"+count_exam+"次");
                        txt_5_nurse.setText(nurse_name);
                    }
                    i++;
                    count--;
                    count_exam-=1;
                }
            }
            cu.close();

        }
    }

    protected void init_element()
    {
        linearLayout2=findViewById(R.id.linearLayout2);
        btn_1_date=findViewById(R.id.btn_1_date);
        btn_2_date=findViewById(R.id.btn_2_date);
        btn_3_date=findViewById(R.id.btn_3_date);
        btn_4_date=findViewById(R.id.btn_4_date);
        btn_5_date=findViewById(R.id.btn_5_date);
        txt_1_time=findViewById(R.id.btn_1_time);
        txt_2_time=findViewById(R.id.btn_2_time);
        txt_3_time=findViewById(R.id.btn_3_time);
        txt_4_time=findViewById(R.id.btn_4_time);
        txt_5_time=findViewById(R.id.btn_5_time);
        txt_1_score=findViewById(R.id.btn_1_score);
        txt_2_score=findViewById(R.id.btn_2_score);
        txt_3_score=findViewById(R.id.btn_3_score);
        txt_4_score=findViewById(R.id.btn_4_score);
        txt_5_score=findViewById(R.id.btn_5_score);
        txt_1_nurse=findViewById(R.id.btn_1_nurse);
        txt_2_nurse=findViewById(R.id.btn_2_nurse);
        txt_3_nurse=findViewById(R.id.btn_3_nurse);
        txt_4_nurse=findViewById(R.id.btn_4_nurse);
        txt_5_nurse=findViewById(R.id.btn_5_nurse);
        nurse=findViewById(R.id.nurse_id);
        patient=findViewById(R.id.patient_id);
        title=findViewById(R.id.textView14);
        patient_id=findViewById(R.id.tev_id);
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Grade.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Grade.this,MainActivity.class);
                        db.close();
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("取消",null).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(18);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(18);
            mTitleView.setTextColor(Color.RED);
            //通过反射修改message字体大小和颜色
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public  void  back(View v){
        Intent i=new Intent( this,choose_education.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        db.close();
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
