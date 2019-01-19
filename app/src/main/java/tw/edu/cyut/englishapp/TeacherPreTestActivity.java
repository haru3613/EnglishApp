package tw.edu.cyut.englishapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import tw.edu.cyut.englishapp.Group.TestPreTestActivity;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class TeacherPreTestActivity extends AppCompatActivity {
    private ImageButton play,ans1,ans2,ans3,ans4,next;
    private String choice_ans,file_name,uid,day;
    private TextView count,middle_tips;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private String[] test_audio={"ba1dT","chang2dT","deng3dT","he4dT","ta1dT"};
    private int play_count;
    private boolean initialStage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pre_test);


        initTestPreTestActivity();

        middle_tips.setText("Please test your headset by listening to the recording.");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        play_count=0;
        Intent intent = this.getIntent();//取得傳遞過來的資料
        Bundle bundle =intent.getExtras();

        //接收array
        if (bundle!=null)
            test_audio=intent.getStringArrayExtra("test_audio");
        Log.d(TAG, "onCreate: "+test_audio);

        String index = intent.getStringExtra("index");
        if (index==null){
            index="0";
        }
        Log.d(TAG, "onCreate>>>>>>>>>>>>>>>>>>>>>>: "+index);
        file_name=test_audio[Integer.parseInt(index)];

        Log.d(TAG, "onCreate: 音檔名稱:"+file_name);


        final String c=file_name.substring(file_name.length()-3,file_name.length()-2);
        int i=file_name.indexOf(c);
        Log.d("TAG","題目名稱:"+file_name.substring(0,i)+"答案:"+c);

        count.setText(Integer.parseInt(index)+1+"/5");
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
                        new TeacherPreTestActivity.Player().execute("http://140.122.63.99/topic_audio/test_audio/"+file_name+".wav");
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
        String finalIndex = index;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choice_ans.equals("")){
                    //開啟自己並讓index+1
                    OpenSelf(String.valueOf(Integer.parseInt(finalIndex)+1));
                }else{
                    Toast.makeText(TeacherPreTestActivity.this,"Choose your answer",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initTestPreTestActivity(){
        play=findViewById(R.id.play);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        next=findViewById(R.id.next);
        count=findViewById(R.id.text_count);
        middle_tips=findViewById(R.id.middle_tips);
    }

    private void OpenSelf(String index){
        Log.d("index>>>>>>>>>>>>>>>>",index);
        if (Integer.parseInt(index)==test_audio.length){
            Toast.makeText(this,"Start to check!",Toast.LENGTH_LONG).show();
            Intent ToCheck=new Intent(TeacherPreTestActivity.this,test_examiner_activity.class);
            startActivity(ToCheck);
            finish();


        }else{
            Intent ToSelf=new Intent(TeacherPreTestActivity.this,TeacherPreTestActivity.class);
            ToSelf.putExtra("index",index);
            Bundle mBundle = new Bundle();
            String[] test_audio={"ba1dT","chang2dT","deng3dT","he4dT","ta1dT"};
            mBundle.putStringArray("test_audio", test_audio);
            ToSelf.putExtras(mBundle);
            startActivity(ToSelf);
            finish();
        }
    }


    private void AlertDialog(int draw){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(TeacherPreTestActivity.this)
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