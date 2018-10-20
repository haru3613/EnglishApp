package tw.edu.cyut.englishapp.Group;

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
import android.widget.TextView;
import android.widget.Toast;

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
import tw.edu.cyut.englishapp.PreExam_test;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

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
        if (Integer.parseInt(day)>17){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(controldetail.this, LoginActivity.class);
            startActivity(intent);
            controldetail.this.finish();
        }

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
            intent.setClass(controldetail.this, PreExam_test.class);
            Bundle bundle = new Bundle();
            bundle.putString("index","0");
            intent.putExtras(bundle);
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
                                    AlertDialog("Instructions ","You are about to start the Mandarin Chinese tones training section.  Before the actual training, five trail samples will enable to test your microphone and headset.\n" +
                                            "Press “Continue” to start the 5 trial samples to test your microphone for recording volumes and quality. Otherwise, press “Go back” to the previous page.\n" +
                                            "您將開始中文聲調訓練階段，在正式訓練前，請利用5個練習題測試您的麥克風及耳機功能。請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n\n" +
                                            "Please test your microphone and headset by first recording the sample word (with the indicated Chinese tone) and then listening to your recording playback. You may record and playback a maximum of 3 times.\n" +
                                            "請閱讀題目並錄音再播放，測試您的麥克風及耳機，最高可錄音及播放3次。");
                                }else{
                                    leadto="Control_training";
                                    AlertDialog("Instructions ","\n" +
                                            "Please make sure your microphone and earphones are functioning properly and carefully follow the instructions listed below:\n" +
                                            "請確認您的麥克風和耳機可正常運作，並仔細閱讀下列資訊:\n\n" +
                                            "Once you start today’s training, continue through all of the 103 (or 104) “listen and record” samples. \n" +
                                            "開始今日的訓練後，請持續完成所有103(或104)題的「聽音選擇聲調」。\n\n" +
                                            "Before pressing “Go”, playback the recording  and listen to make sure you have recorded your response completely. If you need to record your response again, press the microphone icon. Press it again to stop recording. You can record your response up to 3 times. \n" +
                                            "按下 “Go” 前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n\n" +
                                            "Once you press “Go”, the test will move on to the next sample, and you will not be able to go back to the previous sample. \n" +
                                            "按下 “Go” 後，將會進入下一題，並且無法回到上一題。\n\n" +
                                            "The session will take about 40 minutes. Do not pause or take a break from the test once you begin. If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                            "此訓練階段大約40分鐘，開始後請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。");
                                }
                            }else if(Integer.parseInt(day)==0 ){
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Exam_Pre_test";
                                    AlertDialog("Instructions ","You are about to start the pretest. Before the actual test, five trail samples will enable to test your microphone and headset. Press “Continue” to start the five trial samples to test your microphone for recording volumes and quality. Otherwise, press “Go back” to the previous page.\n" +
                                            "您將開始前測，在正式測驗前，請利用5個練習題測試您的麥克風及耳機功能。請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n\nPlease test your microphone and headset by first recording the sample word (with the indicated Chinese tone) and then listening to your recording playback. You may record and playback a maximum of 3 times.\n" +
                                            "請閱讀題目並錄音再播放，測試您的麥克風及耳機，最高可錄音及播放3次。\n");
                                }else{
                                    leadto="Examing";
                                    AlertDialog("Instructions ","Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                            "請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n" +
                                            "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                            "開始測驗後，請完成138題「看字錄音」的題目。\n\n" +
                                            "Before pressing “Go”, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press the microphone icon. You can record your response up to 3 times. \n" +
                                            "按下 “Go” 前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n\n" +
                                            "After pressing “Go”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                            "按下 “Go” 後，將進入下一題，您無法再回到前一題。\n\n" +
                                            "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. 測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n");
                                }

                            }else if(Integer.parseInt(day)==16 ){
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Exam_Pre_test";
                                    AlertDialog("Instructions ","You are about to start the post-test. Before the actual test, five practice questions will enable to test your microphone and headset. Press “Continue” to start the five trial samples to test your microphone for recording volumes and quality. Otherwise, press “Go back” to the previous page.\n" +
                                            "您將開始後測，在正式測驗前，請利用5個練習題測試您的麥克風及耳機功能。請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n\n\n" +
                                            "Please test your microphone and headset by first recording the sample word (with the indicated Chinese tone) and then listening to your recording playback. You may record and playback a maximum of 3 times.\n" +
                                            "請閱讀題目並錄音再播放，測試您的麥克風及耳機，最高可錄音及播放3次。");
                                }else{
                                    leadto="Examing";
                                    AlertDialog("Instructions ","Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                            "請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n\n" +
                                            "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                            "開始測驗後，請完成138題「看字錄音」的題目。\n\n" +
                                            "Before pressing “Go”, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press Record. You can record your response up to 3 times. \n" +
                                            "按下 “Go” 前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n\n" +
                                            "After pressing “Go”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                            "按下 “Go” 後，將進入下一題，您無法再回到前一題。\n\n" +
                                            "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                            "測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。");
                                }

                            }else if(Integer.parseInt(day)==17 ){
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Exam_Pre_test";
                                    AlertDialog("Instructions ","You are about to start the follow-up post-test. Before the actual test, five practice questions will enable to test your microphone and headset. Press “Continue” to start the five trial samples to test your microphone for recording volumes and quality. Otherwise, press “Go back” to the previous page.\n" +
                                            "您將開始延時測，在正式測驗前，請利用5個練習題測試您的麥克風及耳機功能。請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n\n" +
                                            "Please test your microphone and headset by first recording the sample word (with the indicated Chinese tone) and then listening to your recording playback. You may record and playback a maximum of 3 times.\n" +
                                            "請閱讀題目並錄音再播放，測試您的麥克風及耳機，最高可錄音及播放3次。\n");
                                }else{
                                    leadto="Examing";
                                    AlertDialog("Instructions ","Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                            "您將開始延時測，在正式測驗前，請利用5個練習題測試您的麥克風及耳機功能。請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n\n" +
                                            "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                            "開始測驗後，請完成138題「看字錄音」的題目。\n\n" +
                                            "Before pressing “Go”, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press Record. You can record your response up to 3 times. \n" +
                                            "按下 “Go” 前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n\n" +
                                            "After pressing “Go”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                            "按下 “Go” 後，將進入下一題，您無法再回到前一題。\n\n" +
                                            "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                            "測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n\n");
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
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        MaterialDialog dialog=new MaterialDialog.Builder(controldetail.this)
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
