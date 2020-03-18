package com.sbhandare.pawdopt.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private List<Pet> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPetName;
        TextView tvPetBreed;
        ImageView ivPetPhoto;
        CheckBox cbLike;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvPetName = itemView.findViewById(R.id.petName);
            this.tvPetBreed = itemView.findViewById(R.id.petBreed);
            this.ivPetPhoto = itemView.findViewById(R.id.petPhoto);
            this.cbLike = itemView.findViewById(R.id.petLike);
        }
    }

    public RVAdapter(List<Pet> data) {
        this.dataSet = data;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pet_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewPetName = holder.tvPetName;
        TextView textViewPetBreed = holder.tvPetBreed;
        ImageView imageViewPetPhoto = holder.ivPetPhoto;
        CheckBox checkBoxPetLike = holder.cbLike;

        textViewPetName.setText(dataSet.get(listPosition).getName());
        textViewPetBreed.setText(dataSet.get(listPosition).getBreed());
        Picasso.get().load(dataSet.get(listPosition).getImage()).into(imageViewPetPhoto);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
