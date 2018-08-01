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
    Backgorundwork(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        Log.d(Backgorundwork.ACTIVITY_TAG,"Let's run~~~~");
        String type =params[0];
        String login_url =thisURL+"/ajax_login.php";
        if(type.equals("login")){
            Log.d(Backgorundwork.ACTIVITY_TAG,"login if run");
            try {
                String username = params[1];
                Username=username;
                String pwd = params[2];
                Password=pwd;
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


            String sign_url =thisURL+"/ajax_register.php";
            try {

                URL url = new URL(sign_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("u_mail","UTF-8")+"="+URLEncoder.encode(mail,"UTF-8")+"&"+
                        URLEncoder.encode("u_pwd","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+"&"+
                        URLEncoder.encode("u_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("u_nickname","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

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
            Intent toMainActivity=new Intent(context,MainActivity.class);
            context.startActivity(toMainActivity);
            ((Activity)context).finish();
        }else if (result.contains("Password is not match.")){
            Toast.makeText(context, "Password is not match.", Toast.LENGTH_SHORT).show();
        }
        else if (result.contains("register success")){
            Intent toLogin=new Intent(context,LoginActivity.class);
            context.startActivity(toLogin);
            ((Activity)context).finish();
        }else if (result.contains("Insert Error, please notify managers or try again.")){
            Toast.makeText(context, "Insert Error, please notify managers or try again.", Toast.LENGTH_SHORT).show();
        }else if (result.contains("This account has already been registered")){
            Toast.makeText(context, "This account has already been registered", Toast.LENGTH_SHORT).show();
        }else if (result.contains("This account has been banned from use.")){
            Toast.makeText(context, "This account has been banned from use.", Toast.LENGTH_SHORT).show();
        }else if (result.contains("This account has not been registered.")){
            Toast.makeText(context, "This account has not been registered.", Toast.LENGTH_SHORT).show();
        }else if(result.contains("接案成功")){
            Toast.makeText(context, "接案成功", Toast.LENGTH_SHORT).show();
        }
        else if (result.contains("DOCTYPE")){
            Log.d("Result", "onPostExecute: "+result);
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("Result", "onPostExecute: "+result);
        }

    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
