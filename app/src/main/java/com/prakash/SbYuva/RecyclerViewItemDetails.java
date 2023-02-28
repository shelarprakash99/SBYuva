package com.prakash.SbYuva;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RecyclerViewItemDetails extends RecyclerView.Adapter<RecyclerViewItemDetails.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageMac = new ArrayList<>();
    private ArrayList<String> mitemName = new ArrayList<>();
    private ArrayList<String> mitemprice = new ArrayList<>();
    private ArrayList<String> mitemDescription = new ArrayList<>();

    int[] myImageList = new int[]{R.drawable.img1_education, R.drawable.img2_socialwork,R.drawable.img3_kala,R.drawable.img4_sports,R.drawable.img5_culture,R.drawable.img6_medical,R.drawable.img7_agriculture,R.drawable.img8_environment, R.drawable.img9_sanskrutik,R.drawable.img10_bank,R.drawable.img11_home_appliences,R.drawable.img12_career,R.drawable.img13_advertise};

    private Activity mContext;

    public RecyclerViewItemDetails(Activity context, ArrayList<String> imageNames, ArrayList<String> imagesMac, ArrayList<String> itemName, ArrayList<String> itemprice, ArrayList<String> itemDescription) {
        mImageNames = imageNames;
        mImageMac = imagesMac;
        mitemName = itemName;
        mitemprice = itemprice;
        mitemDescription = itemDescription;
        mContext = context;
    }

    @Override
    public RecyclerViewItemDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_item, parent, false);
        RecyclerViewItemDetails.ViewHolder holder = new RecyclerViewItemDetails.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemDetails.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        String encodedString = mImageMac.get(position);
        Bitmap bitmap = StringToBitMap(encodedString);
        holder.img_item.setImageBitmap(bitmap);

        /*Glide.with(mContext)
                .load(mContext.getResources().getDrawable(myImageList[position]))
                .into(holder.imagesAddress);*/

        holder.imageName.setText(mImageNames.get(position));
        holder.tv_item_name.setText(mitemName.get(position));
        holder.tv_price.setText(mitemprice.get(position));
        holder.tv_description.setText(mitemDescription.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "DEVICE NAME: " + mImageNames.get(position));
                Toast.makeText(mContext, "DEVICE NAME: " + mImageNames.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomMessageDilaog(mContext,mImageNames.get(position),mitemName.get(position),"  You wish to buy "+mitemName.get(position)+"  ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_item;
        TextView imageName, tv_item_name,tv_price,tv_description;
        LinearLayout parentLayout;
        Button btn_buy;
        EditText edt_cust_name,edt_cust_number,edt_cust_address;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_buy = itemView.findViewById(R.id.btn_buy);
            imageName = itemView.findViewById(R.id.tv_product_name);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_item = itemView.findViewById(R.id.img_item);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
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

    public void showCustomMessageDilaog(final Activity context, final String ProductName, final String ItemName, String message) {

        final Dialog dialogBus = new Dialog(context);
        dialogBus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBus.setCancelable(false);
        dialogBus.setContentView(R.layout.custom_alertdialouge);
        dialogBus.show();

        final EditText edt_cust_name = (EditText) dialogBus.findViewById(R.id.edt_cust_name);
        final EditText edt_cust_number = (EditText) dialogBus.findViewById(R.id.edt_cust_number);
        final EditText edt_cust_address = (EditText) dialogBus.findViewById(R.id.edt_cust_address);
        TextView tv_msg = (TextView) dialogBus.findViewById(R.id.tv_msg);
        Button btn_ok = (Button) dialogBus.findViewById(R.id.btn_ok);
        Button btn_Cancel = (Button) dialogBus.findViewById(R.id.btnCancel);
        tv_msg.setText(Html.fromHtml(message));


        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (edt_cust_name.getText().toString() == null || edt_cust_name.getText().toString().isEmpty()){
                    edt_cust_name.setError("Required");
                }else if (edt_cust_number.getText().toString() == null || edt_cust_number.getText().toString().isEmpty()){
                    edt_cust_number.setError("Required");
                }else if (edt_cust_address.getText().toString() == null || edt_cust_address.getText().toString().isEmpty()){
                    edt_cust_address.setError("Required");
                }else{
                    //Server call
                    dialogBus.dismiss();
                    String Pname = ProductName;
                    String Iname = ItemName;
                    String Cname = edt_cust_number.getText().toString();
                    String Caddress = edt_cust_address.getText().toString();
                    String Cnumber = edt_cust_number.getText().toString();
                    String url = "http://sbyuvaapi.dmbidri.org/home/InsertOrderDetails?itemName="+Iname+"&productName="+Pname+"&customerName="+Cname+"&address="+Caddress+"&contactNumber="+Cnumber;
                    new PostMethodDemo().execute(url);
                }


                    //edt_cust_name,edt_cust_number,edt_cust_address;


//              editVehicleNumber.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);


            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBus.dismiss();
            }
        });

    }

    public class PostMethodDemo extends AsyncTask<String , Void ,String> {

        String server_response;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            try {
                String s = "Please wait..";
                SpannableString ss2 = new SpannableString(s);
                ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
                ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

                pd = new ProgressDialog(mContext);
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
                    JSONObject jsonObj = new JSONObject(server_response);
                    String Status = jsonObj.get("Status").toString();
                    String Msg = jsonObj.get("Msg").toString();
                    if (Status.equalsIgnoreCase("true") && Msg.equalsIgnoreCase("true")){
                        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("Response", "" + server_response);
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
