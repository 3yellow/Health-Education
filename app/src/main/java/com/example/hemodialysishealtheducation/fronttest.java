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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class fronttest extends AppCompatActivity {

    static final String Question="question"; //database table name
    SQLiteDatabase db; //database object
    boolean result;//判斷答案對錯
    //static final String db_nurse="nurseDB"; //database name;
    Cursor cu;
    String nurseID,id,exam_id,health_education;
    int Q_array[]=new int[5];
    RadioButton item1;
    RadioButton item2;
    String your_ans=null;
    //int index=0;
    int q_id=0;//要把所有用到資料庫的 都要改成int
    int score=0,count=0;


    public  void  back(View v){
        new android.app.AlertDialog.Builder(fronttest.this)
                .setTitle("確定要離開測驗嗎?")
                //  .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("確定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(fronttest.this,Searchlogin.class);
                                i.putExtra("nurseID",nurseID);
                                //i.putExtra("id",id);
                                startActivity(i);
                                finish();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                            }
                        }).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronttest);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫
        TextView Que = (TextView) findViewById(R.id.Question);
       // final TextView YAns = (TextView) findViewById(R.id.YourAns);
        final RadioGroup ans = (RadioGroup) findViewById(R.id.Ans);
        final Button next = (Button) findViewById(R.id.button12);
        item1 = (RadioButton) findViewById(R.id.radioButton1);
        item2 = (RadioButton) findViewById(R.id.radioButton2);
        Button dialog = (Button) findViewById(R.id.button);

        Intent intent = this.getIntent();
        nurseID = intent.getStringExtra("nurseID");
        id = intent.getStringExtra("id");
        exam_id = intent.getStringExtra("exam_id");
        health_education = intent.getStringExtra("health_education");
        score = intent.getIntExtra("score", 0);
        count = intent.getIntExtra("count", 0);
        if(score<0)
            score=0;
        if (count == -1) {
            Que.setText("產生考卷發生問題");
        } else if (count != -1) {
                String answer_id = exam_id + count;
                String sql = "SELECT * FROM Answer  WHERE answer_id='" + answer_id + "'";//我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
                cu = db.rawQuery(sql, null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    if (cu.getInt(1) == -1)//如果作答解果為-1，表示還沒做過題目
                    {
                        q_id= cu.getInt(2);
                    }
                }
                cu.close();
            //q_id = Q_array[c];
            //question_id='"+q_id+"'";
            final String sql_1 = "SELECT * FROM Question WHERE topic_id='" + health_education + "' AND question_id='" + q_id + "'";//我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
            cu = db.rawQuery(sql_1, null);
            if (!cu.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
            } else {
                String content = cu.getString(1);
                Que.setText(content);
                //{"A.發熱", "B.肌肉痙攣", "C.失衡綜合征", "D.透析性骨病", "D.透析性骨病"};

               // item1.setText("正確");
              //  item2.setText("錯誤");

                cu.close();
                ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub
                        cu = db.rawQuery(sql_1, null);
                        int a=-1;
                        if (cu.moveToFirst())
                        {
                            a = cu.getInt(2);//拿到資料庫中的答案
                        }
                        cu.close();
                        RadioButton tempButton = (RadioButton) findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                        your_ans = tempButton.getText().toString();
                        int int_your_ans = -1;
                        if (your_ans.equals("正確")) {
                            int_your_ans = 1;
                        } else {
                            int_your_ans = 0;
                        }
                        // 以下就可以对这个RadioButton进行处理了

                        //patient_answer=tempButton.getText().toString();
                       // YAns.setText("您的答案：" + tempButton.getText());
                        //YAns.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        if (int_your_ans == a) {
                           result = true;
                        } else {
                            result = false;
                        }
                    }

                });
            }

        }
    }

    public void tofronttest2 (View v){
        if (your_ans!=null) {
            int true_or_false = -1;//判別題目有沒有做對 1:對 0:錯
            if (result == true) {
                true_or_false = 1;
                score += 20;
                //Toast.makeText(this, "right" + score, Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "error" + score, Toast.LENGTH_LONG).show();
                true_or_false = 0;
            }
            if (count >= 4) {
                // answer_id exam_id+count
                String answer_id=exam_id+count;
                modify_Answer(answer_id,true_or_false, q_id, exam_id);
                modify_Exam(score, id, exam_id);
                //Intent intent = this.getIntent();
                //health_education = intent.getStringExtra("health education");
                Intent i = new Intent(fronttest.this, choose_education.class);
                i.putExtra("nurseID", nurseID);
                i.putExtra("id", id);
                i.putExtra("flag", 99);//到MaunActivity時要判別 修改考卷

                db.close();
                startActivity(i);
                finish();
            } else {
                // answer_id exam_id+count
                String answer_id=exam_id+count;
                modify_Answer(answer_id, true_or_false, q_id, exam_id);
                Intent i = new Intent(this, fronttest.class);
                count++;
                i.putExtra("count", count);
                i.putExtra("score", score);
                i.putExtra("nurseID", nurseID);
                i.putExtra("health_education", health_education);
                i.putExtra("id", id);
                i.putExtra("exam_id", exam_id);
                db.close();
                startActivity(i);
                finish();
            }
        }
        else
        {
            AlertDialog dialog=new AlertDialog.Builder(fronttest.this)
                    .setTitle("請選擇您的答案!!")
                    .setNegativeButton("確定",null).create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(56);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(56);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            try {
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);
                //通过反射修改title字体大小和颜色
                Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                mTitle.setAccessible(true);
                TextView mTitleView = (TextView) mTitle.get(mAlertController);
                mTitleView.setTextSize(56);
                mTitleView.setTextColor(Color.BLACK);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
    }

    public String datetime(){
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String date_time = nowdate.format(new java.util.Date());

        return date_time;
    }

    public void modify_Answer(String answer_id, int result,int question_id,String exam_id)
    {
        ContentValues cv =new ContentValues(1);//10
        String change_data=datetime();
        cv.put("answer_id",answer_id);
        cv.put("result",result);
        cv.put("question_id",question_id);
        cv.put("exam_id",exam_id);
        cv.put("change_data",change_data);
        String whereClause = "answer_id = ?";
        String whereArgs[] = {answer_id};
       //db.replace ("Answer", cv, whereClause, whereArgs);
        db.replace ("Answer", null,cv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    private void modify_Exam(int score,String patient_id,String exam_id){
        //exam_id TEXT, exam_date DateTime, exam_score INT, patient_id TEXT, nurse_id TEXT
        String change_data=datetime();
        String exam_date,nurseID;
        Cursor c = db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"'",null);
       if(c.getCount()>0) {
            c.moveToFirst();
            exam_date=c.getString(1);
            nurseID=c.getString(4);
            ContentValues cv = new ContentValues(7);
            cv.put("exam_score",score);
            cv.put("exam_id",exam_id);
            cv.put("exam_date",exam_date);
            cv.put("patient_id",patient_id);
            cv.put("nurse_id",nurseID);
            cv.put("change_data",change_data);
            //如果是修改
            String whereClause = "exam_id = ?";
            //String whereArgs[] = {id};
            String whereArgs[ ]={String.valueOf(exam_id)};
            db.replace ("Exam", null,cv);
        //    db.update("Exam", cv, whereClause, whereArgs);
        }
        c.close();

    }
}
