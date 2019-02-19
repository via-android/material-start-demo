package com.morepranit.materialstartdemo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.morepranit.materialstartdemo1.R;
import com.morepranit.materialstartdemo1.models.PhotoModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhotoModel> photoModels;

    public PhotoAdapter(Context context, ArrayList<PhotoModel> photoModels) {
        this.context = context;
        this.photoModels = photoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhotoModel model = photoModels.get(position);
        holder.txtTitle.setText(model.getTitle());
        Glide.with(context)
                .load(model.getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
