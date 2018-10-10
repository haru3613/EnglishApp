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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class SignUpActivity extends AppCompatActivity {

    String username, mail, pwd, name, re_pwd,country,tbackground,Learninghours,testseries,testname,certification,sex,year,month,day,native_language,learningyears,o_language,education,learingmethod;
    Spinner sp_sex,sp_years,sp_month,sp_day,sp_learing_method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initSignUpActivity();

        final Button SignUp = (Button) findViewById(R.id.sign);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pwd = ((EditText) findViewById(R.id.password)).getText().toString();
                re_pwd = ((EditText) findViewById(R.id.re_password)).getText().toString();
                name = ((EditText) findViewById(R.id.name)).getText().toString();
                mail = ((EditText) findViewById(R.id.mailText)).getText().toString();
                username=((EditText) findViewById(R.id.username)).getText().toString();
                tbackground=((EditText) findViewById(R.id.background)).getText().toString();
                Learninghours=((EditText) findViewById(R.id.Learninghours)).getText().toString();
                testseries=((EditText) findViewById(R.id.testseries)).getText().toString();
                testname=((EditText) findViewById(R.id.testname)).getText().toString();
                certification=((EditText) findViewById(R.id.certification)).getText().toString();
                country=((EditText) findViewById(R.id.country)).getText().toString();
                native_language=((EditText) findViewById(R.id.native_language)).getText().toString();
                learningyears=((EditText) findViewById(R.id.learing_years)).getText().toString();
                o_language=((EditText) findViewById(R.id.other_language)).getText().toString();
                education=((EditText) findViewById(R.id.education)).getText().toString();
                year=sp_years.getSelectedItem().toString();
                sex=sp_sex.getSelectedItem().toString();
                month=sp_month.getSelectedItem().toString();
                day=sp_day.getSelectedItem().toString();
                learingmethod=sp_learing_method.getSelectedItem().toString();

                if ( name.equals("")|| pwd.equals("")|| re_pwd.equals("")|| username.equals("")|| tbackground.equals("")|| testseries.equals("")||
                        sex.equals("")||country.equals("")||year.equals("")||month.equals("")||day.equals("")||certification.equals("")|| testname.equals("")||
                        Learninghours.equals("")|| mail.equals("") || learingmethod.equals("")||native_language.equals("")||o_language.equals("")||learningyears.equals("")||
                        education.equals("")|| !pwd.equals(re_pwd) ||username.contains("'")||pwd.contains("'")||pwd.length()<8 || !MailCheck(mail)) {
                     Toast.makeText(SignUpActivity.this,"There were problems creating your account.",Toast.LENGTH_SHORT).show();

                } else{
                    Backgorundwork backgorundwork = new Backgorundwork(SignUpActivity.this);
                    backgorundwork.execute("register",username,pwd,name,mail,tbackground,Learninghours,testseries,testname,certification,sex,year,month,day,country,native_language,learningyears,o_language,education,learingmethod);
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
        sp_sex= (Spinner)findViewById(R.id.gender);
        sp_years = (Spinner)findViewById(R.id.years);
        sp_month = (Spinner)findViewById(R.id.month);
        sp_day = (Spinner)findViewById(R.id.day);
        sp_learing_method = (Spinner)findViewById(R.id.LearningMethod);

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
    }
}




