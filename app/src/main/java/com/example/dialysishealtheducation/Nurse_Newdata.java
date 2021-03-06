package com.example.dialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

//新增護理師

public class Nurse_Newdata extends AppCompatActivity {

    static final String db_nurse="nurseDB"; //database name;
    static final String Nurse="nurse"; //database table name
    SQLiteDatabase db;
    String createTable;
    boolean canSee;
    EditText edt_id,edt_name,edt_pas1,edt_pas2;
    TextView textView7;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse__newdata);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        edt_name=findViewById(R.id.edt_name);
        edt_id=findViewById(R.id.edt_id);
        edt_pas1=findViewById(R.id.edt_pas1);
        edt_pas2=findViewById(R.id.edt_pas2);
        textView7=findViewById(R.id.textView7);
        ImageButton button = (ImageButton) findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (canSee == false)
                {
                    //如果是不能看到密码的情况下，
                    edt_pas1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_pas2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    edt_pas1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_pas2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee = false;
                }
            }
        });
    }

    public void back(View v)//返回前一個頁面
    {
        Intent i=new Intent(this,Menu.class);
        db.close();
        startActivity(i);
        finish();
    }
    public void onclick(View v)//點選確認後檢查欄位是否正確
    {
        Boolean len;
        String pas1,eId;
        String name=edt_name.getText().toString();
        pas1=edt_pas1.getText().toString().trim();
        flag=pas1.compareTo(edt_pas2.getText().toString());

        eId=edt_id.getText().toString().trim();
        eId=eId.toUpperCase();
        len=isValidIDorRCNumber(eId);// isValidIDorRCNumber
        int flag_2=0;
        flag_2=searchData(eId, flag_2);
        if (flag!=0) {
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("兩次密碼輸入必須相同");
        }
        else if (flag_2==2 ){
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("已有此資料");
        }
        else if(!len)
        {
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("身分證格式不對");
        }
        else if (pas1==null){
            //判別是不是空
            textView7.setVisibility(View.VISIBLE);
            textView7.setText("密碼必須輸入");
        }
        else if(flag==0&&flag_2!=2 ){
            //pas1=pas1.toLowerCase();//讓密碼統一都是小寫
            addData(name,eId,pas1,1);
            db.close();
            Intent i=new Intent(this,Menu.class);
            db.close();
            startActivity(i);
            finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) //不能返回
    {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void show_pas(View v){
        //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
        if (canSee == false) {
            //如果是不能看到密码的情况下，
            edt_pas1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt_pas2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            canSee = true;
        }
        else {
            //如果是能看到密码的状态下
            edt_pas1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edt_pas2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            canSee = false;
        }
    }

    private int searchData(String str1,int flag) //判別是否已經有此資料了
    {
        Cursor c;
        c=db.rawQuery("SELECT nurse_id FROM Nurse  WHERE nurse_id='"+str1+"'",null);
        if (c.moveToFirst()) {
            flag = 2;
        }
        else {
            flag =1;
        }
        c.close();
        return flag;
    }

    public static String sha256(String base) //加密
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            String pass = Base64.encodeToString(hash, Base64.DEFAULT);
            return pass;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String datetime()//日期時間格式
    {
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String date_time = nowdate.format(new java.util.Date());

        return date_time;
    }

    private void addData(String name,String id,String pas,int staue) //新增護理師
    {
        String date_time= datetime();
        pas=pas.toUpperCase();
        pas=sha256(pas).replace("\n","");
        ContentValues cv=new ContentValues(5);
        cv.put("nurse_name",name);
        cv.put("nurse_id",id);
        cv.put("nurse_password",pas);
        cv.put("nurse_authority",staue);//1:表示有正常 0:保釋停權
        cv.put("change_data",date_time);
        db.insert(Nurse,null,cv);
    }

/*
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
*/

    public boolean isValidIDorRCNumber(String str) //判別身分證 居留證
    {

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

            return verifyNum == Character.digit(strArr[9], 10);
        }

        return false;
    }
}
