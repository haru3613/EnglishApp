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
        }else{
            textView10.setVisibility(View.INVISIBLE);
            textView9.setVisibility(View.INVISIBLE);
        }

    }
}
