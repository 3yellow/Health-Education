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
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;

//後台管理_護理人員管理

public class Menu extends AppCompatActivity {

    static final String Nurse="nurse"; //database table name

    ArrayList id_array= new ArrayList();
    EditText edt_search;
    Button btn_search;
    String namee=null;
    String idd=null;
    String agee=null;
    Intent intent = new Intent();
    TableLayout layout2;
    TableRow row;
    SQLiteDatabase db;
    int flag=0,i = 0;
    private TableRow r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //btn_search = findViewById(R.id.btn_birth);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        TextView user=(TextView)findViewById(R.id.textView);
        //   String name=getIntent().getStringExtra("name").toString();


        // db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);
        layout2=findViewById(R.id.tbl);
        // row=findViewById(R.id.tbr);
        read();
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
    
    public void on_dialog(String str)//點選變更資料 會出現一個dialog，要輸入該位護理師密碼。
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Menu.this);
        View v1 = getLayoutInflater().inflate(R.layout.dialog_signin,null);
        alertDialog.setView(v1);
        Button btn=v1.findViewById(R.id.btn_right);//確定
        Button btn_cancle=v1.findViewById(R.id.btn_left);//取消
        final TextView username=v1.findViewById(R.id.username);
        final EditText password=v1.findViewById(R.id.password);
        final String str2=str;
        char []pas_id=new char[str.length()];
        for (int i=0;i<str.length();i++)//
        {
            if(i>3 && i<8)
                pas_id[i]='*';
            else
                pas_id[i]=str.charAt(i);
        }
        str=String.valueOf(pas_id);
        username.setText(str);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=str2;//username.getText().toString().trim();
                String prepas=password.getText().toString().trim();
                prepas=prepas.toUpperCase();
                String pas=sha256(prepas).replace("\n","");
                Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id = '"+ s +"'",null);
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else{
                    String password1=cu.getString(2);
                    if (password1.equals(pas)||"ADMIN".equals(prepas))//輸入正確帳號密碼
                    {
                        flag=1;
                        Intent intent=new Intent(Menu.this,Nurse_modify.class);
                        intent.putExtra("id",s);
                        intent.putExtra("flag",flag);
                        dialog.cancel();
                        db.close();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "輸入錯誤!!", Toast.LENGTH_SHORT).show();
                    }
                }
                cu.close();
                /*
                Intent i=new Intent(Menu.this,Nurse_Newdata.class);
                i.putExtra("aa",s);
                TextView v11=findViewById(R.id.textView);
                v11.setText(s);
                startActivity(i);
                */
            }
        });
    }

    public void read()//自動產生按鈕，從資料庫中撈取護理師資料
    {
        i=0;//計數有幾筆資料
        Cursor cu = db.rawQuery("SELECT * FROM "+Nurse,null);
        if(cu.getCount()>0) {
            int co=1;//co-=1;
            cu.moveToFirst();
            namee = cu.getString(1);
            do {

                    String staue = null;
                    int a = cu.getInt(3);
                    if (a == 1) {
                        staue = "在職中";
                    } else {
                        staue = "離職";

                    }
                    //Nurse (nurse_id char(10) NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL,change_data DATETIME,
                    id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                    namee = cu.getString(1);
                    idd = cu.getString(0);
                    agee = cu.getString(2);
                    char []pas_id=new char[idd.length()];
                    for (int i=0;i<idd.length();i++)
                    {
                        if(i>3 && i<8)
                          pas_id[i]='*';
                        else
                           pas_id[i]=idd.charAt(i);
                   }
                   idd=String.valueOf(pas_id);
                    final TextView na = new TextView(this);//final Button
                    final TextView id = new TextView(this);
                    final TextView statu = new TextView(this);
                    final Button btn_modify = new Button(this);//final Button
                    btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    TableRow r = new TableRow(this);//final TableRow
                    //  final ScrollView sc=new ScrollView(this);
                    // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                    btn_modify.setLayoutParams(new TableRow.LayoutParams(60, 100));
                    na.setLayoutParams(new TableRow.LayoutParams());//842
                    // id.setLayoutParams(new TableRow.LayoutParams());
                    // statu.setLayoutParams(new TableRow.LayoutParams());
                    btn_modify.setLayoutParams(new TableRow.LayoutParams());
                    // id.setId(i);
                    //  statu.setId(i);
                    btn_modify.setId(i);
                    na.setId(i);
                    r.setId(i);
                    i++;
                    na.setTextSize(30);
                    na.setText(namee);
                    na.setGravity(Gravity.CENTER);
                    id.setTextSize(30);
                    id.setText(idd);
                    id.setGravity(Gravity.CENTER);
                    statu.setTextSize(30);
                    statu.setText(staue);
                    statu.setGravity(Gravity.CENTER);

                    // la.addView(layout2);
                    btn_modify.setTextSize(30);
                    btn_modify.setText("修改");
                    if(!namee.equals("Admin"))
                    {
                        r.addView(na);//yout
                        r.addView(id);
                        r.addView(statu);
                        r.addView(btn_modify);//yout2
                        layout2.addView(r);
                    }


                    btn_modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btn_modify.getId() != 0) {
                                int tmp = btn_modify.getId();
                                String id_tmp = id_array.get(tmp).toString();
                                on_dialog(id_tmp);
                            } else {
                                AlertDialog dialog = new AlertDialog.Builder(Menu.this)
                                        .setTitle("管理者資料不可以變動!!")
                                        .setNegativeButton("確定", null).create();
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
                                    mTitleView.setTextColor(Color.BLACK);
                                    //通过反射修改message字体大小和颜色
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
                                } catch (NoSuchFieldException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    });
                    co++;

            } while (cu.moveToNext() );
        }
        cu.close();
    }

    public void nwedata(View v)//跳轉到新增護理師
    {
        edt_search = findViewById(R.id.edt_search);
        String na=edt_search.getText().toString().trim();
        Intent i=new Intent(Menu.this,Nurse_Newdata.class);
        if(na!=null)
        {
            intent.putExtra("na",edt_search.getText().toString().trim());
        }
        db.close();
        startActivity(i);
        finish();
    }

    public void search(View v)//收尋護理師
    {
        Cursor cu=null;
        edt_search = findViewById(R.id.edt_search);
        String s_p = edt_search.getText().toString().trim();
        if (s_p.length()>0)//判斷是否有輸入東西  但還沒改好
        {
            for(int x = 0;x<=i;x++)
            {
                ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
                View command = layout.findViewById(x);
                layout.removeView(command);
            }
            if (s_p.length()==10)//收尋病人使用 身分證
            {
                i=0;
                String sql = "SELECT * FROM Nurse WHERE nurse_id = '"+s_p+"'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {

                    cu.moveToFirst();
                    do {
                        i++;
                        String staue=null;
                        if (cu.getInt(3)==1){
                            staue="在職中";
                        }
                        else {
                            staue="離職";

                        }
                        String text=cu.getString(1)+"\t\t"+cu.getString((0))+"\t\t\t"+staue;
                        id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                        namee=cu.getString(1);
                        idd=cu.getString(0);
                        agee=cu.getString(2);
                        char []pas_id=new char[idd.length()];
                        for (int i=0;i<idd.length();i++)
                        {
                            if(i>3 && i<8)
                                pas_id[i]='*';
                            else
                                pas_id[i]=idd.charAt(i);
                        }
                        idd=String.valueOf(pas_id);
                        final TextView na = new TextView(this);//final Button
                        final TextView id = new TextView(this);
                        final TextView statu = new TextView(this);
                        final Button btn_modify = new Button(this);//final Button
                        btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                        TableRow r = new TableRow(this);//final TableRow
                        //  final ScrollView sc=new ScrollView(this);
                        // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                        btn_modify.setLayoutParams(new TableRow.LayoutParams());
                        na.setLayoutParams(new TableRow.LayoutParams());//842
                        id.setLayoutParams(new TableRow.LayoutParams());
                        statu.setLayoutParams(new TableRow.LayoutParams());
                        btn_modify.setLayoutParams(new TableRow.LayoutParams());
                        id.setId(i);
                        statu.setId(i);
                        btn_modify.setId(i);
                        na.setId(i);
                        r.setId(i);
                        i++;
                        na.setTextSize(30);
                        na.setText(namee);
                        na.setGravity(Gravity.CENTER);
                        id.setTextSize(30);
                        id.setText(idd);
                        id.setGravity(Gravity.CENTER);
                        statu.setTextSize(30);
                        statu.setText(staue);
                        statu.setGravity(Gravity.CENTER);
                        btn_modify.setTextSize(30);
                        btn_modify.setText("修改");
                        if(!namee.equals("Admin"))
                        {
                            r.addView(na);//yout
                            r.addView(id);
                            r.addView(statu);
                            r.addView(btn_modify);//yout2
                            layout2.addView(r);
                        }
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp=btn_modify.getId();
                                String id_tmp=id_array.get(tmp).toString();
                                on_dialog(id_tmp);
                            }
                        });
                    } while (cu.moveToNext());
                }
                else
                {
                    AlertDialog dialog = new AlertDialog.Builder(Menu.this)
                            .setTitle("沒有此資料!!!\n請先把要搜尋的內容刪除，再按兩下搜尋按鈕會顯示所有資料")
                            .setNegativeButton("確定", null).create();
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
                        mTitleView.setTextColor(Color.BLACK);
                        //通过反射修改message字体大小和颜色
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e2) {
                        e2.printStackTrace();
                    }

                    cu.close();
                }

            }
            else //收尋病人名
            {
                i=0;
                //String sql = "SELECT * FROM Patient  WHERE patient_name = '"+s_p+"'";  收尋名字時 只能找到輸入全名
                String sql = "SELECT * FROM Nurse  WHERE nurse_name LIKE '%"+s_p+"%'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    do {
                        String staue=null;
                        if (cu.getInt(3)==1){
                            staue="在職中";
                        }
                        else {
                            staue="離職";

                        }
                        id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                        namee=cu.getString(1);
                        idd=cu.getString(0);
                        agee=cu.getString(2);
                        char []pas_id=new char[idd.length()];
                        for (int i=0;i<idd.length();i++)
                        {
                            if(i>3 && i<8)
                                pas_id[i]='*';
                            else
                                pas_id[i]=idd.charAt(i);
                        }
                        idd=String.valueOf(pas_id);
                        final TextView na = new TextView(this);//final Button
                        final TextView id = new TextView(this);
                        final TextView statu = new TextView(this);
                        final Button btn_modify = new Button(this);//final Button
                        btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                        TableRow r = new TableRow(this);//final TableRow
                        //  final ScrollView sc=new ScrollView(this);
                        // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                        btn_modify.setLayoutParams(new TableRow.LayoutParams(60,100));
                        na.setLayoutParams(new TableRow.LayoutParams());//842
                        id.setLayoutParams(new TableRow.LayoutParams());
                        statu.setLayoutParams(new TableRow.LayoutParams());
                        btn_modify.setLayoutParams(new TableRow.LayoutParams());
                        id.setId(i);
                        statu.setId(i);
                        btn_modify.setId(i);
                        na.setId(i);
                        r.setId(i);
                        i++;
                        na.setTextSize(30);
                        na.setText(namee);
                        na.setGravity(Gravity.CENTER);
                        id.setTextSize(30);
                        id.setText(idd);
                        id.setGravity(Gravity.CENTER);
                        statu.setTextSize(30);
                        statu.setText(staue);
                        statu.setGravity(Gravity.CENTER);
                        btn_modify.setTextSize(30);
                        btn_modify.setText("修改");
                        if(!namee.equals("Admin"))
                        {
                            r.addView(na);//yout
                            r.addView(id);
                            r.addView(statu);
                            r.addView(btn_modify);//yout2
                            layout2.addView(r);
                        }
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp=btn_modify.getId();
                                String id_tmp=id_array.get(tmp).toString();
                                on_dialog(id_tmp);
                            }
                        });
                    } while (cu.moveToNext());
                }
                else
                {
                    AlertDialog dialog = new AlertDialog.Builder(Menu.this)
                            .setTitle("沒有此資料!!!\n請先把要收搜尋的內容刪除，再按兩下搜尋按鈕會顯示所有資料")
                            .setNegativeButton("確定", null).create();
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
                        mTitleView.setTextColor(Color.BLACK);
                        //通过反射修改message字体大小和颜色
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e2) {
                        e2.printStackTrace();
                    }

                    cu.close();
                }
            }
        }
        else {
            for(int x = 0;x<=i;x++)
            {
                ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
                View command = layout.findViewById(x);
                layout.removeView(command);
            }
            read();
        }
    }

    public void sign(View v)//病友補簽名
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Menu.this);
        View v1 = getLayoutInflater().inflate(R.layout.dialog_signin,null);
        alertDialog.setView(v1);
        Button btn=v1.findViewById(R.id.btn_right);
        Button btn_cancle=v1.findViewById(R.id.btn_left);
        final EditText username=v1.findViewById(R.id.username);
        final EditText password=v1.findViewById(R.id.password);
        username.setHint("請輸入姓名：");
        password.setHint("請輸入身分證：");
        //username.setText(str);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=username.getText().toString().trim();
                String prepas=password.getText().toString().trim();
                prepas=prepas.toUpperCase();
    //Patient (patient_id char(10) NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign INT, patient_birth DATE , patient_incharge
                Cursor cu = db.rawQuery("SELECT * FROM Patient WHERE patient_name = '"+ s +"'",null);
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else{
                    String password1=cu.getString(0);
                    if (password1.equals(prepas))//輸入身分證和姓名一樣的人
                    {
                        flag=1;
                        Intent intent=new Intent(Menu.this,signature.class);
                        intent.putExtra("id",prepas);
                        intent.putExtra("flag",99);
                        dialog.cancel();
                        db.close();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "輸入錯誤!!", Toast.LENGTH_SHORT).show();
                    }
                }
                cu.close();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) //不能返回
    {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void back(View v) //返回前一頁
    {
        Intent i=new Intent(Menu.this,Backstage.class);
        db.close();
        startActivity(i);
        finish();
    }

    public void onclick(View v)//管理者要登出
    {
        AlertDialog dialog=new AlertDialog.Builder(Menu.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Menu.this,MainActivity.class);
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
}
