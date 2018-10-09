package tw.edu.cyut.englishapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import tw.edu.cyut.englishapp.Group.ControlGroupActivity;
import tw.edu.cyut.englishapp.Group.TeacherGroupActivity;
import tw.edu.cyut.englishapp.Group.TestGroupActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Haru on 2017/12/19.
 */

public class Backgorundwork extends AsyncTask<String,Void,String> {
    Context context;
    MaterialDialog.Builder alertDialog;
    public static final String KEY = "STATUS";
    String thisURL="http://140.122.63.99";
    private static final String ACTIVITY_TAG ="Logwrite";
    String Username;
    String Password;
    public Backgorundwork(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        String type =params[0];

        if(type.equals("login")){
            try {
                String username = params[1];
                Username=username;
                String pwd = params[2];
                Password=pwd;
                String login_url =thisURL+"/app/ajax_login.php";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("psw","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8");
                Log.d("POST_DATA", "doInBackground: "+post_data);



                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line=null;
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("register")){
            String username=params[1];
            String pwd=params[2];
            String name=params[3];
            String mail=params[4];
            String tbackground=params[5];
            String Learninghours=params[6];
            String testseries=params[7];
            String testname=params[8];
            String certification=params[9];
            String sex=params[10];
            String year=params[11];
            String month=params[12];
            String day=params[13];
            String country=params[14];
            String native_language=params[15];
            String learningyears=params[16];
            String o_language=params[17];
            String education=params[18];
            String learingmethod=params[19];

            String sign_url =thisURL+"/app/ajax_register.php";
            try {

                URL url = new URL(sign_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(mail,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+"&"+
                        URLEncoder.encode("names","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("education","UTF-8")+"="+URLEncoder.encode(education,"UTF-8")+"&"+
                        URLEncoder.encode("background","UTF-8")+"="+URLEncoder.encode(tbackground,"UTF-8")+"&"+
                        URLEncoder.encode("average_learning","UTF-8")+"="+URLEncoder.encode(Learninghours,"UTF-8")+"&"+
                        URLEncoder.encode("level_certification","UTF-8")+"="+URLEncoder.encode(testseries,"UTF-8")+"&"+
                        URLEncoder.encode("name_certification","UTF-8")+"="+URLEncoder.encode(testname,"UTF-8")+"&"+
                        URLEncoder.encode("certification","UTF-8")+"="+URLEncoder.encode(certification,"UTF-8")+"&"+
                        URLEncoder.encode("sex","UTF-8")+"="+URLEncoder.encode(sex,"UTF-8")+"&"+
                        URLEncoder.encode("birthyear","UTF-8")+"="+URLEncoder.encode(year,"UTF-8")+"&"+
                        URLEncoder.encode("birthmonth","UTF-8")+"="+URLEncoder.encode(month,"UTF-8")+"&"+
                        URLEncoder.encode("birthday","UTF-8")+"="+URLEncoder.encode(day,"UTF-8")+"&"+
                        URLEncoder.encode("country","UTF-8")+"="+URLEncoder.encode(country,"UTF-8")+"&"+
                        URLEncoder.encode("language","UTF-8")+"="+URLEncoder.encode(native_language,"UTF-8")+"&"+
                        URLEncoder.encode("y_learning","UTF-8")+"="+URLEncoder.encode(learningyears,"UTF-8")+"&"+
                        URLEncoder.encode("o_language","UTF-8")+"="+URLEncoder.encode(o_language,"UTF-8")+"&"+
                        URLEncoder.encode("method_learning","UTF-8")+"="+URLEncoder.encode(learingmethod,"UTF-8");

                        Log.d("POST_DATA", "doInBackground: "+post_data);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line=null;
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (type.equals("BuildTestData")){
            try {
                String uid = params[1];
                String connection_url =thisURL+"/app/buildtestdata.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8");
                Log.d("POST_DATA", "doInBackground: "+post_data);



                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line=null;
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (type.equals("InsertAns")){
            try {
                String uid = params[1];
                String tindex = params[2];
                String ans = params[3];
                String connection_url =thisURL+"/app/insert_ans.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("t_index","UTF-8")+"="+URLEncoder.encode(tindex,"UTF-8")+"&"+
                        URLEncoder.encode("ans","UTF-8")+"="+URLEncoder.encode(ans,"UTF-8");
                Log.d("POST_DATA", "doInBackground: "+post_data);



                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line=null;
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    @Override
    protected void onPreExecute() {
        alertDialog = new MaterialDialog.Builder(context);
        alertDialog.title("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //alertDialog.content(result).show();
        if(result==null){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        } else if (result.contains("login success")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(KEY , MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("Status" , true).apply();
            sharedPreferences.edit().putString("Username" , Username).apply();
            sharedPreferences.edit().putString("Password" ,Password ).apply();

            ((Activity)context).finish();
            if (result.contains("control")){
                //in control
                Intent ToControl=new Intent(context,ControlGroupActivity.class);
                context.startActivity(ToControl);
                ((Activity) context).finish();
            }else if (result.contains("test")){
                //in test
                Intent ToTest=new Intent(context,TestGroupActivity.class);
                context.startActivity(ToTest);
                ((Activity) context).finish();
            }else if(result.contains("teacher")){
                //in teacher
                Intent ToTeacher=new Intent(context,TeacherGroupActivity.class);
                context.startActivity(ToTeacher);
                ((Activity) context).finish();
            }
        }else if (result.contains("this is null")){
            Toast.makeText(context, "Not yet open for answer.", Toast.LENGTH_SHORT).show();
        }
        else if (result.contains("register success")){
            Intent toLogin=new Intent(context,LoginActivity.class);
            context.startActivity(toLogin);
            ((Activity)context).finish();
        }
        else if (result.contains("DOCTYPE")){
            Log.d("Result", "onPostExecute: "+result);
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
