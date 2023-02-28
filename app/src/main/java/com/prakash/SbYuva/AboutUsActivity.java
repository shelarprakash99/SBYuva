package com.prakash.SbYuva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {

    TextView tv_about_us1,tv_about_us2,tv_about_us3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_about_us, frameLayout);
        txt_menuTitle.setText("");
        txt_menuTitle2.setText(R.string.menu_slideshow);

        tv_about_us1 = (TextView)findViewById(R.id.tv_about_us1);
        tv_about_us2 = (TextView)findViewById(R.id.tv_about_us2);
        tv_about_us3 = (TextView)findViewById(R.id.tv_about_us3);


        tv_about_us1.setText(R.string.about_us_1);
        tv_about_us2.setText(R.string.about_us_2);
        tv_about_us3.setText(R.string.about_us_3);

    }
}
