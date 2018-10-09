package tw.edu.cyut.englishapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.Group.TestGroupActivity;
import tw.edu.cyut.englishapp.model.ItemAccount;
import tw.edu.cyut.englishapp.model.ItemTopic;

public class AnswerActivity extends Activity {

    private ImageButton play,ans1,ans2,ans3,ans4,next;
    private String correct_ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        initAnswerActivity();

        Intent intent = this.getIntent();//取得傳遞過來的資料
        final String t_index = intent.getStringExtra("index");

        //Load exam data
        LoadExamData(t_index);


        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO update test table and show correct ans
                ans2.setEnabled(false);
                ans3.setEnabled(false);
                ans4.setEnabled(false);
                if (correct_ans.equals("1")){
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
                ans1.setEnabled(false);
                ans3.setEnabled(false);
                ans4.setEnabled(false);
                if (correct_ans.equals("2")){
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
                ans1.setEnabled(false);
                ans2.setEnabled(false);
                ans4.setEnabled(false);
                if (correct_ans.equals("3")){
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
                ans2.setEnabled(false);
                ans3.setEnabled(false);
                ans1.setEnabled(false);
                if (correct_ans.equals("4")){
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
                //TODO  播放音檔

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //開啟自己並讓index+1
                OpenAnswerActivity(String.valueOf(Integer.parseInt(t_index)+1));
            }
        });


    }

    private void OpenAnswerActivity(String t_index){
        Intent ToAnswer=new Intent(AnswerActivity.this,AnswerActivity.class);
        ToAnswer.putExtra("index", t_index);
        startActivity(ToAnswer);
        finish();
    }

    private void initAnswerActivity(){
        play.findViewById(R.id.play);
        ans1.findViewById(R.id.ans1);
        ans2.findViewById(R.id.ans2);
        ans3.findViewById(R.id.ans3);
        ans4.findViewById(R.id.ans4);
        next.findViewById(R.id.next);

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
                            String pinyin=posts.get(0).getPinyin();
                            String name=posts.get(0).getPinyin();
                            correct_ans=String.valueOf(posts.get(0).getAns());

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
}
