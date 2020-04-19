package com.sbhandare.pawdopt.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sbhandare.pawdopt.Adapter.EndlessRecyclerViewScrollListener;
import com.sbhandare.pawdopt.Adapter.RViewGridAdapter;
import com.sbhandare.pawdopt.Component.PawDoptToast;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Presenter.SearchFragmentPresenter;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.apache.commons.lang3.StringUtils;

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
    private RViewGridAdapter rViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private Button petDistanceBtn;
    private Button petCategoryBtn;
    private AppCompatImageView filterBtn;
    private SearchFragmentPresenter searchFragmentPresenter;
    private ProgressDialog progDialog;

    private FilterFragment filterFragment;
    private DistanceFilterFragment distanceFilterFragment;
    private CategoryFilterFragment categoryFilterFragment;

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

        initUIElements(view);
        searchFragmentPresenter = new SearchFragmentPresenter(this,getContext());

        progDialog = ProgressDialog.show( getContext(), null, null, false, true );
        Objects.requireNonNull(progDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progDialog.setContentView(R.layout.progress_dialog);
        searchFragmentPresenter.populatePetList();

        petDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDistanceBtn.setBackgroundResource(R.drawable.bg_primary_filter_btn_inverted);
                distanceFilterFragment = new DistanceFilterFragment();
                distanceFilterFragment.show(Objects.requireNonNull(getFragmentManager()),"distFiler");
            }
        });

        petCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petCategoryBtn.setBackgroundResource(R.drawable.bg_primary_filter_btn_inverted);
                categoryFilterFragment = new CategoryFilterFragment();
                categoryFilterFragment.show(Objects.requireNonNull(getFragmentManager()),"catFilter");
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFragment = new FilterFragment();
                filterFragment.show(Objects.requireNonNull(getFragmentManager()),"filter");
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                searchFragmentPresenter.getMorePets();
            }
        };

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
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (rViewAdapter.getItemViewType(position) == 1) {
                    return 2;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void populateRV(List<Pet> petList, long totalResults) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rViewAdapter = new RViewGridAdapter(getContext(), petList, totalResults, searchFragmentPresenter);
                recyclerView.setAdapter(rViewAdapter);
                petCategoryBtn.setVisibility(View.VISIBLE);
                petDistanceBtn.setVisibility(View.VISIBLE);
                filterBtn.setVisibility(View.VISIBLE);
                progDialog.dismiss();
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

    @Override
    public void removeFavoriteFromRV(String name, int pos, int size) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PawDoptToast.showFavoritesToast(getContext(), name);
            }
        });

        rViewAdapter.notifyItemRemoved(pos);
        rViewAdapter.notifyItemRangeChanged(pos, size);
    }

    void onDistanceSelected(String distance){
        if(!StringUtils.equals(distance, PawDoptUtil.NO_SELECTION)) {
            petDistanceBtn.setText(distance);
        }
        petDistanceBtn.setBackgroundResource(R.drawable.bg_primary_filter_btn_straight);
    }

    void onCategorySelected(String category){
        if(!StringUtils.equals(category, PawDoptUtil.NO_SELECTION)) {
            petCategoryBtn.setText(category);
        }
        petCategoryBtn.setBackgroundResource(R.drawable.bg_primary_filter_btn_straight);
    }
}
