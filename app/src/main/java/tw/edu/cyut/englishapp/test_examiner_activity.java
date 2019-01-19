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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.Group.ControlPreTestActivity;
import tw.edu.cyut.englishapp.Group.TestPreTestActivity;
import tw.edu.cyut.englishapp.Group.controldetail;
import tw.edu.cyut.englishapp.Group.group_control;
import tw.edu.cyut.englishapp.model.ItemCheckTopic;
import tw.edu.cyut.englishapp.model.ItemTopicLengh;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class test_examiner_activity extends AppCompatActivity {
    private String uid,aid,day,filename,correct_ans,topic_url,ans,test_audio,count;
    private Integer count_topic=0;
    private ProgressDialog progressDialog;
    private boolean playPause;
    private boolean initialStage = true;
    private MediaPlayer   mPlayer = null;
    private TextView text_count;
    private ImageView play,ans0,ans1,ans2,ans3,ans4,ans9,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_examiner_activity);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        LoadTopicSpeak(uid);
        Topic_lengh(uid);
        text_count=findViewById(R.id.text_count);
        next=findViewById(R.id.next);
        play=findViewById(R.id.play);
        ans0=findViewById(R.id.ans0);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        ans9=findViewById(R.id.ans9);
        ans0.setVisibility(View.VISIBLE);
        ans1.setVisibility(View.VISIBLE);
        ans2.setVisibility(View.VISIBLE);
        ans3.setVisibility(View.VISIBLE);
        ans4.setVisibility(View.VISIBLE);
        ans9.setVisibility(View.VISIBLE);

    }




    public void bt_ans_one(View view) {
        ans="1";
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        ans9.setVisibility(View.INVISIBLE);
        ans0.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_two(View view) {
        ans="2";
        ans1.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        ans9.setVisibility(View.INVISIBLE);
        ans0.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_three(View view) {
        ans="3";
        ans2.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        ans9.setVisibility(View.INVISIBLE);
        ans0.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_four(View view) {
        ans="4";
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);
        ans9.setVisibility(View.INVISIBLE);
        ans0.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_zero(View view) {
        ans="0";
        ans4.setVisibility(View.INVISIBLE);
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);
        ans9.setVisibility(View.INVISIBLE);

    }

    public void bt_ans_nine(View view) {
        ans="9";
        ans0.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);


    }

    public void bt_next(View view) {
        if (!ans.equals("")){
            Backgorundwork backgorundwork = new Backgorundwork(this);
            backgorundwork.execute("Insert_audio_topic_check",aid,uid,filename,ans);
            Intent intent = new Intent();
            intent.setClass(test_examiner_activity.this, test_examiner_activity.class);
            startActivity(intent);
            test_examiner_activity.this.finish();
        }else{
            Toast.makeText(test_examiner_activity.this,"Choose your answer",Toast.LENGTH_SHORT).show();

        }

    }


    private void AlertDialog(int draw){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(test_examiner_activity.this)
                .customView(R.layout.alert_gif, wrapInScrollView)
                .backgroundColorRes(R.color.colorBackground)
                .positiveText("OK")
                .build();
        final View item = dialog.getCustomView();
        ImageView gif=item.findViewById(R.id.image_gif);

        Glide.with(item)
                .asGif()
                .load(draw)
                .into(gif);
        dialog.show();
    }

    public void bt_topic_speak(View view) {
        topic_url="http://140.122.63.99/record_mp3/uploads/"+test_audio+".mp3";
        count_topic+=1;
        Log.d("topic_url",topic_url);
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


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(strings[0]);
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

            mPlayer.start();
            initialStage = false;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    public void LoadTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/exam_check_android.php";
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
                            List<ItemCheckTopic> posts = new ArrayList<ItemCheckTopic>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemCheckTopic[].class));
                            aid=posts.get(0).getAid();
                            filename=posts.get(0).getFilename();
                            correct_ans=posts.get(0).getCorrect_ans();
                            test_audio=filename;
                            Log.d("QQQAAAAS>>>>>",aid+"YA^^");

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


    public void Topic_lengh(final String uid){
        String url = "http://140.122.63.99/app/get_check_lengh.php";
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
                            List<ItemTopicLengh> posts = new ArrayList<ItemTopicLengh>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemTopicLengh[].class));
                            count=posts.get(0).getCount();
                            if (Integer.parseInt(count)==0){
                                Toast.makeText(test_examiner_activity.this,"Thank you very much! You finish all the check!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setClass(test_examiner_activity.this, test_examinerGroup.class);
                                startActivity(intent);
                                test_examiner_activity.this.finish();

                            }
                            Log.d("ZZZZZZZZZ>>>>>",count+"YA^^");
                            text_count.setText(count);

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


}
