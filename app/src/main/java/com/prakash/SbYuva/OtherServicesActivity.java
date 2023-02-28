package com.prakash.SbYuva;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OtherServicesActivity extends BaseActivity {

    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_other_services, frameLayout);
        txt_menuTitle.setText("");
        txt_menuTitle2.setText(R.string.menu_gallery);

        prepairList();
        initRecyclerView();

    }

    private void prepairList() {

        Resources res = getResources();
        String[] title =  res.getStringArray(R.array.other_titles);

        mNames = new ArrayList( Arrays.asList(title));
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerv_view_other_services);
        RecyclerViewAdapterOtherServices adapter = new RecyclerViewAdapterOtherServices(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
