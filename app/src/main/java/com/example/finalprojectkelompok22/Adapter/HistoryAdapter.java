package com.example.finalprojectkelompok22.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectkelompok22.Model.Attendance;
import com.example.finalprojectkelompok22.R;


import java.util.List;

import javax.annotation.Nonnull;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Attendance> attendanceList;

    public HistoryAdapter(List<Attendance> attendanceList) {this.attendanceList = attendanceList;}

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nonnull ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);

        holder.nameTextView.setText(attendance.getName());
        holder.statusTextView.setText("Status: " + attendance.getStatus());
        holder.locationTextView.setText("Lokasi "+ attendance.getLokasi());
        holder.timeTextView.setText("Waktu: "+ attendance.getWaktukehadiran());

        if(attendance.getFotoBase64() != null) {
            byte[] decodedString = Base64.decode(attendance.getFotoBase64(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.photoImageView.setImageBitmap(decodedByte);
        } else {
            holder.photoImageView.setImageResource(R.drawable.ic_placeholder_photo);
        }
    }

    @Override
    public int getItemCount() {return attendanceList.size();}

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public ImageView photoImageView;
        public TextView nameTextView;
        public TextView statusTextView;
        public TextView locationTextView;
        public TextView timeTextView;

        public ViewHolder(@Nonnull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
