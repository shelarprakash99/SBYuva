package com.prakash.SbYuva;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewProductDetails extends RecyclerView.Adapter<RecyclerViewProductDetails.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageMac = new ArrayList<>();
    int[] myImageList = new int[]{R.drawable.img1_education, R.drawable.img2_socialwork,R.drawable.img3_kala,R.drawable.img4_sports,R.drawable.img5_culture,R.drawable.img6_medical,R.drawable.img7_agriculture,R.drawable.img8_environment, R.drawable.img9_sanskrutik,R.drawable.img10_bank,R.drawable.img11_home_appliences,R.drawable.img12_career,R.drawable.img13_advertise};

    private Context mContext;

    public RecyclerViewProductDetails(Context context, ArrayList<String> imageNames, ArrayList<String> imagesMac) {
        mImageNames = imageNames;
        mImageMac = imagesMac;
        mContext = context;
    }

    @Override
    public RecyclerViewProductDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_product_details, parent, false);
        RecyclerViewProductDetails.ViewHolder holder = new RecyclerViewProductDetails.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewProductDetails.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        String encodedString = mImageMac.get(position);
        Bitmap bitmap = StringToBitMap(encodedString);
        holder.imagesAddress.setImageBitmap(bitmap);

        /*Glide.with(mContext)
                .load(mContext.getResources().getDrawable(myImageList[position]))
                .into(holder.imagesAddress);*/

        holder.imageName.setText(mImageNames.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "DEVICE NAME: " + mImageNames.get(position));
                //Toast.makeText(mContext, "DEVICE NAME: " + mImageNames.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext,ItemDetails.class);
                i.putExtra("productName",mImageNames.get(position));
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagesAddress;
        TextView imageName, image_mac;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.image_name);
            imagesAddress = itemView.findViewById(R.id.image_view);
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
}
