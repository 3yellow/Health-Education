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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    static final String Nurse="nurse"; //database table name
    EditText editText, Account;
    Intent intent=new Intent();
    ImageButton button;
    boolean canSee;
    SQLiteDatabase db;
    int flag=0;
    Cursor cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);
        createTopicTable();
        createNurseTable();
        createPatientTable();
        createStudyTable();
        createQuestionTable();
        createExamTable();
        createAnswerTable();

        Account = (EditText) findViewById(R.id.editText);
        editText = (EditText) findViewById(R.id.editText2);
        button = (ImageButton) findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (canSee == false) {
                    //如果是不能看到密码的情况下，
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee = false;
                }
            }
        });
    }

    public String datetime(){
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String date_time = nowdate.format(new java.util.Date());

        return date_time;
    }

    private void createNurseTable() {
        String ad="admin".toUpperCase();
        String pas=sha256(ad);
        String date_time=datetime();
        String sql = "CREATE TABLE IF NOT EXISTS Nurse (nurse_id char(10) NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL,change_data DATETIME,  PRIMARY KEY(nurse_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Nurse", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("nurse_id", "admin");
            contentValues.put("nurse_name", "Admin");
            contentValues.put("nurse_password", pas);
            contentValues.put("nurse_authority", 1);
            contentValues.put("change_data", date_time);
            db.insert("Nurse", null, contentValues);
        }
        cursor.close();
    }



    private void createPatientTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id char(10) NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign INT, patient_birth DATE , patient_incharge char(10) NOT NULL,change_data DATETIME,  PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createTopicTable() {
        //vidio  1有影片 0沒有影片
        String sql = "CREATE TABLE IF NOT EXISTS Topic (topic_id char(10), topic_name TEXT, vidio int, change_data DATETIME, PRIMARY KEY(topic_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Topic", null);
        if (!cursor.moveToFirst()) {
            insertTopic("t1","壹.腎臟功能簡介.pdf",0, datetime());//這個之後要改成t1
            insertTopic("t2","貳.甚麼是慢性腎臟病.pdf",0, datetime());
            insertTopic("t3","參.腎衰竭的原因.pdf",0, datetime());
            insertTopic("t4","肆.腎衰竭的治療方式.pdf",0, datetime());
            insertTopic("t5","伍.如何保護自己的腎.pdf",0, datetime());
            insertTopic("t6","陸.血液透析.pdf",1, datetime());
            insertTopic("t7","柒.血管通路.pdf",0, datetime());
            insertTopic("t8","捌.動靜脈廔管的照護.pdf",1, datetime());
            insertTopic("t9","玖.皮膚搔癢該怎麼辦.pdf",0, datetime());
            insertTopic("t10","拾.透析患者睡眠問題.pdf",0, datetime());
            insertTopic("t11","拾壹.腎友如何預防便秘.pdf",0, datetime());
            insertTopic("t12","拾貳.乾體重.pdf",1, datetime());
            insertTopic("t13","拾叁.磷是啥.pdf",1, datetime());
            insertTopic("t14","拾肆.鉀啥咪.pdf",1, datetime());//
            insertTopic("t15","拾伍.日常生活中的注意事項.pdf",0, datetime());
            insertTopic("t16","拾陸.含各種離子高的食物參考.pdf",0, datetime());
            insertTopic("t17","拾柒.飲食控制要點.pdf",0, datetime());
            insertTopic("t18","拾捌.傷口加壓止血的方法.pdf",0, datetime());
            insertTopic("t19","拾玖.藥物的使用.pdf",0, datetime());
            insertTopic("t20","HELP.pdf",0, datetime());

        }
        cursor.close();
    }

    private void createStudyTable()//閱讀紀錄
    {
        String sql = "CREATE TABLE IF NOT EXISTS Study (study_id TEXT, study_date DateTime, topic_id char(10), patient_id char(10), nurse_id char(10),change_data DATETIME,  PRIMARY KEY(study_id), FOREIGN KEY (topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createQuestionTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Question (question_id INT, question_content TEXT, question_answer INT, question_explain TEXT, topic_id char(10), change_data DATETIME, PRIMARY KEY(question_id), FOREIGN KEY(topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
        Cursor cursor = db.rawQuery("SELECT * FROM Question", null);
        if (!cursor.moveToFirst()) {

            //t1的部分  壹.腎臟功能簡介
            insertQuestion( 1, "腎臟是一對位於後腹腔的器官，右腎較左腎略低。", 1, "","t1", datetime());
            insertQuestion( 2, "腎臟是人體主要的排毒器官，負責清除血液中的代謝廢物。", 1, "",   "t1", datetime());
            insertQuestion( 3, "腎臟所分泌的腎素（Renin），為調節血壓維持恆定重要的荷爾蒙。", 1, "",    "t1", datetime());
            insertQuestion( 4, "每個人每天約有3000毫升（ML）的尿液。", 0, "每天約有1,500～2,000毫升（ML）的尿液。", "t1", datetime());
            insertQuestion( 5, "體內水份過多排不出時，血壓也可能會升高。", 1, "", "t1", datetime());
            insertQuestion( 6, "當腎臟功能喪失，會導致體內毒素無法排除，造成尿毒症。", 1,"","t1", datetime());
            insertQuestion( 7, "腎絲球廓清率下降至每分鐘30～35毫升時，稱做末期腎衰竭(尿毒)。", 0,  "是屬於中度慢性腎臟病，如控制不好，離末期腎衰竭也不遠了。", "t1", datetime());
            insertQuestion( 8, "腎絲球廓清率下降至每分鐘5～15毫升時，稱做末期腎衰竭(尿毒)。", 1,"", "t1", datetime());
            insertQuestion( 9, "腎臟功能喪失，鉀離子排泄下降，嚴重時會導致死亡。", 1,"", "t1", datetime());
            insertQuestion( 10, "腎臟功能喪失，不會造成貧血。", 0,"腎臟功能下降時，會影響血球的功能，且會影響紅血球生素的製造，故會產生貧血。", "t1", datetime());
            insertQuestion( 11, "腎臟功能不良時，初期容易感到疲倦、倦怠、食慾不振、失眠等症狀。", 1, "",  "t1", datetime());

            //t2的部分  貳.甚麼是慢性腎臟病
            insertQuestion( 12, "腎臟受損超過三個月，結構或功能無法恢復正常時，稱為「急性腎損傷」。", 0,  "為慢性腎臟病才對。", "t2", datetime());
            insertQuestion( 13, "腎臟受損超過三個月，結構或功能無法恢復正常時，稱為「慢性」腎臟病。", 1,  "", "t2", datetime());
            insertQuestion( 14, "一旦發生慢性腎臟病，現行醫學並無法恢復腎功能，只能減緩腎臟功能衰退的速度。", 1,  "", "t2", datetime());
            insertQuestion( 15, "急性腎損傷經過適當的治療，大部分可使腎功能恢復正常。", 1,  "", "t2", datetime());
            insertQuestion( 16, "腎臟病徵兆的口訣：泡、水、高、貧、倦。", 1,  "", "t2", datetime());
            insertQuestion( 17, "當腎絲球過濾率衰退至每分鐘30-59毫升，就需要開始透析治療。", 0,  "腎絲球過濾率小於每分鐘15毫升，才需依臨床症狀決定是否立即透析治療。", "t2", datetime());
            insertQuestion( 18, "慢性腎衰竭，小便常可以看到泡泡尿。", 1,  "", "t2", datetime());
            insertQuestion( 19, "當看到小便出現許多泡泡，可能是因為解尿衝擊造成的的水波，沒有關係。", 0,  "", "t2", datetime());
            insertQuestion( 20, "慢性腎衰竭常發生高血鉀，但不會致死。", 0,  "高血鉀會產生心律不整，嚴重會致死。", "t2", datetime());
            insertQuestion( 21, "腎衰竭病人口腔有金屬味或尿味，主要是牙周病。", 0,"是體內含氮廢物未能排除所導致的味道。", "t2", datetime());
            insertQuestion( 22, "腎衰竭病人水腫常見於下肢與腳踝水腫以及晨間眼部浮腫。", 1,  "", "t2", datetime());

            //t3的部分   參.腎衰竭的原因
            insertQuestion( 23, "糖尿病腎病變已變為末期腎衰竭之首要原因。", 1,  "", "t3", datetime());
            insertQuestion( 24, "血壓控制不良，也會造成腎臟功能惡化。", 1,  "", "t3", datetime());
            insertQuestion( 25, "抽菸會增加致癌的風險，也會對腎臟的血管造成傷害。", 1,  "", "t3", datetime());
            insertQuestion( 26, "抽菸主要傷害肺部，不會對腎臟血管有傷害。", 0,  "吸菸會影響腎臟動脈的變化，使腎臟血管收縮及血流速度減少，刺激血管收縮的功能，使血壓上昇，而且也會增加蛋白尿的情形加重，造成腎功能惡。", "t3", datetime());
            insertQuestion( 27, "腎臟功能隨著老化的過程逐年衰退，所以要小心愛護。", 1,  "", "t3", datetime());
            insertQuestion( 28, "老化也會讓腎臟功能下降。", 1,  "", "t3", datetime());
            insertQuestion( 29, "家族中若有人患有腎臟病，則家人得到腎臟病的機會較高。", 1,  "", "t3", datetime());
            insertQuestion( 30, "經常服用消炎類止痛藥不會造成腎臟傷害。", 0, "這類藥物影響腎臟血液灌流，破壞腎臟自我調節的機制而造成急性腎衰竭，此外，也會對腎小管造成破壞而產生間質性腎炎。", "t3", datetime());
            insertQuestion( 31, "尿酸會沉積在腎組織，影響腎功能。", 1,  "", "t3", datetime());
            insertQuestion( 32, "引起痛風主要的原因是尿酸過低。", 0, "尿酸過高。", "t3", datetime());
            insertQuestion( 33, "前列腺良性肥大導致尿路阻塞會影響腎臟功能。", 1, "", "t3", datetime());
            insertQuestion( 34, "血壓高，不會影響腎臟的問題。", 0,"會加重血管硬化，影響腎臟功能。", "t3", datetime());
            insertQuestion( 35, "中藥藥性溫和，多吃並不會造成腎臟傷害。", 0, "過多無法排除的藥物，均可能造成腎臟傷害。", "t3", datetime());
            insertQuestion( 36, "中藥裏的補藥，既然是補藥，當然多吃無害。", 0, "補藥過量也會造成腎臟無法負荷，產生腎臟傷害。", "t3", datetime());
            insertQuestion( 37, "腎臟反覆發炎時，只要治好，腎臟功能就都不會有影響。", 0, "死亡的腎臟細胞是無法再生的，腎臟反覆發炎，表示反覆受傷，會造成腎功能下降。", "t3", datetime());

            //t4  肆.腎衰竭的治療方法
            insertQuestion( 38, "血液透析及腹膜透析都是腎衰竭的替代療法。", 1, "", "t4", datetime());
            insertQuestion( 39,"腎移植不是腎衰竭的替代療法。", 0, "腎移植是腎衰竭的代替療法之一。", "t4", datetime());
            insertQuestion( 40, "血液透析俗稱「洗腎」，意思就是把腎臟拿出來洗。", 0, "不是將腎臟拿出來洗，而是透過機器、人工腎臟透析器、透析液，經過毒素的交換及移除，排除體內廢物，達成洗腎的目的。", "t4", datetime());
            insertQuestion( 41, "腹膜透析可以在家中自行操作，達到洗腎的目的。", 1, "", "t4", datetime());
            insertQuestion( 42, "腎移植時，是把原來的腎臟拿掉，換一個新的腎臟。", 0, "是在骨盆腔的腸骨窩處植入另一個健康的腎臟，觸摸腹股溝上方即可摸到新的腎臟。", "t4", datetime());
            insertQuestion( 43, "換腎時新的移植腎臟位置是放在腸骨窩處。", 1, "", "t4", datetime());
            insertQuestion( 44, "腎臟移植分有親屬捐腎或屍腎移植。", 1, "", "t4", datetime());
            insertQuestion( 45, "血液透析洗得比腹膜透析好。", 0, "兩者的治療都可以達到一定毒素的清除，主要仍需依病患的需求及方便性，選擇合適的方法。", "t4", datetime());
            insertQuestion( 46, "保守療法主要在控制含氮廢物的產生。", 1, "", "t4", datetime());
            insertQuestion( 47, "保守療法必需要控制原發疾病的問題及低蛋白飲食。", 1, "", "t4", datetime());

            //t5的部分 伍.健康人如何保護自己的腎
            insertQuestion( 48, "有病治病，無病強身，所以補品和健康食品都可以多吃無害。", 0,"補品及健康食品吃過多時，仍可能造成腎臟負擔，且許多的添加也可能會導致腎臟傷害。", "t5", datetime());
            insertQuestion( 49, "補藥、地攤藥、「健康食品」、減肥藥、止痛劑、類固醇、抗生素及不明來歷的藥品等，這些都可能傷害我們的腎臟。", 1,"", "t5", datetime());
            insertQuestion( 50, "美國糖尿病學會建議三餐前全血血糖控制在80-120毫克/毫升。", 1,"", "t5", datetime());
            insertQuestion( 51, "即使大吃大喝，只要每天都有在運動，並不會影響身體健康。", 0, "過度大吃大喝本來就對身體造成負荷影響健康。", "t5", datetime());
            insertQuestion( 52, "當收縮壓常態性超過140毫米汞柱時，代表已經有輕度的高血壓跡象。", 1, "", "t5", datetime());
            insertQuestion( 53, "高血壓前期，還不至於高血壓，所以可以不管他。", 0,  "如未積極介入預防，會加快高血壓的進程。", "t5", datetime());
            insertQuestion( 54, "腎臟保養之道應該要生活起居正常、避免腎毒性物質（如止痛劑、顯影劑、消炎藥）、攝取充足的水份、多運動等。", 1,  "", "t5", datetime());
            insertQuestion( 55, "因為時常會頭痛，所以固定吃止痛藥沒關係。", 0,  "長期服用止痛藥，就可能會造成腎臟病變。", "t5", datetime());
            insertQuestion( 56, "常態性收縮壓在120-139，舒張壓80-89毫米汞柱，代表有高血壓前期的癥症。", 1,  "", "t5", datetime());
            insertQuestion( 57, "適當的運動有助於血壓及血糖的控制。", 1,  "", "t5", datetime());


            //t6   陸.血液透析
            insertQuestion( 58, "血液透析就是一般人俗稱的洗腎。", 1, "", "t6", datetime());
            insertQuestion( 59,"血液透析一週需要治療三次，每次4小時。", 1, "", "t6", datetime());
            insertQuestion( 60, "一旦需要長期透析治療，就要執行動靜脈廔管手術。", 1, "", "t6", datetime());
            insertQuestion( 61, "血液透析是利用濃度及壓力差，進行物質交換，來矯正因水份、電解質、酸鹼及有毒物質所引起的內在環境改變。", 1, "", "t6", datetime());
            insertQuestion( 62, "洗腎就是把腎臟拿出來洗。", 0, "不是將腎臟拿出來洗，而是透過機器、人工腎臟透析器、透析液，經過毒素的交換及移除，排除體內廢物，達成洗腎的目的。", "t6", datetime());
            insertQuestion( 63, "洗腎可以不舒服再到醫院洗，不用規則洗。", 0, "沒有規則透析，一容易發生來不及透析的危險，也容易導致其他合併症的發生，造成更多的不舒服。", "t6", datetime());
            insertQuestion( 64, "小便量還很多，所以可以不需要常規洗腎。", 0, "洗腎不是只有排除水份的功能，還包括體內廢物毒素的排除。", "t6", datetime());
            insertQuestion( 65, "人工腎臟透析器可以完全取代腎臟的功能。", 0, "人工腎臟只能過濾掉大部份體內產生的廢物，仍有許多物質不容易被排除，因此不能完全取代腎臟功能。", "t6", datetime());
            insertQuestion( 66, "緊急血液透析時的血管通路可先插暫時性的雙腔導管。", 1, "", "t6", datetime());
            insertQuestion( 67, "人工腎臟透析器的作用就是在移除體內多餘的毒素及水份。", 1, "", "t6", datetime());


            //t7   柒.血管通路
            insertQuestion( 68, "廔管開刀的手可以搬重物及運動，再大的力量也不會影響。", 0, "廔管的手若搬過重的重物，可能會導致血流的阻斷，使血管沒有功能。", "t7", datetime());
            insertQuestion( 69, "瘻管的手可以做治療，例如：打針、抽血、量血壓等。", 0, "要避免，以免受傷，影響功能。", "t7", datetime());
            insertQuestion( 70, "瘻管開刀的手仍可以當枕頭墊，並不會傷害血管。", 0, "會造成廔管血管壓迫，阻斷血流使血管沒有功能。", "t7", datetime());
            insertQuestion( 71, "透析前清洗廔管，可以降低感染的機會。", 1, "", "t7", datetime());
            insertQuestion( 72, "透析中發現打針的部位疼痛並漏血，應馬上跟醫護人員反應。", 1, "", "t7", datetime());
            insertQuestion( 73, "視血管的健康程度，可每3或6 個月定期安排看診心臟血管內或外科，可提前覺查是否有異常。", 1, "", "t7", datetime());
            insertQuestion( 74, "廔管的手如果發現紅、腫、熱、痛的現象要立即告知醫護人員。", 1, "", "t7", datetime());
            insertQuestion( 75, "洗腎結束拔掉穿刺針，穿刺處用手加壓越大力越好，才不會流血。", 0, "過度加壓，會使廔管的血管被壓扁，影響功能，嚴重可能使血管損壞。", "t7", datetime());
            insertQuestion( 76, "透析血管通路是透析病人的第二生命，損壞就無法透析。", 1,"", "t7", datetime());
            insertQuestion( 77, "每隔兩天會洗腎，所以瘻管是否通暢無阻塞，由護理師檢查就好了，不用擔心。", 0, "應該每天都要自我檢查，等透析當日才由護理師檢查，如果已經沒有功能，當日就無法進行透析。", "t7", datetime());
            insertQuestion( 78, "需要長期透析的病人，需要建立永久性的血管通路較佳。", 1, "", "t7", datetime());
            insertQuestion( 79, "吸煙不會影響血管廔管的健康。", 0, "吸菸會造成血管收縮及血流速度減少，影響廔管的健康。", "t7", datetime());
            insertQuestion( 80, "熱敷時，愈熱才會愈有效果。", 0, "容易燙傷以外，更可能損壞廔管功能。", "t7", datetime());
            insertQuestion( 81, "廔管部位要熱敷時，溫度要請家人看是否會太熱，以免燙傷。", 1, "", "t7", datetime());
            insertQuestion( 82, "握球運動可以增強血流及增強肌肉組織。", 1, "", "t7", datetime());


            //t8   捌.動靜脈廔管的照顧
            insertQuestion( 83, "廔管開刀的手可以搬重物及運動，再大的力量也不會影響。", 0, "", "t8", datetime());
            insertQuestion( 84, "廔管的手可以做治療，例如：打針、抽血、量血壓等。", 0, "", "t8", datetime());
            insertQuestion( 85, "廔管開刀的手仍可以當枕頭墊，並不會傷害血管。", 0, "", "t8", datetime());
            insertQuestion( 86, "透析前清洗廔管，可以降低感染的機會。", 1, "", "t8", datetime());
            insertQuestion( 87, "透析中發現打針的部位疼痛並漏血，應馬上跟醫護人員反應。", 1, "", "t8", datetime());
            insertQuestion( 88, "視血管的健康程度，可每3或6 個月定期安排看診心臟血管內或外科，可提前覺查是否有異常。", 1, "", "t8", datetime());
            insertQuestion( 89, "廔管的手如果發現紅、腫、熱、痛的現象要立即告知醫護人員。", 1, "", "t8", datetime());
            insertQuestion( 90, "洗腎結束拔掉穿刺針，穿刺處用手加壓越大力越好，才不會流血。", 0, "", "t8", datetime());
            insertQuestion( 91, "透析血管通路是透析病人的第二生命，損壞就無法透析。", 1, "", "t8", datetime());
            insertQuestion( 92, "每隔兩天會洗腎，所以瘻管是否通暢無阻塞，由護理師檢查就好了，不用擔心。", 0, "", "t8", datetime());
            insertQuestion( 93, "需要長期透析的病人，需要建立永久性的血管通路較佳。", 1, "", "t8", datetime());
            insertQuestion( 94, "吸煙不會影響血管廔管的健康。", 0, "", "t8", datetime());
            insertQuestion( 95, "熱敷時，愈熱才會愈有效果。", 0, "", "t8", datetime());
            insertQuestion( 96, "廔管部位要熱敷時，溫度要請家人看是否會太熱，以免燙傷。", 1, "", "t8", datetime());
            insertQuestion( 97, "握球運動可以增強血流及增強肌肉組織。", 1, "", "t8", datetime());
            insertQuestion( 98, "洗腎前應確認廔管表面的皮膚是否完整無任何傷口。", 1, "", "t8", datetime());
            insertQuestion( 99, "每天有洗澡，就代表手部及廔管處是乾淨的不需再特別清潔。", 0, "", "t8", datetime());
            insertQuestion( 100, "打針時，如果手部清潔沒有做好，容易造成血管廔管感染，嚴重甚至引發心內膜炎。", 1, "", "t8", datetime());
            insertQuestion( 101, "打針前，手部的髒污，靠消毒就可以了，不須特別洗手及清潔廔管。", 0, "", "t8", datetime());
            insertQuestion( 102, "養成洗腎前洗清洗血管廔管，可以將感染的風險降低。 ", 1, "", "t8", datetime());
            insertQuestion( 103, "熱敷可以保養血管，所以愈熱愈好、愈久愈好。", 0, "", "t8", datetime());
            insertQuestion( 104, "透析前應於血管廔管處，以肥皂清潔乾淨，降低穿刺感染機會。", 1, "", "t8", datetime());
            insertQuestion( 105, "為避免打針會痛，所以注射的位置不要輪流更換。", 0, "", "t8", datetime());
            insertQuestion( 106, "透析結束止血後，為避免血管阻塞，應立即開始握球運動。", 0, "", "t8", datetime());
            insertQuestion( 107, "只要洗腎前檢查廔管是否有震顫感或聽看看是否有嘈音即可。", 0, "", "t8", datetime());


            //t9    玖.腎友皮膚搔癢怎麼辦
            insertQuestion( 108, "末期腎衰竭病人，有時皮膚上可見粉末狀的沈積物，稱為尿毒霜。", 1, "", "t9", datetime());
            insertQuestion( 109, "尿毒霜的沈積物容易導致汗腺的萎縮與破壞，導致皮膚更癢。", 1, "", "t9", datetime());
            insertQuestion( 110, "末期腎衰竭時，皮膚上粉末狀的沈積物，是因為洗澡洗不乾淨造成的。", 0, "皮膚上可見粉末狀的沈積物，通常是因為身體內磷質太高，導致沈積在皮膚上，稱為尿毒霜。", "t9", datetime());
            insertQuestion( 111, "不要用太熱的水及避免用肥皂，可減少皮膚上的油脂洗得太乾淨，增加搔癢的症狀。", 1,  "", "t9", datetime());
            insertQuestion( 112, "棉質較不刺激皮膚的衣物，可減輕皮膚搔癢的刺激。。", 1, "","t9",datetime());
            insertQuestion( 113, "皮脂腺或汗腺萎縮會讓皮膚更乾躁。", 1, "", "t9", datetime());
            insertQuestion( 114, "乳液選擇愈油的擦，皮膚較不會癢。", 0, "保濕性佳親水性的乳液效果較好。", "t9", datetime());
            insertQuestion( 115, "透析病人因血小板功能較差，且透析時常需使用抗凝劑，容易抓破流血難止，因此要儘量避免用力抓癢。", 1, "", "t9", datetime());
            insertQuestion( 116, "高磷會加重皮膚搔癢的情形。", 1, "", "t9", datetime());
            insertQuestion( 117, "用熱水洗澡，可以減輕搔癢情形。", 0,  "不可過熱，太熱會讓皮膚表皮的油脂洗掉更多，失去滋潤效果。", "t9", datetime());


            //t10    拾.透析患者睡眠問題
            insertQuestion( 118, "睡眠障礙的治療包括非藥物的治療及藥物治療。", 1, "", "t10", datetime());
            insertQuestion( 119, "末期腎臟病患者的失眠致病機轉目前尚未完全釐清。", 1, "", "t10", datetime());
            insertQuestion( 120, "失眠(Insomnia)的定義：為無法入睡、無法保持睡眠狀態，或即使有足夠的睡眠時間仍感到疲倦，而影響到情緒認知功能及生活品質。", 1, "", "t10", datetime());
            insertQuestion( 121, "睡眠問題在慢性腎病的患者中是很常見的，包含失眠、不寧腿症候群以及睡眠呼吸中止症等導致的睡眠障礙。", 1, "", "t10", datetime());
            insertQuestion( 122, "佈置良好的睡眠環境、減少不必要的干擾有效促進睡眠品質。 ", 1, "", "t10", datetime());
            insertQuestion( 123, "研究指出影響透析病患睡眠品質的可能原因包括：電解質、缺鐵、貧血、體液過多、藥物、服用酒精及刺激性的飲料或吸菸等。", 1, "", "t10", datetime());
            insertQuestion( 124, "保持規律而固定的就寢及起床時間不要故意長期睡過多、過少、或太晚睡、避免日夜顛倒的生活有效促進睡眠品質。", 1, "", "t10", datetime());
            insertQuestion( 125, "平時減少咖啡因的攝取、戒菸、戒酒,尤其就寢前避免咖啡因、酒精或尼古丁有效促進睡眠品質。", 1, "", "t10", datetime());


            //t11   拾壹.腎友如何預防便祕
            insertQuestion( 126, "每天要規律運動，可促進腸蠕動。", 1, "", "t11", datetime());
            insertQuestion( 127, "養成定時排便的習慣，較不容易發生便秘的情形", 1, "", "t11", datetime());
            insertQuestion( 128, "排便順暢，較不容易影響體內水分精確的計算，引起透析中的超濾不當。", 1, "", "t11", datetime());
            insertQuestion( 129, "要養成規律服藥，所以已經腹瀉了，仍要吃緩瀉或軟便藥。", 0, "已經腹瀉就不要再吃，以免加重腹瀉症狀。", "t11", datetime());
            insertQuestion( 130, "緩瀉或軟便藥，有助於排泄，所以需要在洗腎前服用。", 0, "有可能會造成透析中急著想解便不適，甚至來不及收機去解便，而解在褲子裏的窘境。", "t11", datetime());
            insertQuestion( 131, "服用緩瀉或軟便藥，發生排便次數過多是正常情形，因此繼續服用就對了。", 0, "應告知醫師調整藥物量。", "t11", datetime());
            insertQuestion( 132, "多食用富含水溶性纖維食物，有助於排便順暢。", 1, "", "t11", datetime());
            insertQuestion( 133, "過度用力排便可能導致隱藏性心腦血管事件發生危及生命。", 1, "", "t11",datetime());
            insertQuestion( 134, "糞便停留時間太長，鉀離子排除的途徑相對下降。", 1, "", "t11", datetime());
            insertQuestion( 135, "腎友常需要服用的鈣片、胃乳片、鐵劑，容易造成便秘，所以乾脆不要吃就好了。", 0, "鈣片、胃乳片、鐵劑，仍屬於腎友治療的一部份，不吃反而可能造成其他合併症，因此仍需要按時服用，而所導致的便秘問題，則只能靠服用緩瀉或軟便藥治療。", "t11", datetime());


            //t12  拾貳.乾體重
            insertQuestion( 136, "乾體重意思就是：洗完腎、脫完水、患者沒有不舒服，而且透析前血壓正常，透析後血壓不會過低，最低的適合體重。", 1, "", "t12", datetime());
            insertQuestion( 137, "兩次透析間體重的增加以不超過乾體重的5%為主。", 1,"", "t12", datetime());
            insertQuestion( 138, "當食慾變好，體重容易增加，所以要調低乾體重。", 0, "要調高才對。", "t12", datetime());
            insertQuestion( 139, "洗腎前、洗腎後量體重，身上的裝備要相同，以免造成誤差。", 1,  "", "t12", datetime());
            insertQuestion( 140, "透析中吃東西不需要加入脫水量。",0, "透析進食，食物和飲品也要秤重，並加入脫水量中，才不會設量的脫水有誤差。", "t12", datetime());
            insertQuestion( 141, "量體重時，體重計可以任意擺放。", 0, "量體重的磅秤最好放在固定的位置，避免移來移去影響準確度。", "t12", datetime());
            insertQuestion( 142, "量體重時，要扶著牆壁，以預防跌倒。",0, "量體重時，身體不可靠牆壁，但可以扶體重機的扶手。", "t12", datetime());
            insertQuestion( 143, "發生耳鳴、頭暈、口乾舌燥、胸悶、血壓下降、抽筋等情形，可能是長胖了。", 1, "", "t12", datetime());
            insertQuestion( 144, "乾體重若控制得好，可以避免心臟衰竭及減少合併症的發生。", 1,"", "t12", datetime());
            insertQuestion( 145, "量體重前，一定要先確定體重計上的指標是否歸零。", 1,"", "t12", datetime());



            //t13  拾叁.磷是啥
            insertQuestion( 146, "血磷為人體最高的礦物質。", 0, "第二高，僅次於鈣。", "t13", datetime());
            insertQuestion( 147, "吃進含磷食物後,人體主要由腎臟及腸道排出。", 1, "", "t13", datetime());
            insertQuestion( 148, "正常血磷值為2.5~4.5mg/dl之間。", 1, "", "t13", datetime());
            insertQuestion( 149, "低血磷多為疾病引起，只要針對病因加以治療即可獲得改善。", 1, "", "t13", datetime());
            insertQuestion( 150, "腎衰竭病患因腎臟排磷能力降低導致血磷滯留堆積體內而造成高血磷。", 1, "", "t13", datetime());
            insertQuestion( 151, "因血磷存在於大部分的食物中,避免高血磷,要先從飲食控制開始。", 1, "", "t13", datetime());
            insertQuestion( 152, "如何達到高蛋白又避免高血磷--注意減少喝火鍋湯、肉汁、大骨湯,還有肉汁拌飯。", 1, "", "t13", datetime());
            insertQuestion( 153, "加工食品中的添加物多為無機磷,容易100%被人體吸收造成高血磷。", 1, "", "t13", datetime());
            insertQuestion( 154, "常見降磷藥有自費及健保給付,服用時要注意需要咬碎或磨粉,有的不可咬碎。", 1, "", "t13", datetime());
            insertQuestion( 155, "腎友對血磷的控制有賴於飲食控制、規則透析和降磷藥的使用。", 1, "", "t13", datetime());
            insertQuestion( 156, "指數>6.5mg/dl即為高血磷症。", 0, "大於>5.5mg/dl即為高血磷症。", "t13", datetime());
            insertQuestion( 157, "過多磷堆積在皮膚造成全身搔癢難耐。", 1, "", "t13", datetime());
            insertQuestion( 158, "磷太高會刺激副甲狀腺造成副甲狀腺機能亢進,使儲存在骨骼中的鈣、磷釋出導致骨痛、易骨折。", 1, "", "t13", datetime());
            insertQuestion( 159, "服用鈣片時不需要咬碎，才不會影響效果。", 0, "要咬碎，才能增加與食物結合的效果，發揮結合磷的功效。", "t13", datetime());

            //t14 拾肆.鉀啥咪
            insertQuestion( 160, "洗腎病人可以自行至中藥行抓藥進補。", 0, "中藥草均富含鉀離子，自行進補，可能鉀離子會過高。", "t14", datetime());
            insertQuestion( 161, "蔬菜時要燙過，可以降低鉀離子的攝取。", 1, "", "t14", datetime());
            insertQuestion( 162, "腎友體內水份（重水）過多，容易造成鉀離子清除效率變差。", 1, "", "t14", datetime());
            insertQuestion( 163, "洗腎病人可以食用保健食品當作治療藥物。", 0, "保健食品成份複雜，而且許多含鉀量標示有限，無法了解所吃進去的含鉀量是多少，容易發生危險。", "t14", datetime());
            insertQuestion( 164, "便祕容易造成鉀離子排出的途徑減少。", 1, "", "t14", datetime());
            insertQuestion( 165, "腎友出現手指嘴唇僵硬、肌肉僵硬無力、舌頭僵硬難說話的症狀可能是因為鉀升變。", 1, "", "t14", datetime());
            insertQuestion( 166, "怕吃的太鹹，可以改用低鈉鹽或薄鹽醬油。", 0, "不行，因為低鈉鹽或薄鹽醬油多半是用鉀鹽取代鈉。", "t14", datetime());
            insertQuestion( 167, "高血鉀是血中鉀離子超過正常生理上限值 5.5 mmol/L。", 1, "", "t14", datetime());
            insertQuestion( 168, "夏天到了，洗腎病人可以喝很多冰涼果汁。", 0, "不行，除了水果中的鉀離子含量會過多以外，水份也可能增加過多。", "t14", datetime());
            insertQuestion( 169, "楊桃及楊桃汁雖不是高鉀水果，但部份會引發腎毒及神經毒性，所以絕對要吃。", 1, "", "t14", datetime());

        }
        cursor.close();
    }

    private void createExamTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Exam (exam_id TEXT, exam_date DateTime, exam_score INT, patient_id char(10), nurse_id char(10),change_data DATETIME,  PRIMARY KEY(exam_id), FOREIGN KEY(patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createAnswerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Answer (answer_id TEXT,result INT,  question_id INT, exam_id INT,change_data DATETIME,  PRIMARY KEY(exam_id, question_id), FOREIGN KEY(exam_id) REFERENCES Exam(exam_id)ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(question_id) REFERENCES Question(question_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void insertTopic(String topic_id,String topic_name,int vidio, String change_data )
    {
        ContentValues cv =new ContentValues(1);//10
        cv.put("topic_id",topic_id);
        cv.put("topic_name",topic_name);
        cv.put("vidio",vidio);
        cv.put("change_data",change_data);
        db.insert("Topic", null, cv);
    }

    private void insertQuestion(int question_id,String content,int question_answer,String explain, String topic_id,String change_data)
    {
        ContentValues cv =new ContentValues(1);//10
        cv.put("question_id",question_id);
        cv.put("question_content",content);
        cv.put("question_answer",question_answer);
        cv.put("question_explain",explain);
        cv.put("topic_id",topic_id);
        cv.put("change_data",change_data);
        db.insert("Question", null, cv);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("確定要結束應用程式嗎?")
                    //  .setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
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
        return true;
    }

    public void end(View v){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("確定要結束應用程式嗎?")
                //  .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("確定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
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

    public void choicepatient(View v) {
        //跳轉到病人畫面
        String str=Account.getText().toString().trim().toUpperCase();
        String pas=editText.getText().toString().trim();
        pas=pas.toUpperCase();
        if(str.equals("") && pas.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("帳號密碼沒有輸入!!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else if(str.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("帳號沒有輸入!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else if(pas.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("密碼沒有輸入!!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else
        {
            pas=sha256(pas);
            Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+str+"'",null);
            if (str.trim().length()>0){
                if ("admin".equals(pas)){
                    AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                            .setTitle("此權限沒有病友管理!!!")
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
                        mTitleView.setTextColor(Color.RED);
                        //通过反射修改message字体大小和颜色
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e2) {
                        e2.printStackTrace();
                    }
                }
                else {
                    if(cu.getCount()>0) {
                        cu.moveToFirst();
                        do {
                            String password=cu.getString(2);
                            int flag_staue=cu.getInt(3);
                            if (password.equals(pas) && flag_staue==1){
                                intent.setClass(this, Searchlogin.class);
                                intent.putExtra("pad",1); //修改狀態時 要分辨是哪一台電腦用的。
                                intent.putExtra("nurseID",str);
                                intent.putExtra("nurse",str);
                                // intent.putExtra("name", Account.getText().toString());
                                db.close();
                                startActivity(intent);
                                finish();
                            }
                            else if (password.equals(pas) && flag_staue!=1){
                                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("沒有權限!!!")
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
                                    mTitleView.setTextColor(Color.RED);
                                    //通过反射修改message字体大小和颜色
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
                                } catch (NoSuchFieldException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            else {
                                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("密碼輸入錯誤!!!")
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
                                    mTitleView.setTextColor(Color.RED);
                                    //通过反射修改message字体大小和颜色
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
                                } catch (NoSuchFieldException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }while(cu.moveToNext());
                    }
                    else {
                        AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("帳號輸入錯誤!!!")
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
                            mTitleView.setTextColor(Color.RED);
                            //通过反射修改message字体大小和颜色
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchFieldException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            cu.close();
        }

    }

    public void back(View v) {
        String str1=Account.getText().toString().trim().toLowerCase();
        String pas=editText.getText().toString().trim().toLowerCase();
        pas=pas.toUpperCase();
        if(str1.equals("") && pas.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("帳號密碼沒有輸入!!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else if(str1.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("帳號沒有輸入!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else if(pas.equals(""))
        {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("密碼沒有輸入!!!")
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
                mTitleView.setTextColor(Color.RED);
                //通过反射修改message字体大小和颜色
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        else
            {
                pas=sha256(pas);
                if (str1.trim().length()>0)//確定有輸入東西，不是誤按。
                {
                    // String str_pas=pas=sha256("admin");
                    if ("admin".equals(str1))//只有管理員可以進入後台管理。
                    {
                        Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+"admin"+"'",null);
                        if(cu.getCount()>0) {
                            cu.moveToFirst();
                            do {
                                String password=cu.getString(2);
                                if (password.equals(pas) )//輸入正確帳號密碼
                                {
                                    Intent i = new Intent(MainActivity.this, Menu.class);
                                    db.close();;
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("密碼輸入錯誤!!!")
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
                                        mTitleView.setTextColor(Color.RED);
                                        //通过反射修改message字体大小和颜色
                                    } catch (IllegalAccessException e1) {
                                        e1.printStackTrace();
                                    } catch (NoSuchFieldException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }while(cu.moveToNext());
                        }
                        cu.close();
                    }
                    else {
                        AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("沒有後台管理權限!!!")
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
                            mTitleView.setTextColor(Color.RED);
                            //通过反射修改message字体大小和颜色
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchFieldException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }

    }

    public void help(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this , HealthInformation.class);
        intent.putExtra("health_education","t20");
        intent.putExtra("flag",99);//顯示PDF的地方 按鈕要改變 跳轉道的葉面要改
        startActivity(intent);
        finish();
    }

    public void data_Transfer(View v)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this , DataTransfer.class);
        intent.putExtra("health_education","t20");
        intent.putExtra("flag",99);//顯示PDF的地方 按鈕要改變 跳轉道的葉面要改
        startActivity(intent);
        finish();
    }

}
