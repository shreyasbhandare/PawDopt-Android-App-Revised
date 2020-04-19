package com.sbhandare.pawdopt.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbhandare.pawdopt.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragmentRoot extends Fragment {


    public FavoritesFragmentRoot() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites_fragment_root, container, false);
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.favorites_fragment_root, new FavoritesFragment(),"favoritesFragment")
                .commit();

        return view;
    }
}
