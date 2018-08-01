package tw.edu.cyut.englishapp;


import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.rey.material.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static tw.edu.cyut.englishapp.RecyclerViewAdapter.KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    String thisURL="http://140.122.63.99";
    public AccountFragment() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(KEY, MODE_PRIVATE);
        String username=sharedPreferences.getString("Username",null);

        view=v;
        LoadUser(username);


        return v;
    }
    public void LoadUser(final String username){
        String url =thisURL+"/App/LoadUserData.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response:",response);
                        try {
                            byte[] u = response.getBytes(
                                    "UTF-8");
                            response = new String(u, "UTF-8");
                            Log.d("LoadUser", "Response " + response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<ItemAccount> posts = new ArrayList<ItemAccount>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemAccount[].class));
                            final List<ItemAccount> itemList=posts;

                            final TextView textView_name=(TextView)view.findViewById(R.id.textView_name);
                            final TextView textView_username=(TextView)view.findViewById(R.id.textView_username);
                            final TextView textView_mail=(TextView)view.findViewById(R.id.textView_mail);
                            final TextView textView_password=(TextView)view.findViewById(R.id.textView_Password);

                            textView_name.setText(itemList.get(0).getNames());
                            textView_username.setText(itemList.get(0).getUsername());
                            textView_mail.setText(itemList.get(0).getEmail());
                            textView_password.setText(itemList.get(0).getPassword());

                            FloatingActionButton floatingActionButton=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
                            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog(itemList.get(0).getNames(),itemList.get(0).getPassword(),itemList.get(0).getEmail());
                                }
                            });

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
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }

    private void customDialog(final String name, final String password, final String email){
        boolean wrapInScrollView = true;

        final View item = LayoutInflater.from(view.getContext()).inflate(R.layout.alert_edit, null);

        MaterialDialog.Builder dialog =new MaterialDialog.Builder(view.getContext());
        dialog.customView(item,wrapInScrollView);
        dialog.backgroundColorRes(R.color.colorBackground);
        dialog.positiveText("UPDATE");
        dialog.negativeText("CANCEL");



        /*final EditText editText_phone = (EditText) item.findViewById(R.id.editText_phone);
        final EditText editText_area = (EditText) item.findViewById(R.id.editText_area);

        editText_phone.setText(phone, TextView.BufferType.EDITABLE);
       editText_area.setText(area, TextView.BufferType.EDITABLE);



        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                if (editText_phone.getText().toString().equals("")||editText_nickname.getText().toString().equals("")||editText_area.getText().toString().equals("")){

                }else if(!editText_phone.getText().toString().matches("[0][9][0-9]{8}")){

                }else{




                    Toast.makeText(getActivity(),"編輯成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                dialog.dismiss();
            }
        });

*/
        dialog.show();

    }


}
