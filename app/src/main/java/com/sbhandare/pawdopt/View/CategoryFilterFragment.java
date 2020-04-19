package com.sbhandare.pawdopt.View;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFilterFragment extends BottomSheetDialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.selection_cat) LinearLayoutCompat catBox;
    @BindView(R.id.selection_dog) LinearLayoutCompat dogBox;
    @BindView(R.id.selection_horse) LinearLayoutCompat horseBox;
    @BindView(R.id.selection_mouse) LinearLayoutCompat mouseBox;
    @BindView(R.id.selection_rabbit) LinearLayoutCompat rabbitBox;
    @BindView(R.id.cat_tv) AppCompatTextView catTv;
    @BindView(R.id.dog_tv) AppCompatTextView dogTv;
    @BindView(R.id.horse_tv) AppCompatTextView horseTv;
    @BindView(R.id.mouse_tv) AppCompatTextView mouseTv;
    @BindView(R.id.rabbit_tv) AppCompatTextView rabbitTv;
    @BindView(R.id.clear_categroy_filter) AppCompatImageView clearCatFilterIv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CategoryFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFilterFragment newInstance(String param1, String param2) {
        CategoryFilterFragment fragment = new CategoryFilterFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this,view);

        catBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(catTv.getText().toString());
                dismiss();
            }
        });

        dogBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(dogTv.getText().toString());
                dismiss();
            }
        });

        horseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(horseTv.getText().toString());
                dismiss();
            }
        });

        mouseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(mouseTv.getText().toString());
                dismiss();
            }
        });

        rabbitBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(rabbitTv.getText().toString());
                dismiss();
            }
        });

        clearCatFilterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(PawDoptUtil.NO_SELECTION);
                dismiss();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCategorySelected(PawDoptUtil.NO_SELECTION);
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

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mListener.onCategorySelected(PawDoptUtil.NO_SELECTION);
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
        void onCategorySelected(String category);
    }
}
