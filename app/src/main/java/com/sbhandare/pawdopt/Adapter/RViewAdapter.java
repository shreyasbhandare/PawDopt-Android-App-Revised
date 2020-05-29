package com.sbhandare.pawdopt.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Presenter.FavoritesFragmentPresenter;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Util.PawDoptUtil;
import com.sbhandare.pawdopt.View.PetDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_RESULT = 1;

    private Context context;
    private List<Pet> dataSet;
    private long totalResults = 0;
    private FavoritesFragmentPresenter presenter;

    public RViewAdapter(Context context, List<Pet> data, long totalResults, FavoritesFragmentPresenter presenter) {
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

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPetName;
        TextView tvPetBreed;
        TextView tvPetDistance;
        ImageView ivPetPhoto;
        CheckBox cbLike;

        private ItemViewHolder(View itemView) {
            super(itemView);
            this.tvPetName = itemView.findViewById(R.id.petName);
            this.tvPetBreed = itemView.findViewById(R.id.petBreed);
            this.tvPetDistance = itemView.findViewById(R.id.petDistance);
            this.ivPetPhoto = itemView.findViewById(R.id.petPhoto);
            this.cbLike = itemView.findViewById(R.id.petLike);
        }
    }

    private static class ResultsViewHolder extends RecyclerView.ViewHolder {

        TextView resultsTV;
        TextView resultsLabelTV;

        private ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsTV = itemView.findViewById(R.id.totalResultsTV);
            resultsLabelTV = itemView.findViewById(R.id.totalResultsLabelTV);
        }
    }

    @SuppressLint("DefaultLocale")
    private void showResultsView(ResultsViewHolder viewHolder, int position) {
        TextView resultsTV = viewHolder.resultsTV;
        TextView resultsLabelTV = viewHolder.resultsLabelTV;
        resultsTV.setText(String.format("%,d", totalResults));
        resultsLabelTV.setText(PawDoptUtil.RESULTS_LABEL_FAVORITES);
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        TextView textViewPetName = viewHolder.tvPetName;
        TextView textViewPetBreed = viewHolder.tvPetBreed;
        TextView textViewPetDistance = viewHolder.tvPetDistance;
        ImageView imageViewPetPhoto = viewHolder.ivPetPhoto;
        CheckBox checkBoxPetLike = viewHolder.cbLike;

        textViewPetName.setText(dataSet.get(position).getName());
        textViewPetBreed.setText(dataSet.get(position).getBreed());
        Picasso.get().load(dataSet.get(position).getImage()).fit().centerCrop().into(imageViewPetPhoto);
        String posTxt = dataSet.get(position).getDistance()<=0 ? "" : dataSet.get(position).getDistance()+" mi";
        textViewPetDistance.setText(posTxt);
        checkBoxPetLike.setChecked(false);

        checkBoxPetLike.setChecked(true);
        checkBoxPetLike.setClickable(false);

        viewHolder.itemView.setOnClickListener(v -> {
            checkBoxPetLike.setStateListAnimator(null);
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            PetDetailsFragment petDetailsFragment = new PetDetailsFragment();
            Bundle args = new Bundle();
            args.putLong("petid", dataSet.get(position).getPetid());
            args.putInt("pos", position);
            args.putBoolean("isFav", true);
            args.putLong("dist", dataSet.get(position).getDistance());
            petDetailsFragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .add(R.id.favorites_fragment_root, petDetailsFragment)
                    .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public void incrementTotalResults(){
        ++totalResults;
    }
}

