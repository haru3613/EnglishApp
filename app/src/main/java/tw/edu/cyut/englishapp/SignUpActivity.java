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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class SignUpActivity extends AppCompatActivity {

    String username, mail, pwd, name, re_pwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        final Button SignUp = (Button) findViewById(R.id.sign);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pwd = ((EditText) findViewById(R.id.password)).getText().toString();
                re_pwd = ((EditText) findViewById(R.id.re_password)).getText().toString();
                name = ((EditText) findViewById(R.id.name)).getText().toString();
                mail = ((EditText) findViewById(R.id.mailText)).getText().toString();
                username=((EditText) findViewById(R.id.username)).getText().toString();

                if (name.equals("") || name.equals("")|| pwd.equals("")|| re_pwd.equals("")|| username.equals("")|| mail.equals("") || !pwd.equals(re_pwd) ||username.contains("'")||pwd.contains("'")||pwd.length()<8) {
                     Toast.makeText(SignUpActivity.this,"There were problems creating your account.",Toast.LENGTH_SHORT).show();

                } else{
                    Backgorundwork backgorundwork = new Backgorundwork(SignUpActivity.this);
                    backgorundwork.execute("register",username,pwd,name,mail);
                }

            }
        });
    }









}




