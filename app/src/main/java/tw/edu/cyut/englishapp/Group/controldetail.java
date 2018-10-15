package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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

import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.PreExamActivity;
import tw.edu.cyut.englishapp.preexam_test;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;
import tw.edu.cyut.englishapp.preexam_test;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class controldetail extends AppCompatActivity {
    private  String index,uid,day,leadto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controldetail);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        LoadTopicSpeak(uid);
        AlertDialog("Notice","Press “Continue” to start the 5 trial samples to test your earphones and adjust the volume.\nOtherwise, press “Go back” to return to the previous page.");



    }

    public void bt_continue(View view) {
        Log.d("leadto--------->",leadto);
        if (leadto.equals("Control_Pre_test")){
            Intent intent = new Intent();
            intent.setClass(controldetail.this, ControlPreTestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("index","0");
            intent.putExtras(bundle);
            startActivity(intent);
            controldetail.this.finish();

        }else if(leadto.equals("Control_training")) {
            Intent intent = new Intent();
            intent.setClass(controldetail.this, group_control.class);
            startActivity(intent);
            controldetail.this.finish();

        }else if(leadto.equals("Exam_Pre_test")) {
            Intent intent = new Intent();
            intent.setClass(controldetail.this, preexam_test.class);
            startActivity(intent);
            controldetail.this.finish();

        }else if(leadto.equals("Examing")) {
            Intent intent = new Intent();
            intent.setClass(controldetail.this, PreExamActivity.class);
            startActivity(intent);
            controldetail.this.finish();

        }else{
            AlertDialog("Who are you?","Who are you? Who are you? Who are you? Who are you? Who are you? Who are you? Who are you? Who are you?");
        }



    }

    public void bt_back(View view) {
        Intent intent = new Intent();
        intent.setClass(controldetail.this, LoginActivity.class);
        startActivity(intent);
        controldetail.this.finish();//結束目前 Activity

    }


    public void LoadTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/buildtestdata.php";
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
                            if (Integer.parseInt(day)>0 && Integer.parseInt(day)<=15 ){
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Control_Pre_test";
                                }else{
                                    leadto="Control_training";
                                }
                            }else{
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Exam_Pre_test";
                                }else{
                                    leadto="Examing";
                                }

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
        RequestQueue requestQueue = Volley.newRequestQueue(controldetail.this);
        requestQueue.add(stringRequest);
    }

    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(controldetail.this)
                .title(title)
                .customView(R.layout.alert_dialog, wrapInScrollView)
                .backgroundColorRes(R.color.colorBackground)
                .positiveText("OK")
                .build();
        final View item = dialog.getCustomView();
        TextView content_txt=item.findViewById(R.id.dialog_content);
        content_txt.setText(content);
        dialog.show();
    }
}
