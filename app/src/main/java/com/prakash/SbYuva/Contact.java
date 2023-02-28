package com.prakash.SbYuva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Contact extends BaseActivity {

    TextView tv_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_contact, frameLayout);
        txt_menuTitle.setText("");
        txt_menuTitle2.setText(R.string.menu_tools);

        tv_contact = (TextView)findViewById(R.id.tv_contact_us);
        tv_contact.setText(R.string.contact_us);
    }
}
