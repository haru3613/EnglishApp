package tw.edu.cyut.englishapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import tw.edu.cyut.englishapp.Group.controldetail;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class test_examinerGroup extends AppCompatActivity {
    private Button bt_topic_check;
    private  String index,uid,day,leadto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_examiner_group);
        bt_topic_check=findViewById(R.id.bt_topic_check);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        AlertDialog("Instructions ",
                "您將開始聽音檢核階段，在正式聽音前，請利用5個練習題測試您的麥克風及耳機功能。請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n\n" +
                "Please test your microphone and headset by first recording the sample word (with the indicated Chinese tone) and then listening to your recording playback. You may record and playback a maximum of 3 times.\n" +
                "請閱讀題目並錄音再播放，測試您的耳機，最高可錄音及播放3次。");
    }

    public void gotocheck(View view) {
        Intent ToCheck=new Intent(test_examinerGroup.this,TeacherPreTestActivity.class);
        startActivity(ToCheck);
        finish();

    }


    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        MaterialDialog dialog=new MaterialDialog.Builder(test_examinerGroup.this)
                .title(ssBuilder)
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
