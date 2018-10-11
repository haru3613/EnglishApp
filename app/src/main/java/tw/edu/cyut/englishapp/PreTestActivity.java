package tw.edu.cyut.englishapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

public class PreTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);


        AlertDialog("Pre-test","You are about to start the pretest. Please make sure your microphone is functioning properly and carefully follow the instructions listed below:");

        //TODO update user day=1
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
