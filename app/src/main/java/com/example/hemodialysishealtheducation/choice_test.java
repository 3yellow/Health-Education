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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class choice_test extends AppCompatActivity {

    String nurseID=null,id=null;
    int pad=0;
    Cursor cu;
    SQLiteDatabase db;
    Button one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_test);

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
        Intent i=new Intent(choice_test.this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choice_test.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choice_test.this,MainActivity.class);
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
        exam_id="11"+id;
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
        Intent i=new Intent( this,backtest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("count",index);
        i.putExtra("score",score);
        i.putExtra("exam_id",exam_id);
        i.putExtra("health education","t14");
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this, backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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
        Intent i=new Intent( this,backtest.class);
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

    public  void  totheme(View v){
        Intent i=new Intent(choice_test.this,choose_education.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
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

}
