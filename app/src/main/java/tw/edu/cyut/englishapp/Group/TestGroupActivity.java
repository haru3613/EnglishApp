package tw.edu.cyut.englishapp.Group;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import java.util.Timer;
import java.util.TimerTask;

import tw.edu.cyut.englishapp.AnswerActivity;
import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.PreExamActivity;
import tw.edu.cyut.englishapp.PreExam_test;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.ResourceHelper;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static com.android.volley.VolleyLog.TAG;
import static tw.edu.cyut.englishapp.LoginActivity.KEY;


public class TestGroupActivity extends Activity {
    private String day,index,level,qbank;

    private String [][] audio_list=new String[16][139];

    private  Boolean isExit = false;
    private  Boolean hasTask = false;

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
        setContentView(R.layout.activity_test_group);

        Button btn_start=findViewById(R.id.btn_start);
        Button btn_back=findViewById(R.id.btn_back);
        int j=0;
        for (TypedArray item : ResourceHelper.getMultiTypedArray(TestGroupActivity.this, "day")) {
            for (int i=0;i<=Integer.parseInt(item.getString(0));i++){
                audio_list[j][i]=item.getString(i);
            }
            j++;
        }


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        final String uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        level=sharedPreferences.getString("level",null);
        Log.d(TAG, "onCreate: "+uid+","+day+","+level);

        //第幾個題庫
        qbank=sharedPreferences.getString("qbank",null);


        InsertTopicSpeak(uid);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToLogin=new Intent(TestGroupActivity.this,LoginActivity.class);
                startActivity(ToLogin);
                finish();
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!level.equals("Teacher")){
                    if (day.equals("0") || day.equals("16") || day.equals("20")){
                        if (Integer.parseInt(index)>0){
                            //insert to topic speak
                            //pre-exam
                            Intent ToPreExam=new Intent(TestGroupActivity.this,PreExamActivity.class);
                            startActivity(ToPreExam);
                            finish();
                        }else if (Integer.parseInt(index)==0){
                            Intent ToPreExamTest=new Intent(TestGroupActivity.this,PreExam_test.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("index","0");
                            ToPreExamTest.putExtras(bundle);
                            startActivity(ToPreExamTest);
                            finish();
                        }
                    }else{
                        if (Integer.parseInt(index)==0){
                            OpenTestPreTest();
                        }else if (Integer.parseInt(index)<=Integer.parseInt(audio_list[Integer.parseInt(qbank)][0])){
                            OpenAnswerActivity(index);

                        }
                    }

                }
            }
        });



    }
    private void OpenAnswerActivity(String t_index){
        Intent ToAnswer=new Intent(TestGroupActivity.this,AnswerActivity.class);
        Bundle mBundle = new Bundle();
        ToAnswer.putExtra("index", t_index);
        ToAnswer.putExtra("day", day);
        ToAnswer.putExtra("qbank", qbank);
        mBundle.putSerializable("audio_list", audio_list);
        ToAnswer.putExtras(mBundle);
        startActivity(ToAnswer);
        finish();
    }
    private void OpenTestPreTest(){
        Intent ToTest=new Intent(TestGroupActivity.this,TestPreTestActivity.class);
        Bundle mBundle = new Bundle();
        ToTest.putExtra("index","0");
        String[] test_audio=new String[]{"ba1dT","chang2dT","deng3dT","he4dT","ta1dT"};
        ToTest.putExtra("test_audio", test_audio);
        ToTest.putExtras(mBundle);
        startActivity(ToTest);
        finish();
    }


    public void InsertTopicSpeak(final String uid){
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
                            index = posts.get(0).getTopic_index();

                            if (!level.equals("Teacher")) {
                                if (day.equals("0") || day.equals("16") || day.equals("20")) {
                                    if (Integer.parseInt(index) > 0) {
                                        //insert to topic speak
                                        //pre-exam
                                        AlertDialog("Instructions ", "You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:\n" +
                                                "您將開始前測，請確定您的麥克風可正常運作，並仔細閱讀下列資訊:\n" +
                                                "Once you start the test, continue through all of the 138 “read and record” questions. \n" +
                                                "開始測驗後，請完成138題「看字錄音」的題目。\n" +
                                                "Before pressing confirm, playback the recording and listen to make sure you have recorded your response completely. If you need to record your response again, press Record. You can record your response up to 3 times. \n" +
                                                "按下「確認」前，請播放您的錄音，檢查錄音檔完整。如果需要重新錄製，請按「錄音」，最多可錄製3次。\n" +
                                                "After pressing “Confirm”, you move on to the next question and will not be able to go back to the previous question.\n" +
                                                "按下「確認」後，將進入下一題，您無法再回到前一題。\n" +
                                                "Do not pause or take a break from the test once you begin.  If the system does not detect any activity for 3 minutes, it will terminate the test, in which case you will need to sign in and start all-over again. Restarting the test may affect the award you will receive for participating. \n" +
                                                "測驗開始後，請勿暫停或休息。如果系統偵測3分鐘無任何動作，將會自動結束測驗，您必須再次登入並重新開始測驗。重新開始測驗可能影響您的實驗獎勵。\n");
                                    } else if (Integer.parseInt(index) == 0) {

                                    }
                                }else {
                                    if (Integer.parseInt(index) == 0) {
                                        AlertDialog("Instructions","Press “Continue” to start the 5 trial samples to test your microphone for recording volumes and quality.");
                                    } else if (Integer.parseInt(index) <= Integer.parseInt(audio_list[Integer.parseInt(qbank)][0])) {
                                        AlertDialog("Instructions ", "You are about to start the Chinese tones training section.\n" +
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
        RequestQueue requestQueue = Volley.newRequestQueue(TestGroupActivity.this);
        requestQueue.add(stringRequest);
    }
    private void AlertDialog(String title,String content){
        boolean wrapInScrollView = true;
        MaterialDialog dialog=new MaterialDialog.Builder(TestGroupActivity.this)
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
