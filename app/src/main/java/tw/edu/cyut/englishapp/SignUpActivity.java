package tw.edu.cyut.englishapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.android.volley.VolleyLog.TAG;


public class SignUpActivity extends AppCompatActivity {

    String username, mail, pwd, name, re_pwd,country,tbackground,Learninghours,certification,sex,year,month,day,native_language,learningyears,o_language,education,learingmethod,level,age;
    Spinner sp_sex,sp_years,sp_month,sp_day,sp_learing_method,average,teacher_background,language_level,certificate;
    EditText teacher_other,method_other,certificate_other;
    private  Boolean isExit = false;
    private  Boolean hasTask = false;

    Timer timerExit = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };

    //按兩次Back退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判斷是否按下Back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否要退出
            if(!isExit ) {
                isExit = true; //記錄下一次要退出
                Toast.makeText(this, "Press Back again to exit the app."
                        , Toast.LENGTH_SHORT).show();
                // 如果超過兩秒則恢復預設值
                if(!hasTask) {
                    timerExit.schedule(task, 2000);
                }
            } else {
                finish(); // 離開程式
                System.exit(0);
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initSignUpActivity();

        AlertDialog("Registration","You are about to register for the Chinese Tones Learning Experiment. The research requires some basic background data and information about your Chinese learning experience. Please answer these questions as precisely as possible, according to your present situation. After answering the background questions, you will move on to the pretest. \nYou are also invited to participate in the learners’ beliefs questionnaire study. If you wish to participate in the study, please press “OK”, otherwise press “Skip”.");
        final Button SignUp = (Button) findViewById(R.id.sign);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pwd = ((EditText) findViewById(R.id.password)).getText().toString();
                re_pwd = ((EditText) findViewById(R.id.re_password)).getText().toString();
                name = ((EditText) findViewById(R.id.name)).getText().toString();
                mail = ((EditText) findViewById(R.id.mailText)).getText().toString();
                username=((EditText) findViewById(R.id.username)).getText().toString();
                country=((EditText) findViewById(R.id.country)).getText().toString();
                native_language=((EditText) findViewById(R.id.native_language)).getText().toString();
                learningyears=((EditText) findViewById(R.id.learing_years)).getText().toString();
                o_language=((EditText) findViewById(R.id.other_language)).getText().toString();
                education=((EditText) findViewById(R.id.education)).getText().toString();
                year=sp_years.getSelectedItem().toString();
                sex=sp_sex.getSelectedItem().toString();
                month=sp_month.getSelectedItem().toString();
                day=sp_day.getSelectedItem().toString();
                tbackground=teacher_background.getSelectedItem().toString();
                learingmethod=sp_learing_method.getSelectedItem().toString();
                Learninghours=average.getSelectedItem().toString();
                certification=certificate.getSelectedItem().toString();
                level=language_level.getSelectedItem().toString();

                String now_date=getDateNow();
                age=date_count(year+"-"+month+"-"+day,now_date);
                if (!teacher_background.getSelectedItem().toString().equals("other"))
                    tbackground=teacher_background.getSelectedItem().toString();
                else
                    tbackground=teacher_other.getText().toString();

                if (!sp_learing_method.getSelectedItem().toString().equals("other"))
                    learingmethod=sp_learing_method.getSelectedItem().toString();
                else
                    learingmethod=method_other.getText().toString();

                if (!certificate.getSelectedItem().toString().equals("other"))
                    certification=certificate.getSelectedItem().toString();
                else
                    certification=certificate_other.getText().toString();

                if ( name.equals("")|| pwd.equals("")|| re_pwd.equals("")|| username.equals("")|| tbackground.equals("")||age.equals("")||
                        sex.equals("")||country.equals("")||year.equals("")||month.equals("")||day.equals("")||certification.equals("")|| level.equals("")||
                        Learninghours.equals("")|| mail.equals("") || learingmethod.equals("")||native_language.equals("")||o_language.equals("")||learningyears.equals("")||
                        education.equals("")|| !pwd.equals(re_pwd) ||username.contains("'")||pwd.contains("'")||pwd.length()<8 || !MailCheck(mail)) {
                     Toast.makeText(SignUpActivity.this,"There were problems creating your account.",Toast.LENGTH_SHORT).show();

                } else{
                    Backgorundwork backgorundwork = new Backgorundwork(SignUpActivity.this);
                    backgorundwork.execute("register",username,pwd,name,mail,tbackground,Learninghours,age,level,certification,sex,year,month,day,country,native_language,learningyears,o_language,education,learingmethod);
                }

            }
        });
    }



    private boolean MailCheck(String mail){
        if (mail.contains("@")){
            return true;
        }else {
            return false;
        }
    }

    private void initSignUpActivity(){
        teacher_other=findViewById(R.id.teacher_other);
        method_other=findViewById(R.id.method_other);
        certificate_other=findViewById(R.id.certificate_other);
        sp_sex= (Spinner)findViewById(R.id.gender);
        sp_years = (Spinner)findViewById(R.id.years);
        sp_month = (Spinner)findViewById(R.id.month);
        sp_day = (Spinner)findViewById(R.id.day);
        sp_learing_method = (Spinner)findViewById(R.id.LearningMethod);
        average=findViewById(R.id.average);
        teacher_background=findViewById(R.id.teacher_back);
        language_level=findViewById(R.id.language_level);
        certificate=findViewById(R.id.certificate);
        ArrayAdapter<CharSequence> averageList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.average,
                android.R.layout.simple_spinner_dropdown_item);
        average.setAdapter(averageList);
        ArrayAdapter<CharSequence> tbList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.background,
                android.R.layout.simple_spinner_dropdown_item);
        teacher_background.setAdapter(tbList);
        ArrayAdapter<CharSequence> llList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.language_level,
                android.R.layout.simple_spinner_dropdown_item);
        language_level.setAdapter(llList);
        ArrayAdapter<CharSequence> ctList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.certificate,
                android.R.layout.simple_spinner_dropdown_item);
        certificate.setAdapter(ctList);
        ArrayAdapter<CharSequence> SexList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.sex,
                android.R.layout.simple_spinner_dropdown_item);
        sp_sex.setAdapter(SexList);
        ArrayAdapter<CharSequence> YearList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.years,
                android.R.layout.simple_spinner_dropdown_item);
        sp_years.setAdapter(YearList);
        ArrayAdapter<CharSequence> MonthList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.month,
                android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(MonthList);
        ArrayAdapter<CharSequence> DayList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.day,
                android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(DayList);
        ArrayAdapter<CharSequence> learingList = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.learing,
                android.R.layout.simple_spinner_dropdown_item);
        sp_learing_method.setAdapter(learingList);

        teacher_background.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position==3){
                    teacher_other.setVisibility(View.VISIBLE);
                }else
                    teacher_other.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        certificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position==3){
                    certificate_other.setVisibility(View.VISIBLE);
                }else
                    certificate_other.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        sp_learing_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position==3){
                    method_other.setVisibility(View.VISIBLE);
                }else
                    method_other.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(SignUpActivity.this)
                .title(title)
                .customView(R.layout.alert_dialog, wrapInScrollView)
                .backgroundColorRes(R.color.colorBackground)
                .neutralText("SKIP")
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://goo.gl/forms/45rID7ZAVesERpzz2"));
                        startActivity(intent);
                    }
                })
                .build();
        final View item = dialog.getCustomView();

        TextView content_txt=item.findViewById(R.id.dialog_content);
        content_txt.setText(content);
        dialog.show();
    }

    private String getDateNow(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        return formatter.format(curDate);
    }
    private String date_count(String origin ,String now){
        Log.d(TAG, "date_count: origin:"+origin);
        Log.d(TAG, "date_count: now:"+now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 =sdf.parse(origin);
            Date dt2 =sdf.parse(now);
            Long ut1=dt1.getTime();
            Long ut2=dt2.getTime();
            Long timeP=ut2-ut1;
            Long year=((((((timeP/1000)/60)/60)/24)/30)/12);
            Log.d(TAG, "date_count: "+year);

            return year.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }
}




