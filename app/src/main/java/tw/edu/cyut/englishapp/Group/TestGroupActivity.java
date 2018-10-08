package tw.edu.cyut.englishapp.Group;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

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

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemAccount;
import tw.edu.cyut.englishapp.model.ItemTestSpeak;

import static tw.edu.cyut.englishapp.Backgorundwork.KEY;

public class TestGroupActivity extends Activity {
    String time_examing,day,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);
        uid="";
        SharedPreferences sharedPreferences = TestGroupActivity.this.getSharedPreferences(KEY, MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        //check user
        LoadUser(uid);
        Log.d("Debug", "data check:"+time_examing);
        Log.d("Debug", "data check:"+day);
        if (time_examing==null){
            //建立資料
            String type = "BuildTestData";
            Backgorundwork backgorundwork = new Backgorundwork(TestGroupActivity.this);
            backgorundwork.execute(type,uid);
        }else{
            //uid 搜尋資料
            LoadTestData(uid);
            //load test_speak
        }
    }

    public void LoadUser(final String uid){
        String url = "http://140.122.63.99/app/loaduser.php";
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
                            day=posts.get(0).getDay();
                            time_examing=posts.get(0).getTime_examing();
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
    public void LoadTestData(final String uid){
        String url = "http://140.122.63.99/app/load_test_date.php";
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
                            List<ItemTestSpeak> posts = new ArrayList<ItemTestSpeak>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemTestSpeak[].class));
                            //處理陣列
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
