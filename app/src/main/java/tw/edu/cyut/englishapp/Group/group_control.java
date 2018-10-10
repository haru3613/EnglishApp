package tw.edu.cyut.englishapp.Group;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.R;

public class group_control extends AppCompatActivity  {
    private ImageView bt_topic_speak,image_background,bt_next,bt_speak_start,bt_stop_speak,bt_speak_talker;
    private TextView text_count;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("Username");
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
        setContentView(R.layout.activity_group_control);
        image_background = findViewById(R.id.image_background);
        bt_topic_speak = findViewById(R.id.bt_topic_speak);
        bt_speak_talker = findViewById(R.id.bt_speak_talker);
        bt_speak_start = findViewById(R.id.bt_speak_start);
        bt_stop_speak = findViewById(R.id.bt_stop_speak);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_next = findViewById(R.id.bt_next);
        bt_next.setVisibility(View.INVISIBLE);
        bt_next.setEnabled(false);
        text_count = findViewById(R.id.text_count);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

    }

    public void bt_next(View view) {
        Backgorundwork backgorundwork = new Backgorundwork(this);
        backgorundwork.execute("Upload_record",mFileName);
    }

    public void bt_recode_playing(View view) {
        startPlaying();
        bt_speak_talker.setVisibility(View.INVISIBLE);
    }

    public void bt_recode_start(View view) {
        bt_speak_talker.setVisibility(View.INVISIBLE);
        bt_speak_talker.setEnabled(false);
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        startRecording();
        Toast.makeText(getApplicationContext(), "Start Recording", Toast.LENGTH_LONG).show();
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
        Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_LONG).show();
    }

    public void bt_topic_speak(View view) {

    }

    private void fileDel() {
        File file = new File(mFileName);
        if (file.exists()){
            file.delete();
        }else{
            Toast.makeText(getBaseContext(),
                    "File doesn't exist",
                    Toast.LENGTH_SHORT).show();
        }
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
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
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

}
