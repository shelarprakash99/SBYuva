package com.prakash.SbYuva;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterOtherServices extends RecyclerView.Adapter<RecyclerViewAdapterOtherServices.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterOtherServices";
    private ArrayList<String> mServiceNames = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapterOtherServices(Context context, ArrayList<String> imageNames) {
        mServiceNames = imageNames;
        mContext = context;
    }

    @Override
    public RecyclerViewAdapterOtherServices.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_other, parent, false);
        RecyclerViewAdapterOtherServices.ViewHolder holder = new RecyclerViewAdapterOtherServices.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterOtherServices.ViewHolder holder, final int position) {

        holder.service_name.setText(mServiceNames.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d(TAG, "DEVICE NAME: " + mImageNames.get(position) + " \nDEVICE MAC: " + mImageMac.get(position));
                //Toast.makeText(mContext, "DEVICE NAME: " + mImageNames.get(position) + " \nDEVICE MAC: " + mImageMac.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mServiceNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView service_name;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
