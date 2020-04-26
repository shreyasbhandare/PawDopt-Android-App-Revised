package com.sbhandare.pawdopt.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sbhandare.pawdopt.Presenter.SignupFragmentPresenter;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Service.PawDoptValidationService;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment implements SignupFragmentPresenter.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.firstNameEdit) EditText fNameET;
    @BindView(R.id.lastNameEdit) EditText lNameET;
    @BindView(R.id.emailEdit) EditText emailET;
    @BindView(R.id.passwordEdit) EditText passET;
    @BindView(R.id.signUpBtn) Button signUpBtn;

    private ProgressDialog progDialog;

    private OnFragmentInteractionListener mListener;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this,view);

        SignupFragmentPresenter signupFragmentPresenter = new SignupFragmentPresenter(this, getContext());

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(fNameET.getText())
                        && TextUtils.isEmpty(lNameET.getText())
                        && TextUtils.isEmpty(emailET.getText())
                        && TextUtils.isEmpty(passET.getText())){
                    fNameET.setError("First Name is required");
                    lNameET.setError("Last Name is required");
                    emailET.setError("Email is required!");
                    passET.setError("Password is required!");
                }else if(!TextUtils.isEmpty(fNameET.getText())
                        && !TextUtils.isEmpty(lNameET.getText())
                        && !TextUtils.isEmpty(emailET.getText())
                        && !TextUtils.isEmpty(passET.getText())){
                    String fName = fNameET.getText().toString(); // length validation needed
                    String lName = lNameET.getText().toString(); // length validation needed
                    String email = emailET.getText().toString(); // email validation needed
                    String pass = passET.getText().toString();   // password validation needed

                    boolean isValidEmail = PawDoptValidationService.isValidEmail(email);
                    boolean isValidPass = PawDoptValidationService.isValidPassword(pass);
                    boolean isValidFName = PawDoptValidationService.isValidFirstName(fName);
                    boolean isValidLName = PawDoptValidationService.isValidLastName(lName);

                    if(!isValidEmail){
                        emailET.setError("Email not valid!");
                    }
                    if(!isValidPass){
                        passET.setError("Password not valid!");
                    }
                    if(!isValidFName){
                        fNameET.setError("First Name is not valid!");
                    }
                    if(!isValidLName){
                        lNameET.setError("Last Name is not valid");
                    }

                    if(isValidEmail && isValidPass && isValidFName && isValidLName){
                        showProgressDialog();
                        signupFragmentPresenter.signUpAndLogin(fName,lName,email,pass);
                    }
                }else{
                    if(TextUtils.isEmpty(fNameET.getText())){
                        fNameET.setError("First Name is required");
                    }
                    if(TextUtils.isEmpty(lNameET.getText())){
                        lNameET.setError("Last Name is required");
                    }
                    if(TextUtils.isEmpty(emailET.getText())){
                        emailET.setError("Email is required!");
                    }
                    if(TextUtils.isEmpty(passET.getText())){
                        passET.setError("Password is required!");
                    }
                }
            }
        });

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

    @Override
    public void loadMainActivity() {
        stopProgressDialog();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public void showSignupErrorToast(){
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopProgressDialog();
                Toast.makeText(getActivity(), "Sorry, can't Sign Up at this time!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog(){
        progDialog = ProgressDialog.show( getContext(), null, null, false, true );
        Objects.requireNonNull(progDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progDialog.setContentView(R.layout.progress_dialog);
    }

    private void stopProgressDialog(){
        progDialog.dismiss();
    }
}
