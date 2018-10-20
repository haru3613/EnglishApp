package tw.edu.cyut.englishapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class todayisfinish extends AppCompatActivity {
    private String day;
    private TextView textView10,textView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayisfinish);
        textView10=findViewById(R.id.textView10);
        textView9=findViewById(R.id.textView9);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        day=sharedPreferences.getString("day",null);
        if (Integer.parseInt(day)==0){
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            textView9.setText("After you have completed the pre-test, the researcher will need some time to analyze the data. Once the researcher have completed the analysis, you will receive a notice advising you to proceed with the 15-Day Tone Training section of the experiment.");
        }else if(Integer.parseInt(day)==16){
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            textView9.setText("Please return in 2 weeks for a follow-up post-test, which will be identical to the original post-test. Thank you for your participation.\n" +
                    "請您於兩週後回到系統完成延時測，此測驗與後測相似。感謝您參與協助此研究。\n");

        }else if(Integer.parseInt(day)==17){
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            textView9.setText("Thank you for your participation. Congratulations! You have completed the Mandarin Chinese Tones learning experiment. \n" +
                    "感謝您參與此研究，恭禧您完成本次華語聲調學習實驗。\n");

        }


        else{
            textView10.setVisibility(View.INVISIBLE);
            textView9.setVisibility(View.INVISIBLE);
        }

    }
}
