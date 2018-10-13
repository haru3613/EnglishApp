package tw.edu.cyut.englishapp.Group;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.PreTestActivity;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.ResourceHelper;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;

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
    private Integer count_topic=0,count_record=0;
    private String day,uid,level,index,username,fname;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private boolean playPause;
    private String[][] audio_list=new String[16][138];
    private String topic_url;
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
        setContentView(R.layout.activity_group_control);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        level=sharedPreferences.getString("level",null);

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
        if (!level.equals("Teacher")){
            if (day.equals("0")){
                //insert to topic speak
                String type = "BuildTestData";
                Backgorundwork backgorundwork = new Backgorundwork(group_control.this);
                backgorundwork.execute(type,uid);
                //pre-test
                Intent ToPreTest=new Intent(group_control.this,PreTestActivity.class);
                startActivity(ToPreTest);
                finish();
            }
            else{
                //get index
                LoadTopicSpeak(uid);
//                if (Integer.parseInt(index) >Integer.parseInt(day_array[Integer.parseInt(day)][0])){
//                    Toast.makeText(getApplicationContext(), "Today is finish", Toast.LENGTH_LONG).show();
//                    //Todo into the activity  "Today is finish "
//                    Intent ToLogin=new Intent(group_control.this,LoginActivity.class);
//                    startActivity(ToLogin);
//                    finish();
//                }
            }
        }

        //接收array
        int j=1;
        for (TypedArray item : ResourceHelper.getMultiTypedArray(group_control.this, "day")) {
            for (int i=1;i<=Integer.parseInt(item.getString(0));i++){
                audio_list[j][i]=item.getString(i);
                Log.d(TAG, "List: "+j+"-"+i+":"+audio_list[j][i]);
            }
            j++;
        }
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        //

    }

    public void bt_next(View view) {
        String index_add=Integer.toString(Integer.parseInt(index)+1);
        Backgorundwork backgorundwork = new Backgorundwork(this);
        backgorundwork.execute("Upload_record",mFileName,uid,index_add,fname);

    }

    public void bt_recode_playing(View view) {
        startPlaying();
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
            mFileName += "/"+fname+".3gp";
            startRecording();
            Toast.makeText(getApplicationContext(), "Start Recording", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "You can't record more than three times,please click \"GO\".", Toast.LENGTH_LONG).show();
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
        Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_LONG).show();
    }

    public void bt_topic_speak(View view) {
        topic_url="http://140.122.63.99/topic_audio/all_audio/"+audio_list[Integer.parseInt(day)][Integer.parseInt(index)]+".wav";
        count_topic+=1;
        if(count_topic<4){
            if (!playPause) {
                Toast.makeText(getApplicationContext(), "Topic is playing", Toast.LENGTH_SHORT).show();
                if (initialStage) {
                    new Player().execute(topic_url);
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
            Toast.makeText(getApplicationContext(), "You can't play more than three times.", Toast.LENGTH_LONG).show();

        }
    }

    public void LoadTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/load_topic_speak.php";
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
                            Log.e(LOG_TAG, "My index~"+index);
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
        RequestQueue requestQueue = Volley.newRequestQueue(group_control.this);
        requestQueue.add(stringRequest);
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
