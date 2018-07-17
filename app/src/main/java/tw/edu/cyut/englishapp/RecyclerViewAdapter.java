package tw.edu.cyut.englishapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static tw.edu.cyut.englishapp.Backgorundwork.KEY;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    public List<Item> itemList;
    private Context context;

    String Status;

    String message;

    public static final String KEY = "STATUS";
    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    String uid;
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);

        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {



        holder.Title.setText(itemList.get(position).getTitle());

        holder.Time.setText(itemList.get(position).getUntil_at());

        holder.Type.setText(itemList.get(position).getType());
        final String type=itemList.get(position).getType();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+itemList.get(position).getTitle());
                //OPEN DETAIL
                SharedPreferences sharedPreferences = context.getSharedPreferences(KEY, MODE_PRIVATE);
                String username=sharedPreferences.getString("Username",null);
                LoadExams(username,itemList.get(position).getEid());

                normalDialogEvent(itemList.get(position).getTitle(),itemList.get(position).getContent(),Status,type);
            }
        });




        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {

                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    private void customDialog(final String count, final String title, final String data, final String message,final String time,final String until,final String pid,final String cid,final String uid){




    }


    public void normalDialogEvent(final String Title,final String Content,final String Status,final String type) {
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(context);
        dialog.title(Title);
        dialog.content(Content);
        dialog.positiveText("Start The Exam");
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                Log.d(TAG, "onClick: "+"Yes");
                if (Status.equals("完成考試")){
                    Toast.makeText(context,"You have finished this exam.",Toast.LENGTH_SHORT).show();
                }else{
                    switch (type){
                        case "Listen":
                            //TODO 聽力的部分
                            break;
                        case "Speak":
                            //TODO 口說的部份

                            break;
                    }
                }


            }
        });

        dialog.show();
    }



    public void LoadExams(final String username,final String exam_id){
        String url ="http://163.17.5.182/englishExamCase/App/Exam_Status.php";//TODO
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response:",response);
                        try {
                            byte[] u = response.getBytes(
                                    "UTF-8");
                            response = new String(u, "UTF-8");
                            Log.d(TAG, "Response " + response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<ItemStatus> posts = new ArrayList<ItemStatus>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemStatus[].class));
                            Status=posts.get(0).getStatus();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //do stuffs with response erroe
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("exam_id",exam_id);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }





}