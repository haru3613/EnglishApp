package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.R;

public class controldetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controldetail);
    }

    public void bt_continue(View view) {
        Intent i = new Intent();
        i.setClass(controldetail.this, LoginActivity.class);
        startActivity(i);
        controldetail.this.finish();//結束目前 Activity

    }

    public void bt_back(View view) {
        Intent i = new Intent();
        i.setClass(controldetail.this, ControlPreTestActivity.class);
        startActivity(i);
        controldetail.this.finish();//結束目前 Activity

    }
}
