package tw.edu.cyut.englishapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import tw.edu.cyut.englishapp.Group.controldetail;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class PreExam_test extends AppCompatActivity {
    private ImageView bt_topic_speak,image_background,bt_next,bt_speak_start,bt_stop_speak,bt_speak_talker;
    private TextView text_count,topic_word;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private Integer count_topic=0,count_record=0;
    private String day,uid,level,index,username,fname,qbank,get_topic_day,today_finish;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private boolean playPause;
    private String[][] audio_list=new String[16][139];
    private String topic_url,test_index;
    private String test_account;
    private String[] test_audio={"deng3dT","ta1dT","he4dT","ba1dT","chang2dT"};
    private String[] test_audio_words={"deng3","ta1","he4","ba1","chang2"};
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
        bt_next.setVisibility(View.INVISIBLE);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_speak_talker.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preexam_test);
        Bundle bundle = getIntent().getExtras();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        day=sharedPreferences.getString("day",null);
        uid=sharedPreferences.getString("uid",null);
        test_index = bundle.getString("index");
        bt_speak_talker = findViewById(R.id.bt_speak_talker);
        bt_speak_start = findViewById(R.id.bt_speak_start);
        topic_word=findViewById(R.id.topic_word);
        topic_word.setText(test_audio_words[Integer.parseInt(test_index)]);
        bt_stop_speak = findViewById(R.id.bt_stop_speak);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_next = findViewById(R.id.bt_next);
        bt_next.setVisibility(View.INVISIBLE);
        bt_next.setEnabled(false);
        text_count = findViewById(R.id.text_count);
        text_count.setText(Integer.parseInt(test_index)+1 +"/5");
        progressDialog = new ProgressDialog(this);
    }

    public void bt_next(View view) {
        File file = new File(mFileName);
        if (file.exists()){
            file.delete();
            if (test_index.equals("4")){
                Backgorundwork backgorundwork = new Backgorundwork(this);
                backgorundwork.execute("finish pretest_control",uid,"1",day);
                Intent intent = new Intent();
                intent.setClass(PreExam_test.this, controldetail.class);
                startActivity(intent);
                PreExam_test.this.finish();
            }else{
                Intent intent = new Intent();
                intent.setClass(PreExam_test.this, PreExam_test.class);
                Bundle bundle = new Bundle();
                bundle.putString("index",String.valueOf(Integer.parseInt(test_index)+1) );
                intent.putExtras(bundle);
                startActivity(intent);
                PreExam_test.this.finish();

            }

        }else{
            Log.d("Error ", "File doesn't exist");
        }
    }

    public void bt_recode_start(View view) {
        count_record+=1;
        if(count_record<4){
            bt_next.setVisibility(View.INVISIBLE);
            bt_next.setEnabled(false);
            bt_speak_talker.setVisibility(View.INVISIBLE);
            bt_speak_talker.setEnabled(false);
            mFileName = getExternalCacheDir().getAbsolutePath();
            fname=uid+"_d"+day+"_"+index;
            mFileName += "/"+fname+".mp3";
            startRecording();
            Toast.makeText(getApplicationContext(), "Start Recording", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "You can't record more than three times,please click \"GO\".", Toast.LENGTH_SHORT).show();
        }
    }

    public void bt_recode_stop(View view) {
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_speak_start.setVisibility(View.VISIBLE);
        bt_speak_start.setEnabled(true);
        bt_speak_talker.setVisibility(View.VISIBLE);
        bt_speak_talker.setEnabled(true);
        bt_next.setVisibility(View.VISIBLE);
        bt_next.setEnabled(true);
        stopRecording();
        Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_SHORT).show();
    }


    public void bt_recode_playing(View view) {
        startPlaying();
    }


    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    private void startRecording() {
        bt_speak_start.setEnabled(false);
        bt_speak_start.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(true);
        bt_stop_speak.setVisibility(View.VISIBLE);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(mFileName);
        Log.e(LOG_TAG, "prepare() failed"+mFileName);


        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
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
