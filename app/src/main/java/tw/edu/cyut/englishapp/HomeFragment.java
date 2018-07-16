package tw.edu.cyut.englishapp;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.RecycleView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(v.getContext()));
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        LoadExams(v);



        return v;
    }

    private void LoadExams(final View v){
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        String url ="http://163.17.5.182/englishExamCase/App/SelectExam.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    byte[] u = response.getBytes(
                            "UTF-8");
                    response = new String(u, "UTF-8");
                    Log.d(TAG, "Response " + response);
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    List<Item> posts = new ArrayList<Item>();
                    posts = Arrays.asList(mGson.fromJson(response, Item[].class));
                    adapter = new RecyclerViewAdapter(v.getContext(), posts);
                    recyclerView.setAdapter(adapter);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }


}
