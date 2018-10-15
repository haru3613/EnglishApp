package tw.edu.cyut.englishapp.Group;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.ResourceHelper;
import tw.edu.cyut.englishapp.todayisfinish;

public class ControlPreTestActivity extends Activity {
    private ImageView bt_topic_speak,image_background,bt_next,bt_speak_start,bt_stop_speak,bt_speak_talker;
    private TextView text_count;
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
    private String topic_url;
    private String test_account;
    private String[] test_audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_pre_test);



    }


    public void bt_next(View view) {
        File file = new File(mFileName);
        if (file.exists()){
            file.delete();
            if (today_finish.equals("0")){
//                Intent ToControl=new Intent(context,group_control.class);
//                context.startActivity(ToControl);
//                ((Activity) context).finish();
            }else{
//                Intent Totodayisfinish=new Intent(context,todayisfinish.class);
//                context.startActivity(Totodayisfinish);
//                ((Activity) context).finish();

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

    public void bt_topic_speak(View view) {
        topic_url="http://140.122.63.99/topic_audio/test_audio/"+audio_list[Integer.parseInt(get_topic_day)][Integer.parseInt(index)]+".wav";
        count_topic+=1;
        if(count_topic<4){
            if (!playPause) {
                Toast.makeText(getApplicationContext(), "Topic is playing", Toast.LENGTH_SHORT).show();
                if (initialStage) {
                    new ControlPreTestActivity.Player().execute(topic_url);
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

    public void bt_recode_playing(View view) {

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


    private void OpenSelf(String index){
        if (Integer.parseInt(index)==test_audio.length){

            //xml to array
            int j=0;
            for (TypedArray item : ResourceHelper.getMultiTypedArray(ControlPreTestActivity.this, "day")) {
                for (int i=0;i<=Integer.parseInt(item.getString(0));i++){
                    audio_list[j][i]=item.getString(i);
                }
                j++;
            }
            String type = "finish pretest";
            Backgorundwork backgorundwork = new Backgorundwork(ControlPreTestActivity.this);
            backgorundwork.execute(type,uid,"1");

        }else{
            Intent ToSelf=new Intent(ControlPreTestActivity.this,ControlPreTestActivity.class);
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
        MaterialDialog dialog=new MaterialDialog.Builder(ControlPreTestActivity.this)
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
