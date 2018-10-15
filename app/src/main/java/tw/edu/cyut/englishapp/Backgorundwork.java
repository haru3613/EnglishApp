package tw.edu.cyut.englishapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import tw.edu.cyut.englishapp.Group.TeacherGroupActivity;
import tw.edu.cyut.englishapp.Group.TestPreTestActivity;
import tw.edu.cyut.englishapp.Group.group_control;
import tw.edu.cyut.englishapp.Group.RecordCheckActivity;
import tw.edu.cyut.englishapp.Group.TestGroupActivity;
import tw.edu.cyut.englishapp.Group.controldetail;

/**
 * Created by Haru on 2017/12/19.
 */

public class Backgorundwork extends AsyncTask<String,Void,String> {
    String FileURL;
    Context context;
    MaterialDialog.Builder alertDialog;
    String thisURL="http://140.122.63.99";
    String Username;
    String audio_list_long,index,today_finish;

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
                String correct_ans = params[4];
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
                        URLEncoder.encode("ans","UTF-8")+"="+URLEncoder.encode(ans,"UTF-8")+"&"+
                        URLEncoder.encode("correct_ans","UTF-8")+"="+URLEncoder.encode(correct_ans,"UTF-8");
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
        }else if (type.equals("Update user day")){
            try {
                String uid = params[1];
                String day = params[2];

                String connection_url =thisURL+"/app/update_user_day.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day,"UTF-8");
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
        }else if (type.equals("finish pretest")){
            try {
                String uid = params[1];
                String index = params[2];

                String connection_url =thisURL+"/app/update_topic_index.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(index,"UTF-8");
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
        }else if (type.equals("Insert topic check")){
            try {
                String uid = params[1];
                String filename = params[2];
                String correct_ans = params[3];
                String ans = params[4];

                String connection_url =thisURL+"/app/insert_checked_file.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("filename","UTF-8")+"="+URLEncoder.encode(filename,"UTF-8")+"&"+
                        URLEncoder.encode("correct_ans","UTF-8")+"="+URLEncoder.encode(correct_ans,"UTF-8")+"&"+
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
        }else if (type.equals("Delete Topic Speak")){
            try {
                String uid = params[1];
                String day = params[2];


                String connection_url =thisURL+"/app/delete_topic_speak.php";
                URL url = new URL(connection_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day,"UTF-8");
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
        }else if (type.equals("Upload_record")) {
            int serverResponseCode = 0;
            String result = null;
            final String SERVER_PATH = "http://140.122.63.99/record_mp3/file_upload.php";
            FileURL= params[1];
            String uid=params[2];
            index=params[3];
            String FileName= params[4];
            today_finish= params[5];
            Log.e("All params->", "uid->" + uid+"t_index->" +index+"FileName->" +FileName +"today_finish->"+today_finish);
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(FileURL);
            if (sourceFile.isFile()) {
                try {

                    FileInputStream fileInputStream = new FileInputStream(new File(FileURL));
                    URL url = new URL(SERVER_PATH);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "utf-8");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    dos = new DataOutputStream(conn.getOutputStream());


                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uid\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(URLEncoder.encode(uid,"UTF-8"));
                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"t_index\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(URLEncoder.encode(index,"UTF-8"));
                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"today_finish\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(URLEncoder.encode(today_finish,"UTF-8"));
                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"filename\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(URLEncoder.encode(FileName,"UTF-8"));
                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + FileURL + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);



                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    // close streams
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String u_result = "";
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        u_result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    conn.disconnect();
                    Log.e("Check_result", "result------>" + u_result);
                    return u_result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else if(type.equals("Upload_record")) {



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
        Log.d("Background", "onPostExecute: "+result);
        if(result==null){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        } else if (result.contains("login success")) {

            ((Activity)context).finish();
            if (result.contains("control")){
                //in control
                Intent ToControl=new Intent(context,group_control.class);
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
        }else if (result.contains("Congratulations on completing today's pre-test")){
            Intent ToTestGroup=new Intent(context,TestPreTestActivity.class);
            context.startActivity(ToTestGroup);
            ((Activity) context).finish();
            Toast.makeText(context, "Congratulations on completing today's pre-test!", Toast.LENGTH_SHORT).show();
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
        else if (result.contains("Upload_Success")){
            Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show();
            File file = new File(FileURL);
            if (file.exists()){
                file.delete();
                Log.d("Success ", "File was deleted"+result);
                if (today_finish.equals("0")){
                    Intent ToControl=new Intent(context,group_control.class);
                    context.startActivity(ToControl);
                    ((Activity) context).finish();
                }else{
                    Intent Totodayisfinish=new Intent(context,todayisfinish.class);
                    context.startActivity(Totodayisfinish);
                    ((Activity) context).finish();

                }

            }else{
                Log.d("Error ", "File doesn't exist"+result);
            }
        }
        else if (result.contains("Upload_Fail")){
            Toast.makeText(context, "Upload Fail", Toast.LENGTH_SHORT).show();
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
