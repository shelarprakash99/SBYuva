package com.prakash.SbYuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    //private String url = "http://sbyuvaapi.dmbidri.org/home/GetProductDetails";
    private String url = "http://sbyuvaapi.dmbidri.org/home/GetItemDetails?productName=AGRICULTURE";

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //new GetMethodDemo().execute(url); //httpurlconnection code
        //new getData().execute(); //okhttp code
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainServicesActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    public class getData extends AsyncTask<Void, Void, String> {


        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            String s = "Please wait..";
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

            pd = new ProgressDialog(SplashActivity.this);
            pd.setMessage(ss2);
            pd.setCancelable(true);
            pd.setCancelable(false);
            pd.show();


        }

        protected String doInBackground(Void... arg0) {
            String resp = "";

            try {
                OkHttpClient client = new OkHttpClient();
                client.setConnectTimeout(14, TimeUnit.SECONDS);
                client.setReadTimeout(14, TimeUnit.SECONDS);
                client.setWriteTimeout(14, TimeUnit.SECONDS);

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                resp = response.body().string();

                //------------------------------

            } catch (Exception e) {
                pd.dismiss();
                System.out.println("Ex" + e.getMessage());
            }

            return resp;
        }


        @Override
        protected void onPostExecute(String result) {

            try {
                pd.dismiss();
                System.out.println("getData...." + result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

                pd = new ProgressDialog(SplashActivity.this);
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

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                pd.dismiss();
                Log.e("Response", "" + server_response);
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
