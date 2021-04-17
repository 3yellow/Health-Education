package com.example.dialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Newdata extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    //static final String db_patient="patientDB"; //database name;
    //static final String Patient="patient"; //database table name
    SQLiteDatabase DBS; //database object
    String createTable;
    Cursor c;
    Calendar cal;
    Calendar ca2;

    EditText edt_id,edt_name=null;
    TextView textView7;
    RadioGroup sex;
    private Button button6;
    private Button btn_birth;

    String date,birth;
    int geender=0;//1：男 2：女
    String eName,eId=null;
    String na=null;
    //以下是要在修改時使用的
    String idd=null;
    int flag1=0;//判斷是要修改的還是新增。 1為修改

    String nurseID,name;
    int flag=0;//判別是不是已經有資料;
    int mYear_b,mMonth_b,mDay_b;
    RadioButton malee,femalee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdata);

        DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"

        Intent i=this.getIntent();
        nurseID=i.getStringExtra("nurseID");
        na=i.getStringExtra("na");

        cal =  Calendar.getInstance();
        ca2 =  Calendar.getInstance();
        textView7=findViewById(R.id.textView7);
        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        //  edt_age=findViewById(R.id.btn_birth);
        sex=findViewById(R.id.radioGroup);
        sex.setOnCheckedChangeListener(this);
        malee = findViewById(R.id.male);
        femalee = findViewById(R.id.female);
        if(na!=null)
        {
            edt_name.setText(na);
        }
        //修改資料
        //   Intent i=this.getIntent();
        flag1=i.getIntExtra("flag",0);
        if (flag1 == 1) {
            idd=i.getStringExtra("id");
            String sql = "SELECT  patient_gender  FROM Patient WHERE patient_id = '"+ idd +"'";
            Cursor cu = DBS.rawQuery( sql,null );
            if (!cu.moveToFirst()){
                Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
            }
            else {
                edt_id.setFocusable(false);
                edt_id.setFocusableInTouchMode(false);
                geender = cu.getInt(0);//性別的預設值
                if (geender==1){
                    malee.setChecked(true);
                }
                else {
                    femalee.setChecked(true);
                }
            }
            cu.close();
            read(idd);
        }
        else {
            String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL, PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
            DBS.execSQL(sql);
            DBS.execSQL("PRAGMA foreign_keys=ON;");
        }
        nowTime(1,idd,flag1);//收案日期的
        nowTime(0,idd,flag1);//生日
    }

    public void read(String id_tmp){
        String sql = "SELECT *FROM Patient WHERE patient_id = '"+ id_tmp +"'";
        Cursor cu = DBS.rawQuery( sql,null );

        if (!cu.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else{
            String anamee = cu.getString(1);
            String date_register=cu.getString(2);
            String bi=cu.getString(3);
            edt_name.setText(anamee);
            String idd2=idd;
            char []pas_id=new char[idd2.length()];
            for (int i=0;i<idd2.length();i++)
            {
                if(i>3 && i<8)
                    pas_id[i]='*';
                else
                    pas_id[i]=idd2.charAt(i);
            }
            idd2=String.valueOf(pas_id);

            edt_id.setText(idd2);
        }
        cu.close();
    }
    public void onDay2(View v)//設定時間的元件 View v int flag,String date
    {
        mYear_b = 0;mMonth_b = 0; mDay_b = 0;
        if (flag1 == 1){
            String[] token=birth.split("-");
            mYear_b=Integer.valueOf(token[0]);
            mMonth_b=Integer.valueOf(token[1])-1;
            mDay_b=Integer.valueOf(token[2]);
        }
        else {
            mYear_b=1970;//ca2.get(Calendar.YEAR)
            mMonth_b=ca2.get(Calendar.MONTH);
            mDay_b=ca2.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(Newdata.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String format = setDateFormat(year, month, day);
                btn_birth.setText(format);
            }
        },mYear_b,mMonth_b,mDay_b).show();
    }
    public void onDay(View v){
        mYear_b = 0;mMonth_b = 0; mDay_b = 0;
        if (flag1 == 1){
            String[] token=date.split("-");
            mYear_b=Integer.valueOf(token[0]);
            mMonth_b=Integer.valueOf(token[1])-1;
            mDay_b=Integer.valueOf(token[2]);
        }
        else {
            mYear_b=ca2.get(Calendar.YEAR);
            mMonth_b=ca2.get(Calendar.MONTH);
            mDay_b=ca2.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(Newdata.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String format = setDateFormat(year, month, day);
                button6.setText(format);
            }
        },mYear_b,mMonth_b,mDay_b).show();
    }

    private String setDateFormat(int year, int month, int day) {
        return String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
    }

    public void onClick(View v) {
        Boolean len,birth_bool,accet_bool;
        eId=edt_id.getText().toString().trim();//trim去除多餘空白
        eId=eId.toUpperCase();
        String ename=edt_name.getText().toString().trim();
        String Accepted_date=button6.getText().toString();
        String birth=btn_birth.getText().toString();
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String now_date = nowdate.format(new java.util.Date());
        Intent i=this.getIntent();
        String nurseID=i.getStringExtra("nurseID");
        String birth_1=birth.replace('-','/');
        String Accepted_date_1=Accepted_date.replace('-','/');
        String now_date_1=now_date.replace('-','/');
        birth_bool=compare_date(birth_1,now_date_1);//birth,now_date
        accet_bool=compare_date(Accepted_date_1,now_date_1);//Accepted_date,now_date
        len=isValidIDorRCNumber(eId);
        flag=searchData(eId);
        if (flag==2&&flag1!=1){
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("已有此資料");
        }
        else if (ename.isEmpty()){
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("姓名還沒填");
        }
        else if (eId.isEmpty()){
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("身分證還沒填");
        }
        else if(!birth_bool)
        {
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("生日日期選錯!!");
        }
        else if(!accet_bool)
        {
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("收案日期日期選錯!!");
        }
        else if(!len&&flag1!=1)
        {
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("身分證格式不對");
        }
        else if (geender==0){
            textView7.setVisibility(View.VISIBLE);
            flag=3;
            textView7.setText("性別還沒選");
        }
        else if(eId==null){
            textView7.setVisibility(View.VISIBLE);
            flag=3;
            textView7.setText("身分證還沒選");
        }
        else if (flag1==1){
            modify_patient(ename,eId,geender,Accepted_date,birth);
            DBS.close();
            i=new Intent(Newdata.this,Searchlogin.class);
            i.putExtra("nurseID",nurseID);
            i.putExtra("patientname",ename);
            DBS.close();
            startActivity(i);
            finish();
        }
        else if (flag == 1) {
            addData(ename,eId,geender,Accepted_date,birth,nurseID);
            //(String name,String id,String age,int gender,String date,String birth_date)
            DBS.close();

            i=new Intent(Newdata.this,consent.class);
            i.putExtra("nurseID",nurseID);
            i.putExtra("id",eId);
            i.putExtra("patientname",ename);
            i.putExtra("health_education","t21");
            DBS.close();
            startActivity(i);
            finish();
        }
    }

    public boolean compare_date(String goal_date,String now_date)
    {
        Date date2 = new Date(now_date);
        Date date1 = new Date(goal_date);
        if(date1.getTime()>date2.getTime())
        {
            return false;
        }
        return true;
    }

    public Boolean  vreifyId(String id){
        int c=0,n=0; //c判斷第一個字是否為英文字 n判別第二個字是否為1或2
        if (id.length()!=10){
            return false;
        }
        for (int i=65;i<=90;i++)
        {
            char ch=(char)i;
            if (id.charAt(0)==i){
                c=1;//第一個字為英文字
            }
        }
        if (c==0){
            return false;//第一個要為英文字母
        }
        char cha1=49,cha2=50;//在ascill碼49為1 50為2
        if (id.charAt(1)==49||id.charAt(2)==50){
            n=1;//第一個字為1或2
        }
        if (n==0){
            return false;
        }

        //判斷格式是否符合身分證
        String str="ABCDEFGHJKLMNPQRSTUVXYWZIO";
        int e=str.indexOf(id.charAt(0))+10;
        int f=e/10,g=e%10,total=0;
        g*=9;
        int aa=0;
        for (int j=1;j<=8;j++)
            total+=(id.charAt(j)-48)*(9-j);           //-48原因在於id.charAt(抓的是數字的char)
        total+=+f+g;
        total%=10;
        int bb=((id.charAt(9)-48)+total)%10;
        if (bb==0)
        {
            textView7.setVisibility(View.VISIBLE);
            System.out.println("這是正確的身分證號碼!!");
            aa=1;
            return true;
        }
        if (aa==0) {              //aa不等於0則輸入身分證字號不符合
            textView7.setVisibility(View.VISIBLE);
            System.out.println("這不是正確的身分證字號!!");
            return false;
        }
        return true;
    }

    public boolean isValidIDorRCNumber(String str) {

        if (str == null || "".equals(str)) {
            return false;
        }

        final char[] pidCharArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        // 原身分證英文字應轉換為10~33，這裡直接作個位數*9+10
        final int[] pidIDInt = { 1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11, 20, 48, 29, 38, 47, 56, 65, 74, 83, 21, 3, 12, 30 };

        // 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod 10]
        final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3, 12, 10 };

        // 原居留證第二碼英文字應轉換為10~33，並僅取個位數*8，這裡直接取[(個位數*8) mod 10]
        final int[] pidResidentSecondInt = {0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8, 4};

        str = str.toUpperCase();// 轉換大寫
        final char[] strArr = str.toCharArray();// 字串轉成char陣列
        int verifyNum = 0;

        /* 檢查身分證字號 */
        if (str.matches("[A-Z]{1}[1-2]{1}[0-9]{8}")) {
            // 第一碼
            verifyNum = verifyNum + pidIDInt[Arrays.binarySearch(pidCharArray, strArr[0])];
            // 第二~九碼
            for (int i = 1, j = 8; i < 9; i++, j--) {
                verifyNum += Character.digit(strArr[i], 10) * j;
            }
            // 檢查碼
            verifyNum = (10 - (verifyNum % 10)) % 10;

            return verifyNum == Character.digit(strArr[9], 10);
        }

        /* 檢查統一證(居留證)編號 */
        verifyNum = 0;
        if (str.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
            // 第一碼
            verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
            // 第二碼
            verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, strArr[1])];
            // 第三~八碼
            for (int i = 2, j = 7; i < 9; i++, j--) {
                verifyNum += Character.digit(strArr[i], 10) * j;
            }
            // 檢查碼
            verifyNum = (10 - (verifyNum % 10)) % 10;
            int a=Character.digit(strArr[9], 10);
            return verifyNum == Character.digit(strArr[9], 10);
        }

        return false;
    }

    private int searchData(String str1) //判別是否已經有此資料了
    {
        c=DBS.rawQuery("SELECT * FROM Patient  WHERE patient_id='"+str1+"'",null);
        if (c.moveToFirst()) {
            flag = 2;
        }
        else {
            flag =1;
        }
        c.close();
        return flag;
    }

    public String datetime(){
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String date_time = nowdate.format(new java.util.Date());

        return date_time;
    }
    private void addData(String name,String id ,int gender,String date,String birth_date,String nurseID) {
        //Patient (patient_id char(10) NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign INT, patient_birth DATE , patient_incharge char(10) NOT NULL,change_data DATETIME,
        String date_time= datetime();
        int pad=0,change_data=0;
        Intent i=this.getIntent();
        pad=i.getIntExtra("pad",-1);
        change_data=pad+1;
        ContentValues cv=new ContentValues(1);
        cv.put("patient_id",id);
        cv.put("patient_name",name);
        cv.put("patient_gender",gender);
        cv.put("patient_register",date);
        cv.put("patient_sign",0);
        cv.put("patient_birth",birth_date);
        cv.put("patient_sign",0);
        cv.put("change_data",date_time);
        cv.put("patient_incharge",nurseID);//目前沒有護理師的資料，護理師的資料是從登入那抓取id，一直傳

        DBS.insert("Patient", null, cv);
    }

    private void modify_patient(String name,String id ,int gender,String date,String birth_date ){
        String date_time= datetime();
        int pad=0;
        //,change_data=0;
        Intent i=this.getIntent();
        //pad=i.getIntExtra("pad",pad);
        //change_data=pad+2;
        ContentValues cv = new ContentValues(7);
        cv.put("patient_id", idd);
        cv.put("patient_name", name);
        cv.put("patient_gender", gender);
        cv.put("patient_register", date);
        cv.put("patient_birth", birth_date);
        cv.put("change_data",date_time);

        //如果是修改
        String whereClause = "patient_id = ?";
        String whereArgs[] = {idd};
       // DBS.replace ("Patient", null,cv);
       DBS.update("Patient", cv, whereClause, whereArgs);
    }

    public void nowTime(int flag_data,String id_tmp,int flag)//取得當日日期並且顯示在按鈕上
    {
        if (flag_data==1){
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            if (flag==1){
                String sql = "SELECT patient_register FROM Patient WHERE patient_id = '"+ id_tmp +"'";
                Cursor cu = DBS.rawQuery( sql,null );
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else {
                    date = cu.getString(0);//formatter.format(new java.util.Date());
                }
                cu.close();
            }
            else {
                date=formatter.format(new java.util.Date());
            }
            button6=findViewById(R.id.button6);
            button6.setText(date);

        }
        else {
            SimpleDateFormat formatter_b=new SimpleDateFormat("1970-MM-dd");
            if (flag==1){
                String sql = "SELECT patient_birth FROM Patient WHERE patient_id = '"+ id_tmp +"'";
                Cursor cu = DBS.rawQuery( sql,null );
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else {
                    birth = cu.getString(0);
                }
                cu.close();
            }
            else {
                birth=formatter_b.format(new java.util.Date());
            }
            btn_birth=findViewById(R.id.btn_birth);
            btn_birth.setText(birth);
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (sex.getCheckedRadioButtonId()){
            case R.id.male://
                geender=1;//"男"
                break;
            case R.id.female:
                geender=2;//"女"
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void back(View v){
        Intent i=new Intent(Newdata.this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        DBS.close();
        startActivity(i);
        finish();
    }
}
