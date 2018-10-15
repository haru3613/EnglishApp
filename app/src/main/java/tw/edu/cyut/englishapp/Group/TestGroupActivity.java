package tw.edu.cyut.englishapp.Group;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import tw.edu.cyut.englishapp.PreExamActivity;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.ResourceHelper;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class TestGroupActivity extends Activity {
    private String day,index,level,qbank;

    private String [][] audio_list=new String[16][139];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);

        Button btn_start=findViewById(R.id.btn_start);

        int j=0;
        for (TypedArray item : ResourceHelper.getMultiTypedArray(TestGroupActivity.this, "day")) {
            for (int i=0;i<=Integer.parseInt(item.getString(0));i++){
                audio_list[j][i]=item.getString(i);
            }
            j++;
        }


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        final String uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        level=sharedPreferences.getString("level",null);
        Log.d(TAG, "onCreate: "+uid+","+day+","+level);

        //第幾個題庫
        qbank=sharedPreferences.getString("qbank",null);


        InsertTopicSpeak(uid);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!level.equals("Teacher")){
                    if (day.equals("0")){
                        //insert to topic speak
                        //pre-exam
                        Intent ToPreExam=new Intent(TestGroupActivity.this,PreExamActivity.class);
                        startActivity(ToPreExam);
                        finish();
                    }else{
                        if (Integer.parseInt(index)==0){
                            OpenTestPreTest();
                        }else if (Integer.parseInt(index)<=Integer.parseInt(audio_list[Integer.parseInt(qbank)][0])){
                            OpenAnswerActivity(index);
                        }
                    }

                }
            }
        });



    }
    private void OpenAnswerActivity(String t_index){
        Intent ToAnswer=new Intent(TestGroupActivity.this,AnswerActivity.class);
        Bundle mBundle = new Bundle();
        ToAnswer.putExtra("index", t_index);
        ToAnswer.putExtra("day", day);
        ToAnswer.putExtra("qbank", qbank);
        mBundle.putSerializable("audio_list", audio_list);
        ToAnswer.putExtras(mBundle);
        startActivity(ToAnswer);
        finish();
    }
    private void OpenTestPreTest(){
        Intent ToTest=new Intent(TestGroupActivity.this,TestPreTestActivity.class);
        Bundle mBundle = new Bundle();
        ToTest.putExtra("index",0);
        String[] test_audio={"ba1dT","chang2dT","deng3dT","he4dT","ta1dT"};
        mBundle.putStringArray("test_audio", test_audio);
        ToTest.putExtras(mBundle);
        startActivity(ToTest);
        finish();
    }


    public void InsertTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/buildtestdata.php";
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

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //do stuffs with response erro
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
