package com.example.hemodialysishealtheducation;


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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class backtest2 extends AppCompatActivity {

    Intent intent = new Intent();
    boolean result;//判斷答案對錯
    SQLiteDatabase db; //database object
    int choiceid,pad=0;
    RadioGroup mRG;
    RadioButton item1,item2;
    String str=null,explain=null;
    String your_ans=null;
    Cursor cu;
    String nurseID,id,exam_id,health_education,patient_answer;
    int score=0,count=0;
    int int_your_ans = -1;
    //int Q_array[]=new int[5];
    int right_choi=0,q_id=0;
    //int index=0;
    RadioButton tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtest2);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫
        TextView Que = (TextView) findViewById(R.id.Question);
        final TextView Als = (TextView) findViewById(R.id.Analysis);
        final TextView An = (TextView) findViewById(R.id.An);
        final RadioGroup ans = (RadioGroup) findViewById(R.id.Ans);
        mRG = (RadioGroup) findViewById(R.id.Mrg);
        item1 = (RadioButton) findViewById(R.id.icho1);
        item2 = (RadioButton) findViewById(R.id.icho2);
        final Button check = (Button) findViewById(R.id.button19);

        intent = this.getIntent();
        nurseID = intent.getStringExtra("nurseID");
        id = intent.getStringExtra("id");
        exam_id = intent.getStringExtra("exam_id");
        //index = intent.getIntExtra("index", -1);
        health_education = intent.getStringExtra("health education");
        score = intent.getIntExtra("score", 0);
        count = intent.getIntExtra("count", 0);//right_choi  explain  int_result
        str=intent.getStringExtra("right_choi");
        explain=intent.getStringExtra("explain");
        int int_result = intent.getIntExtra("int_result", 0);


        String answer_id = exam_id + count;
        String sql = "SELECT * FROM Answer WHERE answer_id='" + answer_id+ "'"; //我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
        cu = db.rawQuery(sql, null);
        if (cu.getCount() > 0) {
            cu.moveToFirst();
            q_id = cu.getInt(2);
        }
        cu.close();
        //q_id = Q_array[c];


        final String  sql_1 = "SELECT * FROM Question WHERE question_id = '" + q_id + "'"; //我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
        cu = db.rawQuery(sql_1, null);
        if (!cu.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else {
            String content = cu.getString(1);
            Que.setText(content);
            //{"A.發熱", "B.肌肉痙攣", "C.失衡綜合征", "D.透析性骨病", "D.透析性骨病"};
            // item1.setText("正確");
            //  item2.setText("錯誤");
            cu.close();
        }

        if(int_result==1)
        {
            //green
            An.setText("恭喜您答對!!!!");
            An.setTextColor(Color.GREEN);
        }
        else
        {
            //red
            An.setText("很遺憾您答錯了! 這題應選擇：" + str);
            An.setTextColor(Color.RED);
        }
        Als.setText(explain);
        Als.setVisibility(View.VISIBLE);
        An.setVisibility(View.VISIBLE);
    }

    //不能返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public  void  back(View v){
        new android.app.AlertDialog.Builder(backtest2.this)
                .setTitle("確定要離開測驗嗎?")
                //  .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("確定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(backtest2.this,Searchlogin.class);
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

    public void tofronttest2 (View v){
        if (count >= 4) {
            // answer_id exam_id+count
            String answer_id=exam_id+count;
            int q=Integer.valueOf(q_id);
            //  modify_Answer(answer_id,true_or_false, q, exam_id);
            //modify_Exam(score, id, exam_id);
            Intent intent = this.getIntent();
            health_education = intent.getStringExtra("health education");
            Intent i = new Intent(backtest2.this, choose_education.class);
            i.putExtra("health_education", health_education);
            i.putExtra("nurseID", nurseID);
            i.putExtra("id", id);
            i.putExtra("score", score);
            i.putExtra("flag", 99);//到MaunActivity時要判別 修改考卷
            db.close();
            startActivity(i);
            finish();
        }
        else {
            String answer_id=exam_id+count;
            count++;
            Intent i = new Intent(this, backtest.class);
            i.putExtra("count", count);
            i.putExtra("score", score);
            i.putExtra("health_education", health_education);
            i.putExtra("nurseID", nurseID);
            i.putExtra("id", id);
            i.putExtra("exam_id", exam_id);
            //i.putExtra("Q_array", Q_array);
            db.close();
            startActivity(i);
            finish();
        }
    }
}
