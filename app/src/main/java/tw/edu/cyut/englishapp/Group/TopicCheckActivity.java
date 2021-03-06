package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTest;
import tw.edu.cyut.englishapp.model.ItemTopicCheck;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;
import tw.edu.cyut.englishapp.todayisfinish;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class TopicCheckActivity extends Activity {

    private ImageButton play,ans1,ans2,ans3,ans4,next;
    private TextView count,file;
    private String qbank,uid, choice_ans,day,file_name,checked_file,c,t_index;
    private  Boolean isExit = false;
    private  Boolean hasTask = false;
    private boolean playPause,emptyList;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    boolean getfile;
    ArrayList<String> checked_topic=new ArrayList<String>();
    ArrayList<String> total_topic=new ArrayList<String>();
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

        emptyList=false;
        getfile=false;

        total_topic=twoDArrayToList(audio_list);
        for (int i=0;i<=138;i++){
            total_topic.set(i,null);
        }
        total_topic.removeAll(Collections.singleton(null));//刪掉所有null
        total_topic=getStringsWithoutEqualLength(3,total_topic);//刪掉所有檔名長度為3的
        addtopic();




        progressDialog.show();

        LoadChecked();

        choice_ans="";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Drawable d = getResources().getDrawable(R.drawable.border_image);


        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="1";
                ans1.setBackgroundDrawable(d);
                ans4.setBackgroundDrawable(null);
                ans2.setBackgroundDrawable(null);
                ans3.setBackgroundDrawable(null);
            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="2";
                ans2.setBackgroundDrawable(d);
                ans1.setBackgroundDrawable(null);
                ans4.setBackgroundDrawable(null);
                ans3.setBackgroundDrawable(null);
            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="3";
                ans3.setBackgroundDrawable(d);
                ans1.setBackgroundDrawable(null);
                ans2.setBackgroundDrawable(null);
                ans4.setBackgroundDrawable(null);
            }
        });
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_ans="4";
                ans4.setBackgroundDrawable(d);
                ans1.setBackgroundDrawable(null);
                ans2.setBackgroundDrawable(null);
                ans3.setBackgroundDrawable(null);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause && getfile==true) {
                    if (uid.equals("8")){
                        file.setVisibility(View.VISIBLE);
                    }

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
        if (emptyList){
            //如果是空陣列
            Intent ToFinish=new Intent(TopicCheckActivity.this,LoginActivity.class);
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
        file=findViewById(R.id.file_name);
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

    public void LoadChecked(){
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
                            Type listType = new TypeToken<ArrayList<ItemTopicCheck>>() {}.getType();
                            if (!response.contains("Undefined")){
                                ArrayList<ItemTopicCheck> posts = new ArrayList<ItemTopicCheck>();
                                posts = mGson.fromJson(response, listType);
                                Log.d(TAG, "onResponse: "+posts);
                                for (int i = 0; i < posts.size(); i++) {
                                    checked_topic.add(posts.get(i).getFilename());
                                }
                                Log.d(TAG, "list: "+checked_topic);
                                Log.d(TAG, "size: "+checked_topic.size());
                                Log.d(TAG, "list: "+total_topic);
                                Log.d(TAG, "size: "+total_topic.size());
                                total_topic.removeAll(checked_topic);
                                Log.d(TAG, "size: "+total_topic.size());
                                Log.d(TAG, "list: "+total_topic);
                                count.setText(String.valueOf(1616-total_topic.size()+1)+"/1616");
                            }else{
                                count.setText("1/1616");
                            }


                            if (total_topic.size()!=0){
                                Random ran = new Random();
                                int i=ran.nextInt(total_topic.size());
                                file_name=total_topic.get(i);
                                Log.d(TAG, "onResponse: i=="+i);
                                Log.d(TAG, "onResponse: "+file_name);
                                c=file_name.substring(file_name.length()-3,file_name.length()-2);
                                int index=file_name.indexOf(c);
                                Log.d("TAG","題目名稱:"+file_name.substring(0,index)+"答案:"+c);
                                getfile=true;

                                if (uid.equals("8")){
                                    file.setText("檔名:"+file_name);
                                }

                            }else{
                                emptyList=true;
                                Toast.makeText(TopicCheckActivity.this,"Finish!!!",Toast.LENGTH_LONG).show();

                            }

                            if (progressDialog.isShowing()){
                                progressDialog.cancel();
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
                params.put("uid",uid);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(TopicCheckActivity.this);
        requestQueue.add(stringRequest);
    }


    private void addtopic(){
        total_topic.add("niang4JS");
        total_topic.add("ma1JH");
        total_topic.add("liu2CL");
        total_topic.add("lvan2JS");
        total_topic.add("nang4JH");
        total_topic.add("miao2CL");total_topic.add("niu3JY");total_topic.add("men1JS");total_topic.add("neng3JY");total_topic.add("nuo2CL");total_topic.add("min1JY");
        total_topic.add("nei2JH");total_topic.add("luan1CL");total_topic.add("mi3JY");total_topic.add("nu1JH");total_topic.add("le2JS");total_topic.add("lang3CL");
        total_topic.add("nie2JS");total_topic.add("lai4JH");total_topic.add("lin3CL");total_topic.add("mao2JY");total_topic.add("long4JS");
        total_topic.add("lan4JS");total_topic.add("ma3JH");total_topic.add("la1JS");total_topic.add("mo2JH");total_topic.add("nou3CL");total_topic.add("miu4JS");
        total_topic.add("nian1JH");total_topic.add("luo3CL");total_topic.add("nin2JH");total_topic.add("lie3JS");total_topic.add("liang1CL");total_topic.add("ming2JY");
        total_topic.add("mian3JH");total_topic.add("mu4JS");total_topic.add("ning3JH");total_topic.add("na4JS");total_topic.add("liao1CL");total_topic.add("me4JY");
        total_topic.add("meng4CL");total_topic.add("leng1JY");total_topic.add("nong3CL");total_topic.add("lao1JY");total_topic.add("mang2JS");total_topic.add("nue2CL");
        total_topic.add("lv3JY");total_topic.add("nan1JS");total_topic.add("lue1CL");total_topic.add("li2JS");total_topic.add("niao4JH");total_topic.add("nuan4JY");
        total_topic.add("lei3JS");total_topic.add("nv1JS");total_topic.add("lun2JH");total_topic.add("ni1CL");total_topic.add("lou2JY");total_topic.add("mie1JS");
        total_topic.add("lia4JH");total_topic.add("nai1JS");total_topic.add("lian4JS");total_topic.add("ling2CL");total_topic.add("ne3CL");total_topic.add("nao4JS");
        total_topic.add("mei1JH");total_topic.add("nen2JY");total_topic.add("man3JH");total_topic.add("lu4CL");total_topic.add("mou4JS");
    }

    public <String> ArrayList<String> twoDArrayToList(String[][] twoDArray) {
        ArrayList<String> list = new ArrayList<String>();
        for (String[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }

    public ArrayList<String> getStringsWithoutEqualLength(int len, ArrayList<String> lijst){
        ArrayList<String> list = new ArrayList<String>();
        for(String woord: lijst){
            if(woord.length() != len){
                list.add(woord);
            }
        }
        return(list);
    }
}
