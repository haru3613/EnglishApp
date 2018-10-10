package tw.edu.cyut.englishapp.Group;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import tw.edu.cyut.englishapp.R;

public class group_control extends AppCompatActivity {
    private ImageView bt_topic_speak,background,bt_next,bt_speak_start,bt_stop_speak,bt_speak_talker;
    private TextView text_count;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_control);
        background = findViewById(R.id.background);
        bt_topic_speak = findViewById(R.id.bt_topic_speak);
        bt_speak_talker = findViewById(R.id.bt_speak_talker);
        bt_speak_start = findViewById(R.id.bt_speak_start);
        bt_stop_speak = findViewById(R.id.bt_stop_speak);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_next = findViewById(R.id.bt_next);
        bt_next.setVisibility(View.INVISIBLE);
        bt_next.setEnabled(false);
        text_count = findViewById(R.id.text_count);


        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    public void bt_speak_start(View view) {
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException ise) {
            // make something ...
        } catch (IOException ioe) {
            // make something
        }
        bt_speak_start.setEnabled(false);
        bt_speak_start.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(true);
        bt_stop_speak.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
    }

    public void bt_stop_speak(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        bt_speak_start.setEnabled(true);
        bt_speak_start.setVisibility(View.VISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_speak_talker.setEnabled(true);
        bt_speak_talker.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();

    }

    public void bt_topic_speak(View view) {

    }

    public void bt_speak_talker(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // make something
        }
    }

    public void bt_next(View view) {

    }
}
