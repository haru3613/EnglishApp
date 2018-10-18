package tw.edu.cyut.englishapp.Group;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.ResourceHelper;

import static com.android.volley.VolleyLog.TAG;

public class TeacherGroupActivity extends Activity {

    private Button topic_check;


    private String [][] audio_list=new String[16][139];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_group);

        initTeacherGroupActivity();

        int j=0;
        for (TypedArray item : ResourceHelper.getMultiTypedArray(TeacherGroupActivity.this, "day")) {
            for (int i=0;i<=Integer.parseInt(item.getString(0));i++){
                audio_list[j][i]=item.getString(i);
            }
            j++;
        }

        //示範音檢核
        topic_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //開啟TopicCheck
                OpenTopicCheckActivity();
            }
        });


    }
    private void initTeacherGroupActivity(){

//        topic_check=findViewById(R.id.bt_topic_check);
    }

    private void OpenTopicCheckActivity(){

        Intent ToCheck=new Intent(TeacherGroupActivity.this,TopicCheckActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("audio_list", audio_list);
        ToCheck.putExtras(mBundle);
        startActivity(ToCheck);
        finish();
    }

    public void bt_Test_xaminer(View view) {


    }
}
