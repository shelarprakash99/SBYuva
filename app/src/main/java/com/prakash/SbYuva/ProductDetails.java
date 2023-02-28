package com.prakash.SbYuva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;

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
import java.util.Arrays;

public class ProductDetails extends BaseActivity {

    private static final String TAG = "Product Details Activity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageAddress = new ArrayList<>();
    private String url = "http://sbyuvaapi.dmbidri.org/home/GetProductDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_product_details);
        View rootView = getLayoutInflater().inflate(R.layout.activity_product_details, frameLayout);
        txt_menuTitle.setText(R.string.product_details);
        txt_menuTitle2.setText("");
        prepairList();
        initRecyclerView();
    }

    private void prepairList() {

        new GetMethodDemo().execute(url); //httpurlconnection code

    }

    @SuppressLint("LongLogTag")
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product_view);
        RecyclerViewProductDetails adapter = new RecyclerViewProductDetails(this, mNames,mImageAddress);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

    }

    @SuppressLint("NewApi")
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

                pd = new ProgressDialog(ProductDetails.this);
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
                        String PRODUCT_NAME = jsonObj.getString("PRODUCT_NAME");
                        String IMAGE = jsonObj.getString("IMAGE");
                        System.out.println(jsonObj);
                        mNames.add(PRODUCT_NAME);
                        mImageAddress.add(IMAGE);
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
}
