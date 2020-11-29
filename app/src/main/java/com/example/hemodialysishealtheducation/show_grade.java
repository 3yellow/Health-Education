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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class show_grade extends AppCompatActivity {

    String nurseID=null,id=null,ed_name_chinese=null,ed_name_ec=null,examid=null;
    Cursor cu;
    SQLiteDatabase db;
    ImageView txv_right_wrong_1,txv_right_wrong_2,txv_right_wrong_3,txv_right_wrong_4,txv_right_wrong_5;
    TextView txv_Q1,txv_Q2,txv_Q3,txv_Q4,txv_Q5;
    TextView nurse,patient,title,patient_id;
    Button btn_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grade);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        get_intent();
        init_element();
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

        title.setText(ed_name_chinese);
        title.setTextSize(45);
        cu.close();
        patient.setTextSize(30);
        patient_id.setText(id);
        patient_id.setTextSize(30);


        input_picture();
        input_Q();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public  void  back(View v){
        Intent i=new Intent( this,Grade.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("ed_name_ec",ed_name_ec);
        i.putExtra("ed_name_chinese",ed_name_chinese);
        db.close();
        startActivity(i);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(show_grade.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(show_grade.this,MainActivity.class);
                        db.close();
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

    protected void input_Q()
    {
        //Answer (answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data DATETIME,
        //Exam (exam_id TEXT, exam_date DateTime, exam_score INT, patient_id char(10), nurse_id char(10),change_data DATETIME
        //question_id INT, question_content TEXT, question_answer INT, question_explain TEXT, topic_id char(10), change_data DATETIME
        int count=0;
        int Q_index=-1;
        String content=null;
        while (count<=4)
        {
            String answer_id_str=examid+count;
            cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id='"+answer_id_str+"' ",null);
            if(cu.getCount()>0)
            {
                cu.moveToFirst();
                Q_index=cu.getInt(2);
            }
            cu.close();
            Cursor c=db.rawQuery("SELECT * FROM Question WHERE question_id='"+Q_index+"' ",null);
            if(c.getCount()>0)
            {
                c.moveToFirst();
                content=c.getString(1);
            }
            c.close();
            switch (count)
            {
                case 0:
                    txv_Q1.setText(content);
                    break;
                case 1:
                    txv_Q2.setText(""+content);
                    break;
                case 2:
                    txv_Q3.setText(content);
                    break;
                case 3:
                    txv_Q4.setText(content);
                    break;
                case 4:
                    txv_Q5.setText(content);
                    break;
            }
            count++;
        }

    }

    protected void input_picture()
    {
        ////Answer (answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data DATETIME,
        //Exam (exam_id TEXT, exam_date DateTime, exam_score INT, patient_id char(10), nurse_id char(10),change_data DATETIME
        int count=0;
        while (count<=4)
        {
            String answer_id_str=examid+count;
            cu = db.rawQuery("SELECT * FROM Answer WHERE answer_id='"+answer_id_str+"' ",null);
            if(cu.getCount()>0)
            {
                cu.moveToFirst();
                int result=cu.getInt(1);
                if(result==1)
                {
                    if(count==0)
                        txv_right_wrong_1.setImageResource(R.drawable.right);
                    else if(count==1)
                        txv_right_wrong_2.setImageResource(R.drawable.right);
                    else if(count==2)
                        txv_right_wrong_3.setImageResource(R.drawable.right);
                    else if(count==3)
                        txv_right_wrong_4.setImageResource(R.drawable.right);
                    else if(count==4)
                        txv_right_wrong_5.setImageResource(R.drawable.right);
                }
                else
                {
                    if(count==0)
                        txv_right_wrong_1.setImageResource(R.drawable.wrong);
                    else if(count==1)
                        txv_right_wrong_2.setImageResource(R.drawable.wrong);
                    else if(count==2)
                        txv_right_wrong_3.setImageResource(R.drawable.wrong);
                    else if(count==3)
                        txv_right_wrong_4.setImageResource(R.drawable.wrong);
                    else if(count==4)
                        txv_right_wrong_5.setImageResource(R.drawable.wrong);
                }
                //cu.moveToNext();
            }
            count++;
        }

    }

    protected void get_intent()
    {
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id");
        ed_name_chinese=intent.getStringExtra("ed_name_chinese");
        ed_name_ec=intent.getStringExtra("ed_name_ec");
        examid=intent.getStringExtra("examid");
    }

    protected void  init_element()
    {
        nurse=findViewById(R.id.nurse_id);
        patient=findViewById(R.id.patient_id);
        title=findViewById(R.id.textView14);
        patient_id=findViewById(R.id.tev_id);
        txv_Q1=findViewById(R.id.txv_Q1);
        txv_Q2=findViewById(R.id.txv_Q2);
        txv_Q3=findViewById(R.id.txv_Q3);
        txv_Q4=findViewById(R.id.txv_Q4);
        txv_Q5=findViewById(R.id.txv_Q5);
        txv_right_wrong_1=findViewById(R.id.txv_right_wrong_1);
        txv_right_wrong_2=findViewById(R.id.txv_right_wrong_2);
        txv_right_wrong_3=findViewById(R.id.txv_right_wrong_3);
        txv_right_wrong_4=findViewById(R.id.txv_right_wrong_4);
        txv_right_wrong_5=findViewById(R.id.txv_right_wrong_5);
    }
}
