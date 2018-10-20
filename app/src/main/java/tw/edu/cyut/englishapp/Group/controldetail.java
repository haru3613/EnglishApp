package tw.edu.cyut.englishapp.Group;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                                    AlertDialog("Instructions ","You are about to start the Chinese tones training section.\n" +
                                            "Please make sure your earphones are functioning properly and carefully follow the instructions listed below: \n" +
                                            "您將開始中文聲調訓練階段，請確認您的耳機可正常運作，並仔細閱讀下列資訊:\n" +
                                            "Once you start today’s training, continue through all of the 103 (or 104) “listen and select” samples. \n" +
                                            "開始今日的訓練後，請持續完成所有103(或104)題的「聽音選擇聲調」。\n" +
                                            "Press the speaker icon (播放音圖示) to play the sound, then choose the tone you hear. You can listen to each sample up to 3 times before choosing.\n" +
                                            "按下播放音圖示聽音檔，每個錄音檔最多可播放3次。\n" +
                                            "Once you press confirm, the test will move on to the next sample, and you will not be able to go back to the previous sample. \n" +
                                            "按下「確認」後，將會進入下一題，並且無法回到上一題。\n" +
                                            "The training section will take about 40 minutes. Do not pause or take a break from the test once you begin. If the system does not detect any activity for 3 minutes, it will terminate the session, in which case you will need to sign in and start all-over again. Restarting the session may affect the award you will receive for participating.\n" +
                                            "此訓練階段大約40分鐘，開始後請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n" +
                                            "\n" +
                                            "Press “Continue” to start the 5 trial samples to test your earphones and adjust the volume. Otherwise, press “Go back” to return to the previous page.\n" +
                                            "請按下「繼續」開始5題的測試題，檢查您的耳機和調整音量，或是按下「返回」到前一頁。\n");
                                }else{
                                    leadto="Control_training";
                                    AlertDialog("Instructions ","You are about to start the Chinese tones training section.\n" +
                                            "Please make sure your earphones are functioning properly and carefully follow the instructions listed below: \n" +
                                            "您將開始中文聲調訓練階段，請確認您的耳機可正常運作，並仔細閱讀下列資訊:\n" +
                                            "Once you start today’s training, continue through all of the 103 (or 104) “listen and select” samples. \n" +
                                            "開始今日的訓練後，請持續完成所有103(或104)題的「聽音選擇聲調」。\n" +
                                            "Press the speaker icon (播放音圖示) to play the sound, then choose the tone you hear. You can listen to each sample up to 3 times before choosing.\n" +
                                            "按下播放音圖示聽音檔，每個錄音檔最多可播放3次。\n" +
                                            "Once you press confirm, the test will move on to the next sample, and you will not be able to go back to the previous sample. \n" +
                                            "按下「確認」後，將會進入下一題，並且無法回到上一題。\n" +
                                            "The training section will take about 40 minutes. Do not pause or take a break from the test once you begin. If the system does not detect any activity for 3 minutes, it will terminate the session, in which case you will need to sign in and start all-over again. Restarting the session may affect the award you will receive for participating.\n" +
                                            "此訓練階段大約40分鐘，開始後請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。");
                                }
                            }else{
                                if (Integer.parseInt(index)==0 ){
                                    leadto="Exam_Pre_test";
                                    AlertDialog("Instructions ","You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                            "您將開始前測，請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n" +
                                            "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                            "開始測驗後，請完成138題「看字錄音」的題目。\n" +
                                            "Before pressing confirm, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press Record. You can record your response up to 3 times. \n" +
                                            "按下「確認」前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n" +
                                            "After pressing “Confirm”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                            "按下「確認」後，將進入下一題，您無法再回到前一題。\n" +
                                            "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                            "測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n");
                                }else{
                                    leadto="Examing";
                                    AlertDialog("Instructions ","You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                            "您將開始前測，請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n" +
                                            "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                            "開始測驗後，請完成138題「看字錄音」的題目。\n" +
                                            "Before pressing confirm, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press Record. You can record your response up to 3 times. \n" +
                                            "按下「確認」前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n" +
                                            "After pressing “Confirm”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                            "按下「確認」後，將進入下一題，您無法再回到前一題。\n" +
                                            "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                            "測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n");
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
