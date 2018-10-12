package tw.edu.cyut.englishapp.Group;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.AnswerActivity;
import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.PreTestActivity;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemAccount;
import tw.edu.cyut.englishapp.model.ItemTestSpeak;
import tw.edu.cyut.englishapp.model.ItemTopic;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class TestGroupActivity extends Activity {
    private String day,index,level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);

        Button btn_start=findViewById(R.id.btn_start);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        final String uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        level=sharedPreferences.getString("level",null);
        Log.d(TAG, "onCreate: "+uid+","+day+","+level);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!level.equals("Teacher")){
                    if (day.equals("0")){
                        //insert to topic speak
                        String type = "BuildTestData";
                        Backgorundwork backgorundwork = new Backgorundwork(TestGroupActivity.this);
                        backgorundwork.execute(type,uid);
                        //pre-test
                        Intent ToPreTest=new Intent(TestGroupActivity.this,PreTestActivity.class);
                        startActivity(ToPreTest);
                        finish();
                    }else{
                        //get index
                        //start ans questions
                        LoadTopicSpeak(uid);
                    }
                }
            }
        });



    }

    private void OpenAnswerActivity(String t_index){
        Intent ToAnswer=new Intent(TestGroupActivity.this,AnswerActivity.class);
        ToAnswer.putExtra("index", t_index);
        ToAnswer.putExtra("day", day);
        startActivity(ToAnswer);
        finish();
    }



    public void LoadTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/load_topic_speak.php";
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
                            List<ItemTopicSpeak> posts = new ArrayList<ItemTopicSpeak>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemTopicSpeak[].class));
                            index=posts.get(0).getTopic_index();
                            OpenAnswerActivity(index);
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
                params.put("uid",uid);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(TestGroupActivity.this);
        requestQueue.add(stringRequest);
    }



}
