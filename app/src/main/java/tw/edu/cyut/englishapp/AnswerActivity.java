package tw.edu.cyut.englishapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    private ImageView background;
    private TextView count,test_day;
    private String qbank,uid, choice_ans,day,file_name;
    private  Boolean isExit = false;
    private  Boolean hasTask = false;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private String[][] audio_list=new String[16][105];
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
        qbank = intent.getStringExtra("qbank");
        Log.d("onCreate","這是第幾個題庫:"+qbank);
        Log.d("onCreate","這是第幾題:"+t_index);

        if (Integer.parseInt(t_index)<=34){
            ans1.setVisibility(View.GONE);
            ans4.setVisibility(View.GONE);
            play.setImageResource(R.drawable.app_y_speaker);
            background.setImageResource(R.drawable.app_y_interface);
            next.setImageResource(R.drawable.app_y_go);
            ans1.setImageResource(R.drawable.app_y_num1);
            ans2.setImageResource(R.drawable.app_y_num2);
            ans3.setImageResource(R.drawable.app_y_num3);
            ans4.setImageResource(R.drawable.app_y_num4);
        }else if (Integer.parseInt(t_index)<=64) {
            ans2.setVisibility(View.GONE);
            ans3.setVisibility(View.GONE);
            play.setImageResource(R.drawable.app_r_speaker);
            background.setImageResource(R.drawable.app_r_interface);
            next.setImageResource(R.drawable.app_r_go);
            ans1.setImageResource(R.drawable.app_r_num1);
            ans2.setImageResource(R.drawable.app_r_num2);
            ans3.setImageResource(R.drawable.app_r_num3);
            ans4.setImageResource(R.drawable.app_r_num4);
        }else{
            play.setImageResource(R.drawable.app_g_speaker);
            background.setImageResource(R.drawable.app_g_interface);
            next.setImageResource(R.drawable.app_g_go);
            ans1.setImageResource(R.drawable.app_g_num1);
            ans2.setImageResource(R.drawable.app_g_num2);
            ans3.setImageResource(R.drawable.app_g_num3);
            ans4.setImageResource(R.drawable.app_g_num4);
        }


        day = intent.getStringExtra("day");

        test_day.setText("Day"+day);

        //接收array
        audio_list=null;
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("audio_list");
        if(objectArray!=null){
            audio_list = new String[objectArray.length][];
            Log.d(TAG, "onCreate: length"+objectArray.length);
            for(int i=0;i<objectArray.length;i++){
                audio_list[i]=(String[]) objectArray[i];
            }
        }

        file_name=audio_list[Integer.parseInt(qbank)][Integer.parseInt(t_index)];
        Log.d(TAG, "onCreate: 音檔名稱:"+file_name);


        final String c=file_name.substring(file_name.length()-3,file_name.length()-2);
        int index=file_name.indexOf(c);
        Log.d("TAG","題目名稱:"+file_name.substring(0,index)+"答案:"+c);

        count.setText(t_index+"/"+audio_list[Integer.parseInt(qbank)][0]);
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
                if (c.equals(choice_ans)){
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
                if (c.equals(choice_ans)){
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
                if (c.equals(choice_ans)){
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
                if (c.equals(choice_ans)){
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
                        new Player().execute("http://140.122.63.99/topic_audio/all_audio/"+audio_list[Integer.parseInt(qbank)][Integer.parseInt(t_index)]+".wav");
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
                    String type = "Delete Topic Speak";
                    Backgorundwork backgorundwork = new Backgorundwork(AnswerActivity.this);
                    backgorundwork.execute(type,uid,day);
                    finish();
                }else{
                    if (!choice_ans.equals("")){
                        //新增test資料表的答案，並update topic_speak的index
                        String type = "InsertAns";
                        Backgorundwork backgorundwork = new Backgorundwork(AnswerActivity.this);
                        Log.d(TAG, "onClick: "+day+":"+choice_ans);
                        backgorundwork.execute(type,uid,t_index,choice_ans,c,day);
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
        if (Integer.parseInt(t_index)>Integer.parseInt(audio_list[Integer.parseInt(qbank)][0])){
            //如果大於總數
            String type = "Update user day";
            Backgorundwork backgorundwork = new Backgorundwork(AnswerActivity.this);
            backgorundwork.execute(type,uid,String.valueOf(Integer.parseInt(day)+1));
            Intent ToFinish=new Intent(AnswerActivity.this,todayisfinish.class);
            startActivity(ToFinish);
            finish();
        }else{
            Intent ToAnswer=new Intent(AnswerActivity.this,AnswerActivity.class);
            ToAnswer.putExtra("index", t_index);
            ToAnswer.putExtra("day", day);
            ToAnswer.putExtra("qbank", qbank);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("audio_list", audio_list);
            ToAnswer.putExtras(mBundle);
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
        count=findViewById(R.id.text_count);
        background=findViewById(R.id.test_background);
        test_day=findViewById(R.id.test_day);
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
                .asGif()
                .load(draw)
                .into(gif);
        dialog.show();
    }

    private String getDateNow(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        return formatter.format(curDate);
    }


    private boolean date_count(String origin ,String now){
        Log.d(TAG, "date_count: origin:"+origin);
        Log.d(TAG, "date_count: now:"+now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 =sdf.parse(origin);
            Date dt2 =sdf.parse(now);
            Long ut1=dt1.getTime();
            Long ut2=dt2.getTime();
            Long timeP=ut2-ut1;
            Long sec=timeP/1000;
            Log.d(TAG, "date_count: "+sec);
            if (sec>180){
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
