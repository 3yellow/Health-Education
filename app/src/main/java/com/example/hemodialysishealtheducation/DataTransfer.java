package com.example.hemodialysishealtheducation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataTransfer extends AppCompatActivity {
    SQLiteDatabase db;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);
        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void import_all(View v) throws IOException, ParseException {
        try {
            importNurse("csvname/Nurse_pc.csv");
            importPatient("csvname/Patient_pc.csv");
            importQuestion("csvname/Question_pc.csv");
            importStudy("csvname/Study_pc.csv");
            importTopic("csvname/Topic_pc.csv");
            importAnswer("csvname/Answer_pc.csv");
            importExam("csvname/Exam_pc.csv");

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Cant find file", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void export_function(View v) throws IOException {
        File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "csvname");
        if (!ddfile.exists()) {
            ddfile.mkdir();
            scanMedia(ddfile.toString());
        }
        exportAll(ddfile.toString());
        /*File file = new File(ddfile, "Nurse_tab.csv");
        if (file.exists()) {
            file.delete();
            scanMedia(file.toString());
        }*/

        /*if (file.createNewFile()) {
            exportNurse(file.toString());
        }*/
    }

    public void back_function(View v)
    {
        Intent intent = new Intent();
        intent.setClass(DataTransfer.this , MainActivity.class);
        intent.putExtra("health_education","t20");
        intent.putExtra("flag",99);//顯示PDF的地方 按鈕要改變 跳轉道的葉面要改
        startActivity(intent);
        finish();
    }

    private void exportAll(String path) {
        exportNurse(path);
        exportPatient(path);
        exportAnswer(path);
        exportExam(path);
        exportQuestion(path);
        exportStudy(path);
        exportTopic(path);
    }

    private void exportNurse(String path) {
        StringBuilder data = new StringBuilder();
        try {
            //String file = path + "\\Nurse.csv";
            File file = new File(path, "Nurse_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Nurse", null);
            FileWriter stream = new FileWriter(file);
            data.append("nurse_id,nurse_name,nurse_password,nurse_authority,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    String x = curCSV.getString(2);
                    x = x.replace("\n", "");
                    data.append(x);
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            curCSV.close();
            scanMedia(file.toString());

            Toast.makeText(this, "Nurse_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Nurse_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportPatient(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Patient_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Patient", null);
            FileWriter stream = new FileWriter(file);
            data.append("patient_id,patient_name,patient_gender,patient_register,patient_sign,patient_birth,patient_incharge,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append(",");
                    data.append(curCSV.getString(5));
                    data.append(",");
                    data.append(curCSV.getString(6));
                    data.append(",");
                    data.append(curCSV.getString(7));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Patient_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Patient_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportQuestion(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Question_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Question", null);
            FileWriter stream = new FileWriter(file);
            data.append("question_id,question_content,question_answer,question_explain,topic_id,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append(",");
                    data.append(curCSV.getString(5));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Question_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Question_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportStudy(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Study_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Study", null);
            FileWriter stream = new FileWriter(file);
            data.append("study_id,study_date,topic_id,patient_id,nurse_id,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append(",");
                    data.append(curCSV.getString(5));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Study_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Study_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportTopic(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Topic_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Topic", null);
            FileWriter stream = new FileWriter(file);
            data.append("topic_id,topic_name,change_data,video\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Topic_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Topic_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportAnswer(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Answer_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Answer", null);
            FileWriter stream = new FileWriter(file);
            data.append("answer_id,result,question_id,exam_id,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Answer_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Answer_error", Toast.LENGTH_LONG).show();
        }
    }

    private void exportExam(String path) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path, "Exam_tab.csv");
            Cursor curCSV = db.rawQuery("SELECT * FROM Exam", null);
            FileWriter stream = new FileWriter(file);
            data.append("exam_id,exam_date,exam_score,patient_id,nurse_id,change_data\n");
            if (curCSV.moveToFirst()) {
                do {
                    data.append(curCSV.getString(0));
                    data.append(",");
                    data.append(curCSV.getString(1));
                    data.append(",");
                    data.append(curCSV.getString(2));
                    data.append(",");
                    data.append(curCSV.getString(3));
                    data.append(",");
                    data.append(curCSV.getString(4));
                    data.append(",");
                    data.append(curCSV.getString(5));
                    data.append("\n");
                } while (curCSV.moveToNext());
            }
            stream.write(data.toString());
            stream.close();
            scanMedia(file.toString());
            curCSV.close();
            Toast.makeText(this, "Exam_success", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Exam_error", Toast.LENGTH_LONG).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importNurse(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Nurse WHERE nurse_id = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(4);
                    Date newdate = dateFormat.parse((data[4]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateNurse(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]);
                    }

                } else if (data[0] != null) {
                    insertNurse(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Nurse_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Nurse_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importPatient(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Patient WHERE patient_id = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(7);
                    Date newdate = dateFormat.parse((data[7]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updatePatient(data[0], data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), data[5], data[6], data[7]);
                    }

                } else if (data[0] != null) {
                    insertPatient(data[0], data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), data[5], data[6], data[7]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Patient_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Patient_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importQuestion(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Question WHERE question_id = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(5);
                    Date newdate = dateFormat.parse((data[5]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateQuestion(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                    }

                } else if (data[0] != null) {
                    insertQuestion(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Question_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Question_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importStudy(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Study WHERE study_id = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(5);
                    Date newdate = dateFormat.parse((data[5]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateStudy(data[0], data[1], data[2], data[3], data[4], data[5]);
                    }

                } else if (data[0] != null) {
                    insertStudy(data[0], data[1], data[2], data[3], data[4], data[5]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Study_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Study_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importTopic(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Topic WHERE topic_id = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(3);
                    Date newdate = dateFormat.parse((data[3]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateTopic(data[0], data[1], Integer.parseInt(data[2]), data[3]);
                    }

                } else if (data[0] != null) {
                    insertTopic(data[0], data[1], Integer.parseInt(data[2]), data[3]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Topic_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Topic_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importAnswer(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Answer WHERE answer = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(4);
                    Date newdate = dateFormat.parse((data[4]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateAnswer(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4]);
                    }

                } else if (data[0] != null) {
                    insertAnswer(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Answer_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Answer_import_error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void importExam(String path) throws IOException, ParseException {
        try {
            SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//yyyy-MM-dd hh:mm:ss
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);
            File ddfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            BufferedReader csvReader = new BufferedReader(new FileReader(ddfile));
            String row = csvReader.readLine();
            SQLiteDatabase DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String sql = "SELECT * FROM Exam WHERE exam = '" + data[0] + "'";
                Cursor cu = DBS.rawQuery(sql, null);

                if (cu.moveToFirst()) {
                    String x = cu.getString(5);
                    Date newdate = dateFormat.parse((data[5]));
                    Date dbdate = dateFormat.parse(x);

                    if (newdate.after(dbdate)) {
                        updateExam(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                    }

                } else if (data[0] != null) {
                    insertExam(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                }
            }
            csvReader.close();
            DBS.close();
            Toast.makeText(this, "Exam_import_success", Toast.LENGTH_LONG).show();
            ddfile.delete();
        } catch (Exception sqlEx) {
            Toast.makeText(this, "Exam_import_error", Toast.LENGTH_LONG).show();
        }
    }


    private void updateNurse(String nurse_id, String nurse_name, String nurse_password, int nurse_authority, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("nurse_id",nurse_id);
        cv.put("nurse_name", nurse_name);
        cv.put("nurse_password", nurse_password);
        cv.put("nurse_authority", nurse_authority);
        cv.put("change_data", change_data);
        String whereClause = "nurse_id = ?";
        String whereArgs[] = {nurse_id};
        db.update("Nurse", cv, whereClause, whereArgs);
    }

    private void updatePatient(String patient_id, String patient_name, int patient_gender, String patient_register, int patient_sign, String patient_birth, String patient_incharge, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("patient_id",patient_id);
        cv.put("patient_name", patient_name);
        cv.put("patient_gender", patient_gender);
        cv.put("patient_register", patient_register);
        cv.put("patient_sign", patient_sign);
        cv.put("patient_birth", patient_birth);
        cv.put("patient_incharge", patient_incharge);
        cv.put("change_data", change_data);
        String whereClause = "patient_id = ?";
        String whereArgs[] = {patient_id};
        db.update("Patient", cv, whereClause, whereArgs);
    }

    private void updateQuestion(int question_id, String question_content, int question_answer, String question_explain, String topic_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("question_id",question_id);
        cv.put("question_content", question_content);
        cv.put("question_answer", question_answer);
        cv.put("question_explain", question_explain);
        cv.put("topic_id", topic_id);
        cv.put("change_data", change_data);
        String whereClause = "question_id = ?";
        String whereArgs[] = {String.valueOf(question_id)};
        db.update("Question", cv, whereClause, whereArgs);
    }

    private void updateStudy(String study_id, String study_date, String topic_id, String patient_id, String nurse_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("study_id",study_id);
        cv.put("study_date", study_date);
        cv.put("topic_id", topic_id);
        cv.put("patient_id", patient_id);
        cv.put("nurse_id", nurse_id);
        cv.put("change_data", change_data);
        String whereClause = "study_id = ?";
        String whereArgs[] = {String.valueOf(study_id)};
        db.update("Study", cv, whereClause, whereArgs);
    }

    private void updateTopic(String topic_id, String topic_name, int vidio, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("topic_id",topic_id);
        cv.put("topic_name", topic_name);
        cv.put("vidio", vidio);
        cv.put("change_data", change_data);
        String whereClause = "topic_id = ?";
        String whereArgs[] = {String.valueOf(topic_id)};
        db.update("Topic", cv, whereClause, whereArgs);
    }

    private void updateAnswer(String answer_id, int result, int question_id, int exam_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("answer_id",answer_id);
        cv.put("result", result);
        cv.put("question_id", question_id);
        cv.put("exam_id", exam_id);
        cv.put("change_data", change_data);
        String whereClause = "answer_id = ?";
        String whereArgs[] = {answer_id};
        db.update("Answer", cv, whereClause, whereArgs);
    }

    private void updateExam(String exam_id, String exam_date, int exam_score, String patient_id, String nurse_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        //cv.put("exam_id",exam_id);
        cv.put("exam_date", exam_date);
        cv.put("exam_score", exam_score);
        cv.put("patient_id", patient_id);
        cv.put("nurse_id", nurse_id);
        cv.put("change_data", change_data);
        String whereClause = "exam_id = ?";
        String whereArgs[] = {String.valueOf(exam_id)};
        db.update("Exam", cv, whereClause, whereArgs);
    }


    private void insertNurse(String nurse_id, String nurse_name, String nurse_password, int nurse_authority, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("nurse_id", nurse_id);
        cv.put("nurse_name", nurse_name);
        cv.put("nurse_password", nurse_password);
        cv.put("nurse_authority", nurse_authority);
        cv.put("change_data", change_data);
        db.insert("Nurse", null, cv);
    }

    private void insertPatient(String patient_id, String patient_name, int patient_gender, String patient_register, int patient_sign, String patient_birth, String patient_incharge, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("patient_id", patient_id);
        cv.put("patient_name", patient_name);
        cv.put("patient_gender", patient_gender);
        cv.put("patient_register", patient_register);
        cv.put("patient_sign", patient_sign);
        cv.put("patient_birth", patient_birth);
        cv.put("patient_incharge", patient_incharge);
        cv.put("change_data", change_data);
        db.insert("Patient", null, cv);
    }

    private void insertQuestion(int question_id, String question_content, int question_answer, String question_explain, String topic_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("question_id", question_id);
        cv.put("question_content", question_content);
        cv.put("question_answer", question_answer);
        cv.put("question_explain", question_explain);
        cv.put("topic_id", topic_id);
        cv.put("change_data", change_data);
        db.insert("Question", null, cv);
    }

    private void insertStudy(String study_id, String study_date, String topic_id, String patient_id, String nurse_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("study_id", study_id);
        cv.put("study_date", study_date);
        cv.put("topic_id", topic_id);
        cv.put("patient_id", patient_id);
        cv.put("nurse_id", nurse_id);
        cv.put("change_data", change_data);
        db.insert("Study", null, cv);
    }

    private void insertTopic(String topic_id, String topic_name, int vidio, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("topic_id", topic_id);
        cv.put("topic_name", topic_name);
        cv.put("vidio", vidio);
        cv.put("change_data", change_data);
        db.insert("Topic", null, cv);
    }

    private void insertAnswer(String answer_id, int result, int question_id, int exam_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("answer_id", answer_id);
        cv.put("result", result);
        cv.put("question_id", question_id);
        cv.put("exam_id", exam_id);
        cv.put("change_data", change_data);
        db.insert("Answer", null, cv);
    }

    private void insertExam(String exam_id, String exam_date, int exam_score, String patient_id, String nurse_id, String change_data) {
        ContentValues cv = new ContentValues(1);//10
        cv.put("exam_id", exam_id);
        cv.put("exam_date", exam_date);
        cv.put("exam_score", exam_score);
        cv.put("patient_id", patient_id);
        cv.put("nurse_id", nurse_id);
        cv.put("change_data", change_data);
        db.insert("Exam", null, cv);
    }


    public String getCurrentTime() {
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String currentTime = nowdate.format(new java.util.Date());

        return currentTime;
    }

    private void scanMedia(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(scanFileIntent);
    }
}
