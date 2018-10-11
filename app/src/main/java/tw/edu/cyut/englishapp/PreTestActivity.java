package tw.edu.cyut.englishapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import tw.edu.cyut.englishapp.Group.TestGroupActivity;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class PreTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String uid=sharedPreferences.getString("uid",null);


        AlertDialog("Pre-test","You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:");


        //TODO if finish pre-test then update user day=1
        String type = "Update user day";
        Backgorundwork backgorundwork = new Backgorundwork(PreTestActivity.this);
        backgorundwork.execute(type,uid,"1");
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
}
