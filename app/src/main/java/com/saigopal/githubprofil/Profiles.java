package com.saigopal.githubprofil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Profiles extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private com.saigopal.githubprofil.recycleViewAdapter recycleViewAdapter;
    private int PageNumber = 1;
    private ArrayList<ProfileModel> dataList = new ArrayList<>();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        final String Name = getIntent().getStringExtra("SearchName");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.loading);
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_search_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        assert Name != null;
        if (!Name.isEmpty())
        {
            if (Functions.isNetworkAvailable(Profiles.this)) {
                Profile_details profile = new Profile_details(Name, PageNumber);
                profile.execute();
            }
            else {
                Error("No Internet");
            }
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1))
                {
                    if (Functions.isNetworkAvailable(Profiles.this)) {
                        progressBar.setVisibility(View.VISIBLE);
                        PageNumber = PageNumber + 1;
                        Profile_details profile = new Profile_details(Name, PageNumber);
                        profile.execute();
                    }
                    else {
                            Error("No Internet");
                    }

                }
            }
        });

        recycleViewAdapter = new recycleViewAdapter(dataList,Profiles.this);
        recyclerView.setAdapter(recycleViewAdapter);

    }


    @SuppressLint("StaticFieldLeak")
    class Profile_details extends AsyncTask<String, Void, String> {

        static final String KEY_USER_ID = "login";
        static final String KEY_AVATAR_URL = "avatar_url";
        static final String KEY_URL = "url";

        String Name;
        public Profile_details(String name , int pageNumber) {
            Name = name;
            PageNumber = pageNumber;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            return Functions.Get("https://api.github.com/search/users?q="+Name+"&page="+PageNumber);
        }

        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.optJSONArray("items");
                if (Integer.parseInt(jsonResponse.optString("total_count")) == 0){
                    Error("Profile Not Found");
                }
                else if(jsonArray == null || jsonArray.length() == 0) {
                    Error("There are no more Profiles");
                } else {
                    actionBar.setTitle("Total Profiles : "+jsonResponse.optString("total_count"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ProfileModel model = new ProfileModel(jsonObject.optString(KEY_USER_ID),
                                jsonObject.optString(KEY_AVATAR_URL),jsonObject.optString(KEY_URL));
                        dataList.add(model);
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                }
                assert jsonArray != null;
                progressBar.setVisibility(View.GONE);

            }
            catch (JSONException ex){
                Log.d("JsonException : ", ex + "");
                Error("Error \nPlease try again later");
            } catch (Exception e) {
                Log.d("Exception ", e + "");
            }

        }

    }

    private void Error(String ErrorType)
    {
        Snackbar.make(progressBar,ErrorType,Snackbar.LENGTH_SHORT).show();
    }

}