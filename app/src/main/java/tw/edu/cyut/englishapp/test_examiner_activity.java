package tw.edu.cyut.englishapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

import tw.edu.cyut.englishapp.Group.ControlPreTestActivity;
import tw.edu.cyut.englishapp.Group.group_control;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class test_examiner_activity extends AppCompatActivity {
    private String uid,test_speak_filename,aid,topic_url,ans;
    private Integer count_topic;
    private ProgressDialog progressDialog;
    private boolean playPause;
    private boolean initialStage = true;
    private MediaPlayer   mPlayer = null;
    private ImageView play,ans1,ans2,ans3,ans4,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_examiner_activity);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        LoadTestSpeak(uid);
        next=findViewById(R.id.next);
        play=findViewById(R.id.play);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        ans1.setVisibility(View.VISIBLE);
        ans2.setVisibility(View.VISIBLE);
        ans3.setVisibility(View.VISIBLE);
        ans4.setVisibility(View.VISIBLE);
    }

    public void topic_play(View view) {
        topic_url="http://140.122.63.99/record_mp3/uploads/"+test_speak_filename+".mp3";
        count_topic+=1;
        if(count_topic<4){
            if (!playPause) {
                Toast.makeText(getApplicationContext(), "Topic is playing", Toast.LENGTH_SHORT).show();
                if (initialStage) {
                    new test_examiner_activity.Player().execute(topic_url);
                } else {
                    if (!mPlayer.isPlaying())
                        mPlayer.start();
                }

                playPause = true;

            } else {
                Toast.makeText(getApplicationContext(), "Stop playing", Toast.LENGTH_SHORT).show();
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }

                playPause = false;
            }


        }else{
            Toast.makeText(getApplicationContext(), "You can't play more than three times.", Toast.LENGTH_SHORT).show();

        }


    }

    public void bt_ans_one(View view) {
        ans="1";
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);



    }

    public void bt_ans_two(View view) {
        ans="2";
        ans1.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_three(View view) {
        ans="3";
        ans2.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_four(View view) {
        ans="4";
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);

    }

    public void bt_next(View view) {



    }


    public void LoadTestSpeak(final String uid){
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
                            aid=posts.get(0).gettest_speak_aid();
                            test_speak_filename=posts.get(3).gettest_speak_filename();

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
        RequestQueue requestQueue = Volley.newRequestQueue(test_examiner_activity.this);
        requestQueue.add(stringRequest);
    }


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            Log.d("EEEEEEEEEEEEEEEE","setDataSource="+strings[0]);
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(strings[0]);
                Log.d("EEEEEEEEEEEEEEEE","setDataSource="+strings[0]);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }

}
