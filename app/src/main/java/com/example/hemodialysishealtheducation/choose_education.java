package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class choose_education extends AppCompatActivity {

    SQLiteDatabase db;
    static final String Nurse="nurse"; //database table name
    static final String Patient="patient"; //database table name
    TextView t1_date,t2_date,t3_date,t4_date,t5_date,t6_date,t7_date,t8_date,t9_date,t10_date,t11_date,t12_date,t13_date,t14_date;
    TextView t1_grade,t2_grade,t3_grade,t4_grade,t5_grade,t6_grade,t7_grade,t8_grade,t9_grade,t10_grade,t11_grade,t12_grade,t13_grade,t14_grade;
    //衛教資料按鈕
    Button one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen;
    ImageButton ib1,ib2,ib3,ib4;

   //顯示成績按鈕
    Button t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14;
    Cursor cu;
    String nurseID;
    int pad=0;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);
        init_element();

        ib1.setBackgroundResource(R.drawable.choicetheme11);

        TextView nurse=findViewById(R.id.tex_nurse_name);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent i=this.getIntent();
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
        button(id);
        show_grade(id);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hendl action here eg
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","壹.腎臟估能簡介");
                intent.putExtra("ed_name_ec","t1");
                startActivity(intent);
                finish();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","貳.甚麼是慢性腎臟病");
                intent.putExtra("ed_name_ec","t2");
                startActivity(intent);
                finish();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","叁.腎衰竭的原因");
                intent.putExtra("ed_name_ec","t3");
                startActivity(intent);
                finish();
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","肆.腎衰竭的治療方式");
                intent.putExtra("ed_name_ec","t4");
                startActivity(intent);
                finish();
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","伍.如何保護自己的腎");
                intent.putExtra("ed_name_ec","t5");
                startActivity(intent);
                finish();
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","陸.血液透析");
                intent.putExtra("ed_name_ec","t6");
                startActivity(intent);
                finish();
            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","柒.血管通路");
                intent.putExtra("ed_name_ec","t7");
                startActivity(intent);
                finish();
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","捌.動靜脈廔管的照護");
                intent.putExtra("ed_name_ec","t8");
                startActivity(intent);
                finish();
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","玖.皮膚搔癢該怎麼辦");
                intent.putExtra("ed_name_ec","t9");
                startActivity(intent);
                finish();
            }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","拾.透析患者睡眠問題");
                intent.putExtra("ed_name_ec","t10");
                startActivity(intent);
                finish();
            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","拾壹.腎友如何預防便秘");
                intent.putExtra("ed_name_ec","t11");
                startActivity(intent);
                finish();
            }
        });

        t12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","拾貳.乾體重");
                intent.putExtra("ed_name_ec","t12");
                startActivity(intent);
                finish();
            }
        });

        t13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","拾叁.磷是啥");
                intent.putExtra("ed_name_ec","t13");
                startActivity(intent);
                finish();
            }
        });

        t14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choose_education.this,Grade.class);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",id);
                intent.putExtra("ed_name_chinese","拾肆.鉀啥咪");
                intent.putExtra("ed_name_ec","t14");
                startActivity(intent);
                finish();
            }
        });

    }

    protected void show_grade(String id)
    {
        show_t1( id,"t1");
        show_t2( id,"t2");
        show_t3( id,"t3");
        show_t4( id,"t4");
        show_t5( id,"t5");
        show_t6( id,"t6");
        show_t7( id,"t7");
        show_t8( id,"t8");
        show_t9( id,"t9");
        show_t10( id,"t10");
        show_t11( id,"t11");
        show_t12( id,"t12");
        show_t13( id,"t13");
        show_t14( id,"t14");
    }

    public void show_t1(String id,String s_p){
        //kindney_function_date=findViewById(R.id.kindney_function_date);
        //kindney_function_grade=findViewById(R.id.kindney_function_grade);
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t1_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t1_grade.setText("上次測驗沒有做完!!");
                t1_grade.setTextSize(16);
            }
            else
                t1_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t2(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t2_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t2_grade.setText("上次測驗沒有做完!!");
                t2_grade.setTextSize(16);
            }
            else
                t2_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t3(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t3_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t3_grade.setText("上次測驗沒有做完!!");
                t3_grade.setTextSize(16);
            }
            else
                t3_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t4(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t4_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t4_grade.setText("上次測驗沒有做完!!");
                t4_grade.setTextSize(16);
            }
            else
                t4_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t5(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t5_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t5_grade.setText("上次測驗沒有做完!!");
                t5_grade.setTextSize(16);
            }
            else
                t5_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t6(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t6_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t6_grade.setText("上次測驗沒有做完!!");
                t6_grade.setTextSize(16);
            }
            else
                t6_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t7(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t7_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t7_grade.setText("上次測驗沒有做完!!");
                t7_grade.setTextSize(16);
            }
            else
                t7_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t8(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t8_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t8_grade.setText("上次測驗沒有做完!!");
                t8_grade.setTextSize(16);
            }
            else
                t8_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t9(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t9_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t9_grade.setText("上次測驗沒有做完!!");
                t9_grade.setTextSize(16);
            }
            else
                t9_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t10(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t10_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t10_grade.setText("上次測驗沒有做完!!");
                t10_grade.setTextSize(16);
            }
            else
                t10_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t11(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t11_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t11_grade.setText("上次測驗沒有做完!!");
                t11_grade.setTextSize(16);
            }
            else
                t11_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t12(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t12_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t12_grade.setText("上次測驗沒有做完!!");
                t12_grade.setTextSize(16);
            }
            else
                t12_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t13(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t13_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t13_grade.setText("上次測驗沒有做完!!");
                t13_grade.setTextSize(16);
            }
            else
                t13_grade.setText(" "+grade);
        }
        cu.close();
    }

    public void show_t14(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        cu.close();
        count=count-1;
        if(count<0)
        {
            count=0;
        }
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            t14_date.setText("  "+date);
            if(grade.equals("-1"))
            {
                t14_grade.setText("上次測驗沒有做完!!");
                t14_grade.setTextSize(16);
            }
            else
                t14_grade.setText(" "+grade);
        }
        cu.close();
    }

    protected void button(String id)
    {
        String examid=null;
        //按鈕顏色
        examid="t1"+id;
        button_color( examid,one);
        examid="t2"+id;
        button_color( examid,two);
        examid="t3"+id;
        button_color( examid,three);
        examid="t4"+id;
        button_color( examid,four);
        examid="t5"+id;
        button_color( examid,five);
        examid="t6"+id;
        button_color( examid,six);
        examid="t7"+id;
        button_color( examid,seven);
        examid="t8"+id;
        button_color( examid,eight);
        examid="t9"+id;
        button_color( examid,nine);
        examid="t10"+id;
        button_color( examid,ten);
        examid="t11"+id;
        button_color( examid,eleven);
        examid="t12"+id;
        button_color( examid,twelve);
        examid="t13"+id;
        button_color( examid,thirteen);
        examid="t14"+id;
        button_color( examid,fourteen);
    }

    protected void button_color(String examid,Button btn_id)
    {
        int exams_count=-1;
        ////Answer (answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data DATETIME,
        //Exam (exam_id TEXT, exam_date DateTime, exam_score INT, patient_id char(10), nurse_id char(10),change_data DATETIME
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+examid+"%'",null);
        if(cu.getCount()>0)//沒有做過考卷
        {
            exams_count=cu.getCount();
            cu.close();
            int count=cu.getCount();
            count-=1;
            if(count<0)
                count=0;
            String ans_id=examid+count;
            //Patient WHERE patient_id='"+id+"' ",
            cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+ans_id+"' AND exam_score='"+"-1"+"' ",null);
            if(cu.getCount()>0)//做到一半 沒有做完後測
            {
                if(exams_count<2)
                {
                    btn_id.setBackgroundColor(Color.parseColor("#e2e7bf"));
                    btn_id.setTextColor(Color.parseColor("#58b19f"));
                }
                else if(exams_count==2) {
                    //綠
                    btn_id.setBackgroundColor(Color.parseColor("#96e8d7"));
                    btn_id.setTextColor(Color.parseColor("#FFFAFA"));
                }
                else if(exams_count==3)
                {
                    //紫/藍
                    btn_id.setBackgroundColor(Color.parseColor("#d0dfe6"));
                    btn_id.setTextColor(Color.parseColor("#58b19f"));
                }
                else if(exams_count>3)
                {
                    //紅
                    btn_id.setBackgroundColor(Color.parseColor("#FEDFE1"));
                    btn_id.setTextColor(Color.parseColor("#58b19f"));
                }
                cu.close();
            }
            else//已經做過後測
            {
                cu.close();
                cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+ans_id+"' AND exam_score!='"+"-1"+"' ",null);
                //exams_count=cu.getCount();
                if(exams_count<2)
                {
                    btn_id.setBackgroundColor(Color.parseColor("#f7f1e3"));
                    btn_id.setTextColor(Color.parseColor("#58b19f"));
                }
                else if(exams_count==2) {
                //綠
                    btn_id.setBackgroundColor(Color.parseColor("#58b19f"));
                    btn_id.setTextColor(Color.parseColor("#FFFAFA"));
                 }
                else if(exams_count==3)
                    {
                //紫/藍
                btn_id.setBackgroundColor(Color.parseColor("#2376b7"));
                btn_id.setTextColor(Color.parseColor("#f7f1e3"));
                }
                else if(exams_count>3)
                {
                //紅
                btn_id.setBackgroundColor(Color.parseColor("#ee3f4d"));
                btn_id.setTextColor(Color.parseColor("#f7f1e3"));
             }
            }
            cu.close();
        }
    }

    protected void init_element()
    {
        ib1=findViewById(R.id.imageView7);
        one=findViewById(R.id.one);
        t1_date=findViewById(R.id.t1_date);
        t1_grade=findViewById(R.id.t1_grade);
        t1=findViewById(R.id.t1);

        ib2=findViewById(R.id.imageView8);
        two=findViewById(R.id.two);
        t2_date=findViewById(R.id.t2_date);
        t2_grade=findViewById(R.id.t2_grade);
        t2=findViewById(R.id.t2);

        ib3=findViewById(R.id.imageView9);
        three=findViewById(R.id.three);
        t3_date=findViewById(R.id.t3_date);
        t3_grade=findViewById(R.id.t3_grade);
        t3=findViewById(R.id.t3);

        ib4=findViewById(R.id.imageView10);
        four=findViewById(R.id.four);
        t4_date=findViewById(R.id.t4_date);
        t4_grade=findViewById(R.id.t4_grade);
        t4=findViewById(R.id.t4);

        five=findViewById(R.id.five);
        t5_date=findViewById(R.id.t5_date);
        t5_grade=findViewById(R.id.t5_grade);
        t5=findViewById(R.id.t5);

        six=findViewById(R.id.six);
        t6_date=findViewById(R.id.t6_date);
        t6_grade=findViewById(R.id.t6_grade);
        t6=findViewById(R.id.t6);

        seven=findViewById(R.id.seven);
        t7_date=findViewById(R.id.t7_date);
        t7_grade=findViewById(R.id.t7_grade);
        t7=findViewById(R.id.t7);

        eight=findViewById(R.id.eight);
        t8_date=findViewById(R.id.t8_date);
        t8_grade=findViewById(R.id.t8_grade);
        t8=findViewById(R.id.t8);

        nine=findViewById(R.id.nine);
        t9_date=findViewById(R.id.t9_date);
        t9_grade=findViewById(R.id.t9_grade);
        t9=findViewById(R.id.t9);

        ten=findViewById(R.id.ten);
        t10_date=findViewById(R.id.t10_date);
        t10_grade=findViewById(R.id.t10_grade);
        t10=findViewById(R.id.t10);

        eleven=findViewById(R.id.eleven);
        t11_date=findViewById(R.id.t11_date);
        t11_grade=findViewById(R.id.t11_grade);
        t11=findViewById(R.id.t11);

        twelve=findViewById(R.id.twelve);
        t12_date=findViewById(R.id.t12_date);
        t12_grade=findViewById(R.id.t12_grade);
        t12=findViewById(R.id.t12);

        thirteen=findViewById(R.id.thirteen);
        t13_date=findViewById(R.id.t13_date);
        t13_grade=findViewById(R.id.t13_grade);
        t13=findViewById(R.id.t13);

        fourteen=findViewById(R.id.fourteen);
        t14_date=findViewById(R.id.t14_date);
        t14_grade=findViewById(R.id.t14_grade);
        t14=findViewById(R.id.t14);

    }

    public int[] choi_Q(String str)//隨機產生5題題目
    {
        int [] array;
        int min=0,max=0;
        int [ ]Q=new  int[20];
        int []Q_array=new int[5];
        int count=0,total=0;

        cu = db.rawQuery("SELECT * FROM Question WHERE topic_id='"+str+"' ",null);
        if (cu.getCount()>0)
        {
            //total_Q=cu.getCount();
            cu.moveToFirst();
            min=Integer.valueOf(cu.getString(0));
            cu.moveToLast();
            max=Integer.valueOf(cu.getString(0));
        }
        cu.close();
        array=new int [5];
        while (count<5){
            int num=(int)(Math.random()*(max-min+1))+min;
            boolean flag=true;
            for (int j=0;j<5;j++){
                if (num==array[j]){
                    flag=false;
                    break;
                }
            }
            if (flag)
            {
                array[count]=num;
                Q_array[count]=num;
                count++;
            }
        }
        cu.close();
        return Q_array;
    }

    public String datetime(){
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String date_time = nowdate.format(new java.util.Date());

        return date_time;
    }

    private void insertExam(String exam_id ,String nurse_id,  String patient_id){
        //  exam_id TEXT, exam_date DateTime, exam_score INT,question_id_1 TEXT,question_id_2 TEXT,question_id_3 TEXT,question_id_4 TEXT,question_id_5 TEXT, patient_id TEXT, nurse_id TEXT
        // ==格式化
        String date_time=datetime();
        int change_data=pad+1;
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String exam_date = nowdate.format(new java.util.Date());
        ContentValues cv =new ContentValues(5);
        cv.put("exam_id",exam_id);
        cv.put("exam_date",exam_date);
        cv.put("exam_score",-1);
        cv.put("patient_id",patient_id);
        cv.put("nurse_id",nurse_id);
        cv.put("change_data",date_time);

        db.insert("Exam", null, cv);
    }

    public void insertAnswer(String answer_id, int result,int question_id,String exam_id){
        //answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data INT
        // answer_id exam_id+count
        String date_time=datetime();
        ContentValues cv =new ContentValues(1);//10
        cv.put("answer_id",answer_id);
        cv.put("result",result);
        cv.put("question_id",question_id);
        cv.put("exam_id",exam_id);
        cv.put("change_data",date_time);
        db.insert("Answer", null, cv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public  void  back(View v){
        Intent i=new Intent(choose_education.this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choose_education.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choose_education.this,MainActivity.class);
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

    public void t1(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t1"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        int co=cu.getCount();
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t1"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                    cu.close();
                }

                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t1(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t1(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t1"+id+count;
                Q_array=choi_Q("t1");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t1(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t1");
            insertExam("t1"+id+"0" ,nurseID, id);
            Answer_inser_db("t1"+id+"0",Q_array);
            go_fronttest_t1(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t2(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t2"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        int co=cu.getCount();
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t2"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t2(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t2(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t2"+id+count;
                Q_array=choi_Q("t2");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t2(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t2");
            insertExam("t2"+id+"0" ,nurseID, id);
            Answer_inser_db("t2"+id+"0",Q_array);
            go_fronttest_t2(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t3(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t3"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t3"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t3(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t3(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t3"+id+count;
                Q_array=choi_Q("t3");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t3(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t3");
            insertExam("t3"+id+"0" ,nurseID, id);
            Answer_inser_db("t3"+id+"0",Q_array);
            go_fronttest_t3(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t4(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t4"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t4"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t4(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t4(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t4"+id+count;
                Q_array=choi_Q("t4");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t4(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t4");
            insertExam("t4"+id+"0" ,nurseID, id);
            Answer_inser_db("t4"+id+"0",Q_array);
            go_fronttest_t4(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t5(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t5"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t5"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t5(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t5(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t5"+id+count;
                Q_array=choi_Q("t5");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t5(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t5");
            insertExam("t5"+id+"0" ,nurseID, id);
            Answer_inser_db("t5"+id+"0",Q_array);
            go_fronttest_t5(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t6(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t6"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t6"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t6(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t6(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t6"+id+count;
                Q_array=choi_Q("t6");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t6(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t6");
            insertExam("t6"+id+"0" ,nurseID, id);
            Answer_inser_db("t6"+id+"0",Q_array);
            go_fronttest_t6(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t7(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t7"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t7"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t7(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t7(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t7"+id+count;
                Q_array=choi_Q("t7");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t7(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t7");
            insertExam("t7"+id+"0" ,nurseID, id);
            Answer_inser_db("t7"+id+"0",Q_array);
            go_fronttest_t7(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t8(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t8"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t8"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t8(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t8(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t8"+id+count;
                Q_array=choi_Q("t8");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t8(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t8");
            insertExam("t8"+id+"0" ,nurseID, id);
            Answer_inser_db("t8"+id+"0",Q_array);
            go_fronttest_t8(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t9(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t9"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t9"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t9(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t9(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t9"+id+count;
                Q_array=choi_Q("t9");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t9(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t9");
            insertExam("t9"+id+"0" ,nurseID, id);
            Answer_inser_db("t9"+id+"0",Q_array);
            go_fronttest_t9(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t10(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t10"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t10"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t10(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t10(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t10"+id+count;
                Q_array=choi_Q("t10");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t10(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t10");
            insertExam("t10"+id+"0" ,nurseID, id);
            Answer_inser_db("t10"+id+"0",Q_array);
            go_fronttest_t10(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t11(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t11"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t11"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t11(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t11(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t11"+id+count;
                Q_array=choi_Q("t11");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t11(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t11");
            insertExam("t11"+id+"0" ,nurseID, id);
            Answer_inser_db("t11"+id+"0",Q_array);
            go_fronttest_t11(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t12(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t12"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t12"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t12(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t12(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t12"+id+count;
                Q_array=choi_Q("t12");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t12(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t12");
            insertExam("t12"+id+"0" ,nurseID, id);
            Answer_inser_db("t12"+id+"0",Q_array);
            go_fronttest_t12(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t13(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t13"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t13"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                int k=0;
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t13(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t13(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t13"+id+count;
                Q_array=choi_Q("t13");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t13(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t13");
            insertExam("t13"+id+"0" ,nurseID, id);
            Answer_inser_db("t13"+id+"0",Q_array);
            go_fronttest_t13(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public void t14(View v){
        int score=0;
        int Q_array[]=new int[5];
        int count=0;//看有幾張考卷了
        String exam_id;
        exam_id="t14"+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+exam_id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            int flag=0;
            count=cu.getCount();
            count=count-1;
            exam_id="t14"+id+count;
            flag=judgment_f_or_b(exam_id);
            if (flag==-1)//考卷還沒做完
            {
                int i=0;//判別考卷從哪開始做
                int j=0;//檢查題目
                while (j<5)
                {
                    cu.close();
                    cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id LIKE '"+exam_id+j+"%'AND result!='" + -1 + "'"  ,null);
                    if (cu.getCount()>0)
                    {
                        i++;
                        cu.moveToFirst();
                        int r_w=cu.getInt(1);
                        if (r_w==1)
                            score+=20;
                    }
                    j++;
                }
                //去做上次沒做完的後側
                if(count==0)//前側
                {
                    go_fronttest_t14(Q_array,count,i,score);
                }
                else //後側
                {
                    go_backtest_t14(count,Q_array,i,score);//0：考卷還沒做過
                }
            }
            else if(flag==99 || flag==2)
            {
                count=cu.getCount();
                exam_id="t14"+id+count;
                Q_array=choi_Q("t14");
                insertExam(exam_id ,nurseID, id);
                Answer_inser_db(exam_id,Q_array);
                go_backtest_t14(count,Q_array,0,-1);//0：考卷還沒做過
            }
            else {
                Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //前側
            Q_array=choi_Q("t14");
            insertExam("t14"+id+"0" ,nurseID, id);
            Answer_inser_db("t14"+id+"0",Q_array);
            go_fronttest_t14(Q_array,count,0,-1);//0：考卷還沒做過
        }
        cu.close();
    }

    public int  judgment_f_or_b(String exam_id) {
        int flag=0;//flag=-1 去前一個側驗 flag=2 去做目前得測 flag=-99 找不到資料
        Cursor cur = db.rawQuery("SELECT * FROM Exam WHERE exam_id = '"+ exam_id +"'",null);
        if (cur.getCount()>0){
            cur.moveToFirst();
            //count=cur.getCount();
            int sc=cur.getInt(2);
            if (sc==-1){
                flag=-1;
            }
            else {
                flag=2;
            }
        }
        else{
            flag=99;
            //Toast.makeText(getApplicationContext(), "查無此資料", Toast.LENGTH_SHORT).show();
        }
        cu.close();
        return flag;
    }

    public void Answer_inser_db(String exam_id,int[] Q_array){
        for (int i=0;i<5;i++)
        {
            int q_id=Integer.valueOf(Q_array[i]);
            String answer_id=exam_id+i;
            insertAnswer(answer_id,-1,q_id,exam_id);//true_or_false：-1 為還沒有做題目，先都存-1
        }
    }

    public void go_backtest_t14(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t14"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t14");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t14(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t14"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t14");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t13(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t13"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t13");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t13(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t13"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t13");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t12(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t12"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t12");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t12(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t12"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t12");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t11(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t11"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t11");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t11(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t11"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t11");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t10(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t10"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t10");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t10(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t10"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t10");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t9(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t9"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t9");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t9(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t9"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t9");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t8(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t8"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t8");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t8(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t8"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t8");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t7(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t7"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t7");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t7(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t7"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t7");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t6(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t6"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t6");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t6(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t6"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t6");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t5(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t5"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t5");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t5(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t5"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t5");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t4(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t4"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t4");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t4(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t4"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t4");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t3(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t3"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t3");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t3(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t3"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t3");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t2(int count,int[] Q_array,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        String exam_id="t2"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t2");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t2(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t2"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health_education","t2");
        i.putExtra("count",index);

        db.close();
        startActivity(i);
        finish();
    }

    public void go_backtest_t1(int count,int[] Q_array,int index,int score)
    {
        //衛教+後側
        String exam_id="t1"+id+count;//考卷id=衛教資料名+病友id+第幾筆
        Intent i=new Intent( this,HealthInformation.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("exam_id",exam_id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("health_education","t1");
        db.close();
        startActivity(i);
        finish();
    }

    public void go_fronttest_t1(int[] Q_array,int count,int index,int score)//index是用來判別題目從哪題開始做  還沒修改完
    {
        //前側
        String exam_id="t1"+id+count;
        Intent i=new Intent( this,fronttest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("exam_id",exam_id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("health_education","t1");
        //i.putExtra("Q_array",Q_array);
        db.close();
        startActivity(i);
        finish();
    }

    public void go_vedio(View v)
    {
        Intent i=new Intent( this,choice_vedio.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public void go_pdf(View v)
    {
        Intent i=new Intent( this,choice_pdf.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public void go_test(View v)
    {
        Intent i=new Intent( this,choice_test.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }
}