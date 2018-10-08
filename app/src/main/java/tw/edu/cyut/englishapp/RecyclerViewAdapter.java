package tw.edu.cyut.englishapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.model.Item;
import tw.edu.cyut.englishapp.model.ItemStatus;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    public List<Item> itemList;
    private Context context;
    String thisURL="http://140.122.63.99";
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
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {



        holder.Title.setText(itemList.get(position).getTitle());



        holder.Type.setText(itemList.get(position).getType());
        final String type=itemList.get(position).getType();





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+itemList.get(position).getTitle());
                //OPEN DETAIL
                SharedPreferences sharedPreferences = context.getSharedPreferences(KEY, MODE_PRIVATE);
                String username=sharedPreferences.getString("Username",null);
                LoadExamsStatus(username,itemList.get(position).getEid(),holder);

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
                        case "AExam":


                            break;
                        case "BExam":


                            break;
                        case "CExam":


                            break;
                        case "DExam":


                            break;
                        case "EExam":


                            break;
                        case "FExam":


                            break;
                        case "GExam":


                            break;
                        case "HExam":


                            break;
                        case "IExam":


                            break;
                        case "JExam":


                            break;
                        case "KExam":


                            break;
                        case "LExam":


                            break;
                        case "MExam":


                            break;
                        case "NExam":


                            break;
                        case "OExam":


                            break;
                        case "PExam":


                            break;
                        case "QExam":


                            break;
                        case "RExam":


                            break;
                        case "SExam":


                            break;
                        case "TExam":


                            break;
                        case "UExam":


                            break;
                        case "VExam":


                            break;


                    }
                }


            }
        });

        dialog.show();
    }



    public void LoadExamsStatus(final String username,final String exam_id,final RecyclerViewHolders holder){

        String url =thisURL+"/App/Exam_Status.php";
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
                            holder.Status.setText(Status);
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