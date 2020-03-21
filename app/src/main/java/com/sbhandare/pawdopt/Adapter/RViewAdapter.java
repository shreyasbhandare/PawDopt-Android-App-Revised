package com.sbhandare.pawdopt.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<Pet> dataSet;


    public RViewAdapter(List<Pet> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pet_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPetName;
        TextView tvPetBreed;
        ImageView ivPetPhoto;
        CheckBox cbLike;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.tvPetName = itemView.findViewById(R.id.petName);
            this.tvPetBreed = itemView.findViewById(R.id.petBreed);
            this.ivPetPhoto = itemView.findViewById(R.id.petPhoto);
            this.cbLike = itemView.findViewById(R.id.petLike);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        TextView textViewPetName = viewHolder.tvPetName;
        TextView textViewPetBreed = viewHolder.tvPetBreed;
        ImageView imageViewPetPhoto = viewHolder.ivPetPhoto;
        CheckBox checkBoxPetLike = viewHolder.cbLike;

        textViewPetName.setText(dataSet.get(position).getName());
        textViewPetBreed.setText(dataSet.get(position).getBreed());
        Picasso.get().load(dataSet.get(position).getImage()).fit().into(imageViewPetPhoto);
    }
}

