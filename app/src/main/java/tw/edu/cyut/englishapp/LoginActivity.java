package tw.edu.cyut.englishapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private static Boolean isExit = false;
    private static Boolean hasTask = false;
    private static final String ACTIVITY_TAG ="Logwrite";
    public static final String KEY = "STATUS";
    String email;
    String pwd;
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
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //判斷使用者是否第一次登入

        SharedPreferences sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE);
        Boolean FirstLogin=sharedPreferences.getBoolean("Status",false);
        Log.d("FirstLogin?",FirstLogin.toString());
        if (FirstLogin){
            Intent toMainActivity=new Intent(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(toMainActivity);
            finish();
        }

        final Button Login=(Button)findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        }
        );

        final Button SignUp=(Button)findViewById(R.id.SignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToSignUp = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(ToSignUp);
            }
        });



    }


    //Login帳號偵錯
    public void Login() {
        email = ((EditText) findViewById(R.id.email)).getText().toString();
        pwd = ((EditText) findViewById(R.id.password)).getText().toString();
        Log.d("AUTH", email + "/" + pwd);
        if (email.equals("")||pwd.equals("")||pwd.length() < 8) {
                String type = "login";
                Backgorundwork backgorundwork = new Backgorundwork(this);
                backgorundwork.execute(type,email,pwd);

            } else {
            Toast.makeText(LoginActivity.this,"Incorrect username or password.",Toast.LENGTH_SHORT).show();
            }
    }





}
