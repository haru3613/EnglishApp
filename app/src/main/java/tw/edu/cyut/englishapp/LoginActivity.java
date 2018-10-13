package tw.edu.cyut.englishapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tw.edu.cyut.englishapp.Group.TestGroupActivity;
import tw.edu.cyut.englishapp.model.ItemAccount;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {
    private  Boolean isExit = false;
    private  Boolean hasTask = false;
    public static final String KEY = "com.cyut.englishapp";
    private Button SignUp,Login;
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


        initLoginActivity();

        AlertDialog("Instructions","You are about to start the Chinese tones training section.\n" +
                "Please make sure your earphones are functioning properly and carefully follow the instructions listed below: \n");


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        }
        );

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToSignUp = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(ToSignUp);
            }
        });



    }
    private void initLoginActivity(){
        SignUp=(Button)findViewById(R.id.SignUp);
        Login=(Button)findViewById(R.id.Login);
    }


    //Login帳號偵錯
    public void Login() {
        email = ((EditText) findViewById(R.id.email)).getText().toString();
        pwd = ((EditText) findViewById(R.id.password)).getText().toString();
        Log.d("AUTH", email + "/" + pwd);
        if (email.equals("")||pwd.equals("")||pwd.length() < 8) {
            Toast.makeText(LoginActivity.this,"Incorrect username or password.",Toast.LENGTH_SHORT).show();
        } else {
            LoadUser(email);
            String type = "login";
            Backgorundwork backgorundwork = new Backgorundwork(this);
            backgorundwork.execute(type,email,pwd);
        }
    }

    public void LoadUser(final String username){
        String url = "http://140.122.63.99/app/load_user_uid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response:",response);
                        try {
                            byte[] u = response.getBytes(
                                    "UTF-8");
                            response = new String(u, "UTF-8");
                            Log.d(ContentValues.TAG, "Response " + response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<ItemAccount> posts = new ArrayList<ItemAccount>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemAccount[].class));
                            String uid=posts.get(0).getUid();
                            String day=posts.get(0).getDay();
                            String level=posts.get(0).getLevel();
                            String qbank=posts.get(0).getQbank();
                            Log.d(TAG, "onResponse uid: "+uid);
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid",uid);
                            editor.putString("day",day);
                            editor.putString("level",level);
                            editor.putString("qbank",qbank);
                            editor.apply();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //do stuffs with response erroe
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(LoginActivity.this)
                .title(title)
                .customView(R.layout.alert_dialog, wrapInScrollView)
                .backgroundColorRes(R.color.colorBackground)
                .positiveText("OK")
                .build();
        final View item = dialog.getCustomView();
        TextView content_txt=item.findViewById(R.id.dialog_content);
        content_txt.setText(content);
        dialog.show();
    }

}
