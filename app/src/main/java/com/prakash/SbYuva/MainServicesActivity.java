package com.prakash.SbYuva;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MainServicesActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mImageAddress = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_main_services, frameLayout);
        txt_menuTitle.setText(R.string.main_title);
        txt_menuTitle2.setText(R.string.sub_title);
        prepairList();
        initRecyclerView();

    }

    private void prepairList() {

        Resources res = getResources();
        String[] title =  res.getStringArray(R.array.titles);
        String[] description =  res.getStringArray(R.array.description);

        mNames = new ArrayList( Arrays.asList(title));
        mImageUrls = new ArrayList( Arrays.asList(description));

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerv_view);
        RecyclerViewAdapterMainServices adapter = new RecyclerViewAdapterMainServices(this, mNames, mImageUrls,mImageAddress);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
