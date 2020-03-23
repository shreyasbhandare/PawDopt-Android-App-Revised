package com.sbhandare.pawdopt.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Presenter.FavoritesFragmentPresenter;
import com.sbhandare.pawdopt.Presenter.PawDoptPresenter;
import com.sbhandare.pawdopt.Presenter.SearchFragmentPresenter;
import com.sbhandare.pawdopt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_RESULT = 1;

    private Context context;
    private List<Pet> dataSet;
    private long totalResults;
    private PawDoptPresenter presenter;

    public RViewAdapter(Context context, List<Pet> data, long totalResults, PawDoptPresenter presenter) {
        this.context = context;
        this.dataSet = data;
        this.totalResults = totalResults;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pet_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_results_item, parent, false);
            return new ResultsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof ResultsViewHolder) {
            showResultsView((ResultsViewHolder) viewHolder, position);
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
        return dataSet.get(position) == null ? VIEW_TYPE_RESULT : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPetName;
        TextView tvPetBreed;
        ImageView ivPetPhoto;
        CheckBox cbLike;

        private ItemViewHolder(View itemView) {
            super(itemView);
            this.tvPetName = itemView.findViewById(R.id.petName);
            this.tvPetBreed = itemView.findViewById(R.id.petBreed);
            this.ivPetPhoto = itemView.findViewById(R.id.petPhoto);
            this.cbLike = itemView.findViewById(R.id.petLike);
        }
    }

    private class ResultsViewHolder extends RecyclerView.ViewHolder {

        TextView resultsTV;

        private ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsTV = itemView.findViewById(R.id.totalResultsTV);
        }
    }

    @SuppressLint("DefaultLocale")
    private void showResultsView(ResultsViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        TextView resultsTV = viewHolder.resultsTV;
        resultsTV.setText(String.format("%,d", totalResults));
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        TextView textViewPetName = viewHolder.tvPetName;
        TextView textViewPetBreed = viewHolder.tvPetBreed;
        ImageView imageViewPetPhoto = viewHolder.ivPetPhoto;
        CheckBox checkBoxPetLike = viewHolder.cbLike;

        textViewPetName.setText(dataSet.get(position).getName());
        textViewPetBreed.setText(dataSet.get(position).getBreed());
        Picasso.get().load(dataSet.get(position).getImage()).fit().into(imageViewPetPhoto);
        checkBoxPetLike.setChecked(false);
        if(presenter instanceof FavoritesFragmentPresenter) {
            checkBoxPetLike.setChecked(true);
            checkBoxPetLike.setClickable(false);
        }
        else if(presenter instanceof SearchFragmentPresenter) {
            checkBoxPetLike.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    presenter.addUserFavorite(dataSet.get(position), position);
                }
            });
        }
    }
}

