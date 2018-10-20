package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTest;
import tw.edu.cyut.englishapp.model.ItemTopicCheck;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;
import tw.edu.cyut.englishapp.todayisfinish;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class TopicCheckActivity extends Activity {

    private ImageButton play,ans1,ans2,ans3,ans4,next;
    private TextView count;
    private String qbank,uid, choice_ans,day,file_name,checked_file;
    private  Boolean isExit = false;
    private  Boolean hasTask = false;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    boolean checked;
    private String[][] audio_list=new String[16][105];
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
        progressDialog = new ProgressDialog(this);
        uid="";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        Log.d("onCreate", "uid: "+uid);



        //接收array
        audio_list=null;
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("audio_list");
        if(objectArray!=null){
            audio_list = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                audio_list[i]=(String[]) objectArray[i];
            }
        }


        checked=false;

        do {
            int i,j;
            do{
                Random ran = new Random();
                i=ran.nextInt(16);
                j=ran.nextInt(Integer.parseInt(audio_list[i][0]));
                Log.d(TAG, "onCreate: "+i+":"+j);
                file_name=audio_list[i][j];
            }while(file_name.equals(""));

            audio_list[i][j]="";
            Log.d(TAG, "onCreate: 音檔名稱:"+file_name);
            AsyncTask task = new CheckedTopic().execute(file_name);

            //LoadChecked(file_name);
            //如果filename=""或等於資料庫有的音檔則重抓
            while (task.getStatus()!=AsyncTask.Status.FINISHED){

            }
            Log.d(TAG, "onCreate: checked=="+checked);
        }while (checked==true);



        final String c=file_name.substring(file_name.length()-3,file_name.length()-2);
        int index=file_name.indexOf(c);
        Log.d("TAG","題目名稱:"+file_name.substring(0,index)+"答案:"+c);


        choice_ans="";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);




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
                if (!playPause && !progressDialog.isShowing()) {

                    if (initialStage) {
                        new Player().execute("http://140.122.63.99/topic_audio/all_audio/"+file_name+".wav");
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
                if (!choice_ans.equals("")){
                    String type = "Insert topic check";
                    Backgorundwork backgorundwork = new Backgorundwork(TopicCheckActivity.this);
                    backgorundwork.execute(type,uid,file_name,c,choice_ans);
                    OpenSelf();
                }else{
                    Toast.makeText(TopicCheckActivity.this,"Choose your answer",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void OpenSelf(){
        Boolean emptyList=true;
        for (int i=0;i<=15;i++){
            for(int j=1;j<=Integer.parseInt(audio_list[i][0]);j++){
                if (audio_list[i][j].equals(""))
                    emptyList=false;
            }
        }
        if (emptyList){
            //如果是空陣列
            Intent ToFinish=new Intent(TopicCheckActivity.this,todayisfinish.class);
            startActivity(ToFinish);
            finish();
        }else{
            Intent ToTopicCheck=new Intent(TopicCheckActivity.this,TopicCheckActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("audio_list", audio_list);
            ToTopicCheck.putExtras(mBundle);
            startActivity(ToTopicCheck);
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

    }


    private void AlertDialog(int draw){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(TopicCheckActivity.this)
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

    public void LoadChecked(final String filename){
        String url = "http://140.122.63.99/app/load_checked_file.php";
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

                            if (!response.contains("Undefined")){
                                List<ItemTopicCheck> posts = new ArrayList<ItemTopicCheck>();
                                posts = Arrays.asList(mGson.fromJson(response, ItemTopicCheck[].class));

                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                }
                                checked=true;
                            }else{
                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();

                                }
                                checked=false;
                            }


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
                params.put("filename",filename);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(TopicCheckActivity.this);
        requestQueue.add(stringRequest);
    }


    private class CheckedTopic extends AsyncTask<String , Void , String>{
        private ProgressDialog progressBar;

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定

            super.onPreExecute();
            progressBar = new ProgressDialog(TopicCheckActivity.this);
            progressBar.setMessage("Loading...");
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //執行中 在背景做事情

            try {
                String filename = params[0];

                String connection_url ="http://140.122.63.99/app/load_checked_file.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("filename","UTF-8")+"="+URLEncoder.encode(filename,"UTF-8");
                Log.d("POST_DATA", "doInBackground: "+post_data);



                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line=null;
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //執行中 可以在這邊告知使用者進度
            super.onProgressUpdate(values);
            progressBar.show();
        }

        @Override
        protected void onPostExecute(String result) {
            //執行後 完成背景任務
            super.onPostExecute(result);

            if (result.contains("Undefined")){
                checked=false;
            }else{
                checked=true;
            }

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        }
    }
}
