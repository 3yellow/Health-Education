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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//病友個人衛教畫面

public class Searchlogin extends AppCompatActivity {

    static final String Nurse="nurse"; //database table name
    static final String Patient="patient"; //database table name

    ArrayList id_array= new ArrayList();
    ArrayList id_array_search= new ArrayList();
    EditText edt_search;
    Button btn_search;
    String namee=null;
    String idd=null;
    String agee=null;
    int pad=0;
    TableLayout layout2;
    TableRow row;
    SQLiteDatabase db;
    int flag=0,i = 0;
    // private Button button,btn_modify;
    private TableRow r;
    String nurseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlogin);

        // btn_search = findViewById(R.id.btn_birth);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        TextView user=(TextView)findViewById(R.id.textView);
        Intent i=this.getIntent();
        nurseID=i.getStringExtra("nurseID");
        pad=i.getIntExtra("pad",-1);
        Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);//從資料庫中找取哪一位護理師登入
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            user.setText(nurse_name+"登入");
            user.setTextSize(28);
        }
        cu.close();

        // db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);
        layout2=findViewById(R.id.tbl);
        // row=findViewById(R.id.tbr);
        read();


/*
        // 定义一个List集合
        final List<String> components = new ArrayList<>();
        components.add("TextView");
        components.add("EditText");
        components.add("Button");
        components.add("CheckBox");
        components.add("RadioButton");
        components.add("ImageView");
        components.add("ToggleButton");


        // 将List包装成ArrayAdapter
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_item, R.id.content_tv, components);*/

        // 为ListView设置Adapter
        //listView.setAdapter(adapter);

        // 为ListView列表项绑定点击事件监听器
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Searchlogin.this, components.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) //不能使用返回鍵
    {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void search(View v)//收尋病友
    {
        Cursor cu=null;
        edt_search = findViewById(R.id.edt_search);
        String s_p = edt_search.getText().toString().trim();
        //if (s_p.length()>0)//判斷是否有輸入東西  但還沒改好
        //{
        for(int x = 0;x<=i;x++)
        {
            ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
            View command = layout.findViewById(x);
            layout.removeView(command);
        }
        if (s_p.length()==10)//收尋病人使用 身分證
        {
            i=0;
            String sql = "SELECT * FROM Patient WHERE patient_id = '"+s_p+"'";
            cu=db.rawQuery(sql,null);
            if (cu.getCount() > 0) {
                cu.moveToFirst();
                do {
                    i++;
                    String text1=cu.getString(1);
                    String text2=cu.getString(0);
                    String text3=cu.getString(3);
                    id_array_search.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                    String namee = cu.getString(0);
                    String idd = cu.getString(1);
                    String agee = cu.getString(2);
                   // final Button button = new Button(this);//final Button
                    final Button button = new Button(this);//final Button
                    button.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    final TextView id = new TextView(this);
                    final TextView statu = new TextView(this);
                    final Button btn_modify=new Button(this);//final Button
                    btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    TableRow r = new TableRow(this);//final TableRow
                    //  final ScrollView sc=new ScrollView(this);
                    // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                    r.setLayoutParams(new TableRow.LayoutParams());
                    button.setLayoutParams(new TableRow.LayoutParams());//842
                    id.setLayoutParams(new TableRow.LayoutParams());
                    statu.setLayoutParams(new TableRow.LayoutParams());
                    btn_modify.setLayoutParams(new TableRow.LayoutParams());
                    btn_modify.setId(i);
                    id.setId(i);
                    statu.setId(i);
                    button.setId(i);
                    r.setId(i);
                    button.setTextSize(30);
                    button.setText(text1);
                    id.setTextSize(30);
                    id.setGravity(Gravity.CENTER);
                    id.setText(text2);
                    statu.setTextSize(30);
                    statu.setGravity(Gravity.CENTER);
                    statu.setText(text3);
                    btn_modify.setTextSize(30);
                    btn_modify.setText("修改");
                    // la.addView(layout2);

                    r.addView(button);//yout
                    r.addView(id);
                    r.addView(statu);
                    r.addView(btn_modify);//yout2
                    layout2.addView(r);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int tmp = button.getId();
                            String id_tmp = id_array_search.get(tmp).toString();
                            Intent i = new Intent(Searchlogin.this, choose_education.class);
                            i.putExtra("flag",0);//要前測
                            i.putExtra("nurseID",nurseID);
                            i.putExtra("pad",pad);
                            i.putExtra("id",id_tmp);
                            db.close();
                            startActivity(i);
                            finish();
                        }
                    });
                    btn_modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tmp = btn_modify.getId();
                            String id_tmp = id_array_search.get(tmp).toString();
                            int flag = 1;
                            Intent intent = new Intent(Searchlogin.this, Newdata.class);
                            intent.putExtra("id",id_tmp);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("flag",flag);
                            intent.putExtra("pad",pad);
                            // intent.putExtra("id", id_tmp);
                            // intent.putExtra("flag", flag);
                            db.close();
                            startActivity(intent);
                            finish();
                        }
                    });
                } while (cu.moveToNext());
            }
            else
            {
                AlertDialog dialog=new AlertDialog.Builder(Searchlogin.this)
                        .setTitle("沒有此資料!!!\n請先把要搜尋的內容刪除，再按兩下搜尋按鈕會顯示所有資料")
                        .setNegativeButton("確定",null).create();
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
            cu.close();
        }
        else if(s_p.length() > 0) //收尋病人名
        {

            i=0;
            //String sql = "SELECT * FROM Patient  WHERE patient_name = '"+s_p+"'";  收尋名字時 只能找到輸入全名
            String sql = "SELECT * FROM Patient  WHERE patient_name LIKE '%"+s_p+"%'";
            cu=db.rawQuery(sql,null);
            if (cu.getCount() > 0) {
                cu.moveToFirst();
                do {
                    String text1=cu.getString(1);
                    String text2=cu.getString(0);
                    String text3=cu.getString(3);
                    id_array_search.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;

                    char []pas_id=new char[text2.length()];
                    for (int i=0;i<text2.length();i++)
                    {
                        if(i>3 && i<8)
                            pas_id[i]='*';
                        else
                            pas_id[i]=text2.charAt(i);
                    }
                    text2=String.valueOf(pas_id);

                    final Button button = new Button(this);//final Button
                    button.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    final TextView id = new TextView(this);
                    final TextView statu = new TextView(this);
                    final Button btn_modify=new Button(this);//final Button
                    btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    r=new TableRow(this);//final TableRow
                    //  final ScrollView sc=new ScrollView(this);
                    // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                    r.setLayoutParams(new TableRow.LayoutParams());
                    button.setLayoutParams(new TableRow.LayoutParams());

                    btn_modify.setLayoutParams(new TableRow.LayoutParams());

                    button.setTextSize(30);
                    button.setText(text1);
                    id.setTextSize(30);
                    id.setGravity(Gravity.CENTER);
                    id.setText(text2);
                    statu.setTextSize(30);
                    statu.setGravity(Gravity.CENTER);
                    statu.setText(text3);
                    btn_modify.setTextSize(30);
                    btn_modify.setText("修改");
                    // la.addView(layout2);
                    btn_modify.setId(i);
                    id.setId(i);
                    statu.setId(i);
                    button.setId(i);
                    r.setId(i);
                    i++;
                    r.addView(button);//yout
                    r.addView(id);
                    r.addView(statu);
                    r.addView(btn_modify);//yout2
                    layout2.addView(r);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int tmp = button.getId();
                            String id_tmp = id_array_search.get(tmp).toString();
                            Intent i = new Intent(Searchlogin.this, choose_education.class);
                            i.putExtra("flag",0);//要前側
                            i.putExtra("nurseID",nurseID);
                            i.putExtra("id",id_tmp);
                            db.close();
                            startActivity(i);
                        }
                    });
                    btn_modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tmp = btn_modify.getId();
                            String id_tmp = id_array_search.get(tmp).toString();
                            int flag = 1;
                            Intent intent = new Intent(Searchlogin.this, Newdata.class);
                            intent.putExtra("id",id_tmp);
                            intent.putExtra("nurseID",nurseID);
                            intent.putExtra("flag",flag);
                            // intent.putExtra("id", id_tmp);
                            //intent.putExtra("flag", flag);
                            db.close();
                            startActivity(intent);
                        }
                    });
                } while (cu.moveToNext());
            }
            else
            {
                AlertDialog dialog=new AlertDialog.Builder(Searchlogin.this)
                        .setTitle("沒有此資料!!!\n請先把要搜尋的內容刪除，再按兩下搜尋按鈕會顯示所有資料")
                        .setNegativeButton("確定",null).create();
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
            cu.close();
        }
        else
            read();
        //}

    }

    public void read()//自動產生按鈕 從病友資料庫中抓取每一筆資料並顯示
    {
        i=0;//計數有幾筆資料
        Cursor cu = db.rawQuery("SELECT * FROM "+Patient,null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            do {
                String text1=cu.getString(1);
                String text2=cu.getString(0);
                String text3=cu.getString(3);
                id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;

                char []pas_id=new char[text2.length()];
                for (int i=0;i<text2.length();i++)
                {
                    if(i>3 && i<8)
                        pas_id[i]='*';
                    else
                        pas_id[i]=text2.charAt(i);
                }
                text2=String.valueOf(pas_id);

                final Button button = new Button(this);//final Button
                button.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                final TextView id = new TextView(this);
                final TextView statu = new TextView(this);
                final Button btn_modify=new Button(this);//final Button
                btn_modify.getBackground().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
                r=new TableRow(this);//final TableRow
                r.setLayoutParams(new TableRow.LayoutParams());
                button.setLayoutParams(new TableRow.LayoutParams());//842
                id.setLayoutParams(new TableRow.LayoutParams());
                statu.setLayoutParams(new TableRow.LayoutParams());
                btn_modify.setId(i);
                id.setId(i);
                statu.setId(i);
                button.setId(i);
                r.setId(i);
                i++;
                button.setTextSize(30);
                button.setText(text1);
                id.setTextSize(30);
                id.setGravity(Gravity.CENTER);
                id.setText(text2);
                statu.setTextSize(30);
                statu.setGravity(Gravity.CENTER);
                statu.setText(text3);
                // la.addView(layout2);
                btn_modify.setTextSize(30);
                btn_modify.setText("修改");
                r.addView(button);//yout
                r.addView(id);
                r.addView(statu);
                r.addView(btn_modify);//yout2
                layout2.addView(r);
                button.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        int tmp=button.getId();
                        String id_tmp=id_array.get(tmp).toString();
                        Intent i=new Intent(Searchlogin.this,choose_education.class);
                        i.putExtra("flag",0);//要前側
                        i.putExtra("nurseID",nurseID);
                        i.putExtra("pad",pad);
                        i.putExtra("id",id_tmp);
                        db.close();
                        startActivity(i);
                        finish();
                    }
                });
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tmp=btn_modify.getId();
                        String id_tmp=id_array.get(tmp).toString();
                        flag=1;
                        Intent intent=new Intent(Searchlogin.this,Newdata.class);
                        intent.putExtra("id",id_tmp);
                        intent.putExtra("nurseID",nurseID);
                        intent.putExtra("pad",pad);
                        intent.putExtra("flag",flag);
                        db.close();
                        startActivity(intent);
                        finish();
                    }
                });
            }while(cu.moveToNext());
        }
        cu.close();
    }

    public void insertpaient(View v)//跳轉到新增病友畫面
    {
        edt_search = findViewById(R.id.edt_search);
        String na=edt_search.getText().toString().trim();
        Intent intent = new Intent();
        intent.setClass(this,Newdata.class);
        intent.putExtra("nurseID",nurseID);
        intent.putExtra("pad",pad);
        if(na!=null)
        {
            intent.putExtra("na",edt_search.getText().toString().trim());
        }
        db.close();
        startActivity(intent);
        finish();
    }

    public void onclick(View v)//護理師登出
    {
        AlertDialog dialog=new AlertDialog.Builder(Searchlogin.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Searchlogin.this,MainActivity.class);
                        startActivity(i);
                        db.close();
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
