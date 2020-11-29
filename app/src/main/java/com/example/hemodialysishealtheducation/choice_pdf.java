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
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

public class choice_pdf extends AppCompatActivity {

    int pad=0;
    String id,nurseID,health_education;
    Cursor cu;
    SQLiteDatabase db;
    Button one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eightteen,nineteen;
    TextView patient,nurse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pdf);
        init_element();
        get_intent();
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
        id=i.getStringExtra("id");
        cu.close();
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
        }
        cu.close();
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t1");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
         two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t2");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t3");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t4");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t5");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t6");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t7");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t8");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t9");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t10");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t11");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t12");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        thirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t13");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        fourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t14");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t15");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        sixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t16");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        seventeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t17");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        eightteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t18");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
        nineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choice_pdf.this,HealthInformation.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("health_education","t19");
                i.putExtra("flag",1);
                db.close();
                startActivity(i);
                finish();
            }
        });
    }

    protected void get_intent()
    {
        Intent intent = this.getIntent();
        nurseID = intent.getStringExtra("nurseID");
        id = intent.getStringExtra("id");
        health_education=intent.getStringExtra("health_education");
    }

    protected void init_element()
    {
        patient = findViewById(R.id.tex_patient_name);
        nurse=findViewById(R.id.tex_nurse_name);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        ten=findViewById(R.id.ten);
        eleven=findViewById(R.id.eleven);
        twelve=findViewById(R.id.twelve);
        thirteen=findViewById(R.id.thirteen);
        fourteen=findViewById(R.id.fourteen);
        fifteen=findViewById(R.id.fifteen);
        sixteen=findViewById(R.id.sixteen);
        seventeen=findViewById(R.id.seventeen);
        eightteen=findViewById(R.id.eightteen);
        nineteen=findViewById(R.id.nineteen);
    }

    public  void  back(View v){
        Intent i=new Intent(this,Searchlogin.class);
        startActivity(i);
        finish();
    }

    public  void  totheme(View v){
        Intent i=new Intent(choice_pdf.this,choose_education.class);
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

    public void go_test(View v)
    {
        Intent i=new Intent( this,choice_test.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choice_pdf.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choice_pdf.this,MainActivity.class);
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
