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

    String mail, pwd, identity, sex, name, nickname, phone, address, re_pwd;


    private ImageView image;
    boolean imgboo;

    // number of images to select
    String u_image, u_verify, u_verityCode;
    Uri picUri;
    String path;
    private static final int REQUEST_EXTERNAL_STORAGE = 200;
    private static final int PICKER = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        final Button SignUp = (Button) findViewById(R.id.sign);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpp(view);
            }
        });
    }


    //註冊按鈕
    public void signUpp(View v) {
        phone = ((EditText) findViewById(R.id.phone)).getText().toString();
        mail = ((EditText) findViewById(R.id.mailText)).getText().toString();
        identity = ((EditText) findViewById(R.id.pid)).getText().toString();

            if (phone.matches("[0][9][0-9]{8}")) {
                if (mail.length() > 0) {
                    if ((mail.substring(0, 1)).equals("s")) {
                        signup();
                    } else {
                        alertDialog(getResources().getString(R.string.email_error_title), getResources().getString(R.string.email_error), "OK");
                    }
                } else {
                    alertDialog(getResources().getString(R.string.email_error_title), getResources().getString(R.string.email_error2), "OK");
                }
            } else {
                alertDialog(getResources().getString(R.string.phone_error_title), getResources().getString(R.string.phone_error), "ok");
            }
        }


    //呼叫註冊事件
    public void signup() {

        pwd = ((EditText) findViewById(R.id.password)).getText().toString();
        re_pwd = ((EditText) findViewById(R.id.re_password)).getText().toString();
        nickname = ((EditText) findViewById(R.id.nickname)).getText().toString();
        name = ((EditText) findViewById(R.id.name)).getText().toString();
        address = ((EditText) findViewById(R.id.address)).getText().toString();
        phone = ((EditText) findViewById(R.id.phone)).getText().toString();
        identity = ((EditText) findViewById(R.id.pid)).getText().toString();
        Log.d("pwd", pwd);
        Log.d("repwd", re_pwd);


        if (name.equals("") || nickname.equals("") || address.equals("")) {
            alertDialog("欄位有空值", "請勿輸入空值", "OK");
        } else if (mail.length() == 0) {
            alertDialog(getResources().getString(R.string.email_error_title), getResources().getString(R.string.email_error2), "OK");
        } else if (nickname.length() > 11) {
            alertDialog("暱稱過長", "你設定的暱稱太長", "OK");
        } else if (pwd.length() < 5) {
            alertDialog(getResources().getString(R.string.pwd_short_title), getResources().getString(R.string.pwd_short), "OK");
        } else if (!pwd.equals(re_pwd)) {
            alertDialog(getResources().getString(R.string.re_error_title), getResources().getString(R.string.re_error), "OK");
        } else{




        }

    }



    //呼叫回傳值為null的AlertDialog
    public void alertDialog(String T, String M, String P) {
        new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(T)
                .setMessage(M)
                .setPositiveButton(P, null)
                .show();
    }


}




