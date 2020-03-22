package com.sbhandare.pawdopt.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sbhandare.pawdopt.Adapter.EndlessRecyclerViewScrollListener;
import com.sbhandare.pawdopt.Adapter.RVAdapter;
import com.sbhandare.pawdopt.Adapter.RViewAdapter;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Presenter.SearchFragmentPresenter;
import com.sbhandare.pawdopt.R;

import org.michaelbel.bottomsheet.BottomSheet;
import org.michaelbel.bottomsheet.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchFragmentPresenter.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EndlessRecyclerViewScrollListener scrollListener;
    private static RViewAdapter rViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private Button petDistanceBtn;
    private Button petCategoryBtn;
    private AppCompatImageView filterBtn;
    private SearchFragmentPresenter searchFragmentPresenter;

    private boolean isLoading = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inflate the layout for this fragment
        initUIElements(view);
        searchFragmentPresenter = new SearchFragmentPresenter(this,getContext());
        searchFragmentPresenter.populatePetList();

        String[] distance_array = getResources().getStringArray(R.array.distance_array);
        String[] categoy_text_array = getResources().getStringArray(R.array.pettype_array);

        petDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
                builder.setTitle(R.string.distanceTitle);
                builder.setDarkTheme(false);
                builder.setItems(distance_array,(dialogInterface, i) -> {
                    petDistanceBtn.setText(distance_array[i]);
                });
                builder.setDividers(false);
                builder.setFullWidth(false);
                builder.setItemTextColor(ResourcesCompat.getColor(getResources(), R.color.appPrimaryGreenColor, null));
                builder.show();
            }
        });

        petCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
                builder.setTitle(R.string.categoryTitle);
                builder.setDarkTheme(false);
                builder.setItems(categoy_text_array,(dialogInterface, i) -> {
                    petCategoryBtn.setText(categoy_text_array[i]);
                });
                builder.setDividers(false);
                builder.setFullWidth(false);
                builder.setItemTextColor(ResourcesCompat.getColor(getResources(), R.color.appPrimaryGreenColor, null));
                builder.show();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment filterFragment = new FilterFragment();
                filterFragment.show(getFragmentManager(),"filter");
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                searchFragmentPresenter.getMorePets();
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initUIElements(View view){
        petCategoryBtn = view.findViewById(R.id.petTypeBtn);
        petDistanceBtn = view.findViewById(R.id.petDistaceBtn);
        filterBtn = view.findViewById(R.id.filterImg);

        recyclerView = view.findViewById(R.id.petSearchListRV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void populateRV(List<Pet> petList, long totalResults) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rViewAdapter = new RViewAdapter(petList, totalResults);
                recyclerView.setAdapter(rViewAdapter);
            }
        });
    }

    @Override
    public void updateRV() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
