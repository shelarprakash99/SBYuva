package com.prakash.SbYuva;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ItemDetails extends BaseActivity {

    private String url = "http://sbyuvaapi.dmbidri.org/home/GetItemDetails?productName=";
    ImageView img_item;
    TextView tv_product_name,tv_item_name,tv_price,tv_description;
    Button btn_back,btn_buy;
    private static final String TAG = "Item Details Activity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageAddress = new ArrayList<>();
    private ArrayList<String> mitemName = new ArrayList<>();
    private ArrayList<String> mitemprice = new ArrayList<>();
    private ArrayList<String> mitemDescription = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_details);
        View rootView = getLayoutInflater().inflate(R.layout.activity_item_details, frameLayout);
        txt_menuTitle.setText(R.string.product_details);
        txt_menuTitle2.setText("");

        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        if (!productName.isEmpty()){
            url = url+productName;
            prepairList();
        }

    }

    private void prepairList() {
        new GetMethodDemo().execute(url);
    }

    public class GetMethodDemo extends AsyncTask<String , Void ,String> {

        String server_response;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            try {
                String s = "Please wait..";
                SpannableString ss2 = new SpannableString(s);
                ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
                ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

                pd = new ProgressDialog(ItemDetails.this);
                pd.setMessage(ss2);
                pd.setCancelable(true);
                pd.setCancelable(false);
                pd.show();
            }catch (Exception e){
                pd.dismiss();
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", e.getMessage());
                e.printStackTrace();
                pd.dismiss();
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
                e.printStackTrace();
                pd.dismiss();
            }

            return server_response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                pd.dismiss();
                Log.e("Response", "" + server_response);

                if (server_response != null && !server_response.isEmpty()) {
                    //mNames.clear();mImageAddress.clear();//clear list
                    JSONArray jsonArr = new JSONArray(server_response);
                    Log.e("Response", "" + server_response);
                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String ImageencodedString = jsonObj.getString("Image");
                        String PRODUCT_NAME = jsonObj.getString("PRODUCT_NAME");
                        String ItemName = jsonObj.getString("ItemName");
                        String Price = jsonObj.getString("Price");
                        String Description = jsonObj.getString("Description");
                        //String Discounted_Price = jsonObj.getString("Discounted_Price");
                        //String Features = jsonObj.getString("Features");
                        //String color = jsonObj.getString("color");

                        mNames.add(PRODUCT_NAME);
                        mImageAddress.add(ImageencodedString);
                        mitemName.add(ItemName);
                        mitemprice.add(Price);
                        mitemDescription.add(Description);

                    }
                    Log.e("Response", "" + server_response);
                    initRecyclerView();
                }
            }catch (Exception e){
                e.printStackTrace();
                pd.dismiss();
            }

        }
    }

    @SuppressLint("LongLogTag")
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_item_view);
        RecyclerViewItemDetails adapter = new RecyclerViewItemDetails(this, mNames,mImageAddress,mitemName,mitemprice,mitemDescription);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //int numberOfColumns = 2;
        //recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

    }

    // Converting InputStream to String
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void BuyItem(String s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);

        builder.setTitle("Willing to buy item");
        builder.setMessage("Buy "+s);
        builder.setIcon(R.drawable.app_logo);
        //final AlertDialog dialog = builder.create();
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

         //finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
