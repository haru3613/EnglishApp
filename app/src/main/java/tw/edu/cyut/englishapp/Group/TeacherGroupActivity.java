package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

import tw.edu.cyut.englishapp.AnswerActivity;
import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTestSpeak;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class TeacherGroupActivity extends Activity {
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ImageView play_media,btn_next;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_group);

        initTeacherGroupActivity();

        //TODO  評分欄位
        play_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //讀取音檔並播放
                LoadTestSpeak();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //開啟自己
                //TODO 評分
                OpenTeacherGroupActivity();
            }
        });


    }
    private void initTeacherGroupActivity(){
        play_media=findViewById(R.id.media_play);
        btn_next=findViewById(R.id.teacher_next);
    }

    private void OpenTeacherGroupActivity(){
        Intent ToSelf=new Intent(TeacherGroupActivity.this,TeacherGroupActivity.class);
        startActivity(ToSelf);
        finish();
    }

    private void PlayMedia(String filename){
        if (!playPause ){
            if (initialStage) {
                new Player().execute("http://140.122.63.99/topic_audio/all_audio/"+filename+".wav");
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
    public void LoadTestSpeak(){
        RequestQueue queue = Volley.newRequestQueue(TeacherGroupActivity.this);
        String url ="http://140.122.63.99/app/select_test_speak.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    byte[] u = fromHtml(response).toString().getBytes(
                            "UTF-8");
                    response = new String(u, "UTF-8");
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    List<ItemTestSpeak> posts = new ArrayList<ItemTestSpeak>();
                    posts = Arrays.asList(mGson.fromJson(response, ItemTestSpeak[].class));
                    PlayMedia(posts.get(0).getFilename());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    public static Spanned fromHtml(String html) {
        html = (html.replace("&lt;", "<").replace("&gt;", ">"));
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html.trim(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html.trim());
        }
        return result;
    }
}
