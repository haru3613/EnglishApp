package tw.edu.cyut.englishapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import tw.edu.cyut.englishapp.Group.TestGroupActivity;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class PreTestActivity extends Activity {
    private String index;
    private String [][] pre_test_array=new String[16][139];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
        Button btn_start=findViewById(R.id.btn_pre_start);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String uid=sharedPreferences.getString("uid",null);
        Log.d("I'm in Pretest","Check uid = "+uid);
        //讀取第幾題
        LoadTopicSpeak(uid);

        //xml to array
        int j=0;
        for (TypedArray item : ResourceHelper.getMultiTypedArray(PreTestActivity.this, "day")) {
            for (int i=1;i<=Integer.parseInt(item.getString(0));i++){
                pre_test_array[j][i]=item.getString(i);
            }
            j++;
        }

        AlertDialog("Pre-test","You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPreStartActivity(index);
            }
        });
    }

    private void OpenPreStartActivity(String t_index){
        Intent ToAnswer=new Intent(PreTestActivity.this,PreStartActivity.class);
        Bundle mBundle = new Bundle();
        ToAnswer.putExtra("index", t_index);
        mBundle.putSerializable("audio_list", pre_test_array);
        ToAnswer.putExtras(mBundle);
        startActivity(ToAnswer);
        finish();
    }
    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(PreTestActivity.this)
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
        RequestQueue requestQueue = Volley.newRequestQueue(PreTestActivity.this);
        requestQueue.add(stringRequest);
    }
}
