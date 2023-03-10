package com.prakash.SbYuva;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected FrameLayout frameLayout;
    private Context context;
    private Toolbar toolbar;
    public TextView txt_menuTitle,txt_menuTitle2, txt_username, txt_email,txt_card_value;
    public ImageView img_menuOption, image_profile,img_menu_add_cart;
    public FloatingActionButton img_menu_prac_test;
    public static ArrayList<String> DetailsNames = new ArrayList<>();
    public static ArrayList<String> DetailsAddress = new ArrayList<>();

    private DrawerLayout drawer;
    private static final int INTENT_REQUEST_CODE = 200;
    private int INTENT_CAMERA_CODE = 100;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context=this;
        initView();
        frameLayout = (FrameLayout) findViewById(R.id.container);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        txt_menuTitle = findViewById(R.id.txt_menuTitle);
        txt_menuTitle2 = findViewById(R.id.txt_menuTitle2);
        //img_menu_prac_test = findViewById(R.id.img_menu_prac_test);
        img_menuOption = findViewById(R.id.img_menuOption);


        img_menuOption.setBackgroundResource(R.drawable.ic_menu);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        image_profile = headerview.findViewById(R.id.image_profile);
        txt_username = headerview.findViewById(R.id.txt_username);
        txt_email = headerview.findViewById(R.id.txt_email);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        img_menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            //ExitApp();
        }
    }
    private void ExitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Code Inside Coffee");
        builder.setMessage("Do You Want To Exit?");
        builder.setIcon(R.drawable.ic_icon);
        //final AlertDialog dialog = builder.create();
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_home) {
            //Toast.makeText(context, "You Click On Home", Toast.LENGTH_SHORT).show();
            intent=new Intent(this, MainServicesActivity.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
           // Toast.makeText(context, "You Click On Galary", Toast.LENGTH_SHORT).show();
            intent=new Intent(this, OtherServicesActivity.class);
            startActivity(intent);
            finish();


        } else if (id == R.id.nav_slideshow) {
            //Toast.makeText(context, "You Click On SlideShow", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BaseActivity.this,AboutUsActivity.class);
            this.startActivity(i);

        } else if (id == R.id.nav_tools) {
            //Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BaseActivity.this,Contact.class);
            this.startActivity(i);
        } else if (id == R.id.nav_product_details) {
            //Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BaseActivity.this,ProductDetails.class);
            this.startActivity(i);
        } else if (id == R.id.nav_share) {

            Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
