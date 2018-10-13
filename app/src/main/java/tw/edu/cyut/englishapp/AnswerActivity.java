package tw.edu.cyut.englishapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tw.edu.cyut.englishapp.Group.TestGroupActivity;
import tw.edu.cyut.englishapp.model.ItemAccount;
import tw.edu.cyut.englishapp.model.ItemTopic;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class AnswerActivity extends Activity {

    private ImageButton play,ans1,ans2,ans3,ans4,next;
    private String correct_ans,uid, choice_ans,day,file_name;
    private  Boolean isExit = false;
    private  Boolean hasTask = false;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private String[] audio_list;
    private int play_count;
    private boolean initialStage = true;
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
        setContentView(R.layout.activity_answer);

        initAnswerActivity();

        final String origin_time=getDateNow();
        Log.d("onCreate", "TimeNow:"+origin_time);

        play_count=0;
        uid="";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        Log.d("onCreate", "uid: "+uid);

        Intent intent = this.getIntent();//取得傳遞過來的資料
        final String t_index = intent.getStringExtra("index");

        Log.d("onCreate","這是第幾題:"+t_index);

        if (Integer.parseInt(t_index)<=34){
            ans1.setVisibility(View.GONE);
            ans4.setVisibility(View.GONE);
        }else if (Integer.parseInt(t_index)<=64) {
            ans2.setVisibility(View.GONE);
            ans3.setVisibility(View.GONE);
        }

        day = intent.getStringExtra("day");

        //Load exam data
        LoadExamData(t_index);

        //TODO 從xml檔撈陣列給audio_list
        //TODO 從陣列抓取音檔
        //file_name=audio_list[Integer.parseInt(t_index)];
        Log.d(TAG, "onCreate file_name:"+file_name);

        choice_ans="";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);



        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="1";
                ans2.setVisibility(View.INVISIBLE);
                ans3.setVisibility(View.INVISIBLE);
                ans4.setVisibility(View.INVISIBLE);
                if (correct_ans.equals(choice_ans)){
                    //open good gif
                    AlertDialog(R.drawable.applaud);
                }else {
                    //open bad gif
                    AlertDialog(R.drawable.shaking_head);
                }
            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="2";
                ans1.setVisibility(View.INVISIBLE);
                ans3.setVisibility(View.INVISIBLE);
                ans4.setVisibility(View.INVISIBLE);
                if (correct_ans.equals(choice_ans)){
                    //open good gif
                    AlertDialog(R.drawable.applaud);
                }else {
                    //open bad gif
                    AlertDialog(R.drawable.shaking_head);
                }
            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="3";
                ans1.setVisibility(View.INVISIBLE);
                ans2.setVisibility(View.INVISIBLE);
                ans4.setVisibility(View.INVISIBLE);
                if (correct_ans.equals(choice_ans)){
                    //open good gif
                    AlertDialog(R.drawable.applaud);
                }else {
                    //open bad gif
                    AlertDialog(R.drawable.shaking_head);
                }
            }
        });
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="4";
                ans2.setVisibility(View.INVISIBLE);
                ans3.setVisibility(View.INVISIBLE);
                ans1.setVisibility(View.INVISIBLE);
                if (correct_ans.equals(choice_ans)){
                    //open good gif
                    AlertDialog(R.drawable.applaud);
                }else {
                    //open bad gif
                    AlertDialog(R.drawable.shaking_head);
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause && play_count!=3) {
                    play_count+=1;

                    if (initialStage) {
                        new Player().execute("http://140.122.63.99/app/"+file_name);
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }

                    playPause = true;

                } else {
                    //btn.setText("Launch Streaming");

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }

                    playPause = false;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date_count(origin_time,getDateNow())){
                    //如果等於true則大於三分鐘
                    finish();
                }else{
                    if (!choice_ans.equals("")){
                        //新增test資料表的答案，並update topic_speak的index
                        String type = "InsertAns";
                        Backgorundwork backgorundwork = new Backgorundwork(AnswerActivity.this);
                        backgorundwork.execute(type,uid,t_index,choice_ans);
                        //開啟自己並讓index+1
                        OpenAnswerActivity(String.valueOf(Integer.parseInt(t_index)+1));
                    }else{
                        Toast.makeText(AnswerActivity.this,"Choose your answer",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private void OpenAnswerActivity(String t_index){
        if (Integer.parseInt(t_index)==audio_list.length){
            //如果等於總數
            String type = "Update user day";
            Backgorundwork backgorundwork = new Backgorundwork(AnswerActivity.this);
            backgorundwork.execute(type,uid,String.valueOf(Integer.parseInt(day)+1));
            finish();
        }else{
            Intent ToAnswer=new Intent(AnswerActivity.this,AnswerActivity.class);
            ToAnswer.putExtra("index", t_index);
            startActivity(ToAnswer);
            finish();
        }
    }

    private void initAnswerActivity(){
        play=findViewById(R.id.play);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        next=findViewById(R.id.next);

    }

    public void LoadExamData(final String index){
        String url = "http://140.122.63.99/app/load_topic_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            byte[] u = response.getBytes(
                                    "UTF-8");
                            response = new String(u, "UTF-8");
                            Log.d(ContentValues.TAG, "Response " + response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<ItemTopic> posts = new ArrayList<ItemTopic>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemTopic[].class));
                            correct_ans=posts.get(0).getAns();

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
                params.put("tid",index);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AnswerActivity.this);
        requestQueue.add(stringRequest);
    }

    private void AlertDialog(int draw){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(AnswerActivity.this)
                .customView(R.layout.alert_gif, wrapInScrollView)
                .backgroundColorRes(R.color.colorBackground)
                .positiveText("OK")
                .build();
        final View item = dialog.getCustomView();
        ImageView gif=item.findViewById(R.id.image_gif);
        Glide.with(item)
                .load(ContextCompat.getDrawable(item.getContext(),draw ))
                .into(gif);
        dialog.show();
    }

    private String getDateNow(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        return formatter.format(curDate);
    }


    private boolean date_count(String origin ,String now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date dt1 =sdf.parse(origin);
            Date dt2 =sdf.parse(now);
            Long ut1=dt1.getTime();
            Long ut2=dt2.getTime();
            Long timeP=ut2-ut1;
            Long min=timeP/1000*60;
            if (min>3){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        //btn.setText("Launch Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
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

            mediaPlayer.start();
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
