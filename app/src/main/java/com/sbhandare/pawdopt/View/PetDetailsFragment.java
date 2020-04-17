package com.sbhandare.pawdopt.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbhandare.pawdopt.Model.Address;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Presenter.PetDetailsFragmentPresenter;
import com.sbhandare.pawdopt.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetDetailsFragment extends Fragment implements PetDetailsFragmentPresenter.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.pet_image) ImageView petPhotoIv;
    @BindView(R.id.name_txt) TextView nameTv;
    @BindView(R.id.age_txt) TextView ageTv;
    @BindView(R.id.gender_txt) TextView genderTv;
    @BindView(R.id.size_txt) TextView sizeTv;
    @BindView(R.id.breed_txt) TextView breedTv;
    @BindView(R.id.coat_txt) TextView coatTv;
    @BindView(R.id.color_txt) TextView colorTv;
    @BindView(R.id.breed_img) ImageView breedIv;
    @BindView(R.id.coat_img) ImageView coatIv;
    @BindView(R.id.color_img) ImageView colorIv;
    @BindView(R.id.vaccinated_txt) TextView vaccinatedTv;
    @BindView(R.id.spayed_txt) TextView spayedNeuterTv;
    @BindView(R.id.declaw_txt) TextView declawTv;
    @BindView(R.id.specialneeds_txt) TextView specNeedsTv;
    @BindView(R.id.vaccinated_img) ImageView vaccinatedIv;
    @BindView(R.id.spayed_img) ImageView spayedNeuterIv;
    @BindView(R.id.declaw_img) ImageView declawIv;
    @BindView(R.id.specialneeds_img) ImageView specNeedsIv;
    @BindView(R.id.house_train_txt) TextView houseTrainedTv;
    @BindView(R.id.good_dogs_txt) TextView goodWithDogsTv;
    @BindView(R.id.good_cats_txt) TextView goodWithCatsTv;
    @BindView(R.id.good_kids_txt) TextView goodWithKidsTv;
    @BindView(R.id.house_train_img) ImageView houseTrainedIv;
    @BindView(R.id.good_cats_img) ImageView goodWithCatsIv;
    @BindView(R.id.good_dogs_img) ImageView goodWithDogsIv;
    @BindView(R.id.good_kids_img) ImageView goodWithKidsIv;
    @BindView(R.id.know_more_name_txt) TextView bioNameTv;
    @BindView(R.id.bio_txt) TextView bioTv;
    @BindView(R.id.tag_txt) TextView tagsTv;
    @BindView(R.id.org_name_txt) TextView orgNameTv;
    @BindView(R.id.address_txt) TextView orgAddressTv;
    @BindView(R.id.email_txt) TextView orgEmailTv;
    @BindView(R.id.phone_txt) TextView orgPhoneTv;
    @BindView(R.id.org_photo) ImageView orgPhotoIv;
    @BindView(R.id.address_img) ImageView addressIv;
    @BindView(R.id.email_img) ImageView emailIv;
    @BindView(R.id.phone_img) ImageView phoneIv;
    @BindView(R.id.weblink_iv) ImageView weblinkIv;
    @BindView(R.id.fb_iv) ImageView fbIv;
    @BindView(R.id.twitter_iv) ImageView twitterIv;
    @BindView(R.id.insta_iv) ImageView instaIv;
    @BindView(R.id.youtube_iv) ImageView youtubeIv;
    @BindView(R.id.petLike) CheckBox likeCb;
    @BindView(R.id.ask_details_btn) Button askDetailsBtn;
    @BindView(R.id.no_data_txt) TextView noDataTv;
    @BindView(R.id.layout_pet_details) ConstraintLayout dataLayout;
    private ProgressDialog progDialog;

    private PetDetailsFragmentPresenter petDetailsFragmentPresenter;

    private int petId;

    private OnFragmentInteractionListener mListener;

    public PetDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetDetailsFragment newInstance(String param1, String param2) {
        PetDetailsFragment fragment = new PetDetailsFragment();
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
            petId = getArguments().getInt("petid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_details, container, false);
        ButterKnife.bind(this,view);
        petDetailsFragmentPresenter = new PetDetailsFragmentPresenter(this, getContext());

        progDialog = ProgressDialog.show( getContext(), null, null, false, true );
        Objects.requireNonNull(progDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progDialog.setContentView(R.layout.progress_dialog);
        petDetailsFragmentPresenter.populatePetDetails(petId);

        petPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openFullImage();
            }
        });

        weblinkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openWeblink();
            }
        });

        fbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openFb();
            }
        });

        twitterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openTwitter();
            }
        });

        instaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openInstagram();
            }
        });

        youtubeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.openYoutube();
            }
        });

        askDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDetailsFragmentPresenter.sendEmailWithBody();
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
    public void populateUI(Pet pet) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(pet != null){
                    if(!StringUtils.isEmpty(pet.getImage()))
                        Picasso.get().load(pet.getImage()).fit().centerCrop().into(petPhotoIv);
                    if(!StringUtils.isEmpty(pet.getName())) {
                        nameTv.setText(pet.getName());
                        bioNameTv.setText(pet.getName());
                        String askDetalsStr = "Ask about "+pet.getName();
                        askDetailsBtn.setText(askDetalsStr);
                    }
                    else
                        askDetailsBtn.setVisibility(View.GONE);
                    if(!StringUtils.isEmpty(pet.getAge()))
                        ageTv.setText(pet.getAge());
                    else
                        ageTv.setVisibility(View.GONE);
                    if(!StringUtils.isEmpty(pet.getGender()))
                        genderTv.setText(pet.getGender());
                    else
                        genderTv.setVisibility(View.GONE);
                    if(!StringUtils.isEmpty(pet.getSize()))
                        sizeTv.setText(pet.getSize());
                    else
                        sizeTv.setVisibility(View.GONE);
                    if(!StringUtils.isEmpty(pet.getBreed()))
                        breedTv.setText(pet.getBreed());
                    else {
                        breedTv.setVisibility(View.GONE);
                        breedIv.setVisibility(View.GONE);
                    }
                    if(!StringUtils.isEmpty(pet.getCoat()))
                        coatTv.setText(pet.getCoat());
                    else {
                        coatTv.setVisibility(View.GONE);
                        coatIv.setVisibility(View.GONE);
                    }
                    if(!StringUtils.isEmpty(pet.getColor()))
                        colorTv.setText(pet.getColor());
                    else {
                        colorTv.setVisibility(View.GONE);
                        colorIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsVaccinated().equals("N") || pet.getIsVaccinated().equals("D") || StringUtils.isEmpty(pet.getIsVaccinated())) {
                        vaccinatedTv.setVisibility(View.GONE);
                        vaccinatedIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsSpayedNeutered().equals("N") || pet.getIsSpayedNeutered().equals("D") || StringUtils.isEmpty(pet.getIsSpayedNeutered())) {
                        spayedNeuterTv.setVisibility(View.GONE);
                        spayedNeuterIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsDeclawed().equals("N") || pet.getIsDeclawed().equals("D") || StringUtils.isEmpty(pet.getIsDeclawed())) {
                        declawTv.setVisibility(View.GONE);
                        declawIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsSpecialNeeds().equals("N") || pet.getIsSpecialNeeds().equals("D") || StringUtils.isEmpty(pet.getIsSpecialNeeds())) {
                        specNeedsTv.setVisibility(View.GONE);
                        specNeedsIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsHouseTrained().equals("N") || pet.getIsHouseTrained().equals("D") || StringUtils.isEmpty(pet.getIsHouseTrained())) {
                        houseTrainedTv.setVisibility(View.GONE);
                        houseTrainedIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsGoodWithCats().equals("N") || pet.getIsGoodWithCats().equals("D") || StringUtils.isEmpty(pet.getIsGoodWithCats())) {
                        goodWithCatsTv.setVisibility(View.GONE);
                        goodWithCatsIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsGoodWithDogs().equals("N") || pet.getIsGoodWithDogs().equals("D") || StringUtils.isEmpty(pet.getIsGoodWithDogs())) {
                        goodWithDogsTv.setVisibility(View.GONE);
                        goodWithDogsIv.setVisibility(View.GONE);
                    }
                    if(pet.getIsGoodWithChildren().equals("N") || pet.getIsGoodWithChildren().equals("D") || StringUtils.isEmpty(pet.getIsGoodWithChildren())) {
                        goodWithKidsTv.setVisibility(View.GONE);
                        goodWithKidsIv.setVisibility(View.GONE);
                    }
                    if(!StringUtils.isEmpty(pet.getBio()))
                        bioTv.setText(pet.getBio());
                    else
                        bioTv.setVisibility(View.GONE);
                    if(!StringUtils.isEmpty(pet.getTags()))
                        tagsTv.setText(pet.getTags());
                    else
                        tagsTv.setVisibility(View.GONE);
                    if(pet.getOrganization()!=null){
                        Organization organization = pet.getOrganization();
                        if(!StringUtils.isEmpty(organization.getName()))
                            orgNameTv.setText(organization.getName());
                        if(!StringUtils.isEmpty(organization.getEmail()))
                            orgEmailTv.setText(organization.getEmail());
                        else{
                            orgEmailTv.setVisibility(View.GONE);
                            emailIv.setVisibility(View.GONE);
                        }
                        if(!StringUtils.isEmpty(organization.getPhone()))
                            orgPhoneTv.setText(organization.getPhone());
                        else{
                            orgPhoneTv.setVisibility(View.GONE);
                            phoneIv.setVisibility(View.GONE);
                        }
                        if(!StringUtils.isEmpty(organization.getImage()))
                            Picasso.get().load(organization.getImage()).fit().centerCrop().into(orgPhotoIv);
                        else
                            orgPhotoIv.setVisibility(View.GONE);
                        if(organization.getAddress()!=null){
                            Address address = organization.getAddress();
                            StringBuilder addressSb = new StringBuilder();
                            if(!StringUtils.isEmpty(address.getStreet1()))
                                addressSb.append(address.getStreet1()).append(", ");
                            if(!StringUtils.isEmpty(address.getStreet2()))
                                addressSb.append(address.getStreet2()).append(", ");
                            if(!StringUtils.isEmpty(address.getCity()))
                                addressSb.append(address.getCity()).append(", ");
                            if(!StringUtils.isEmpty(address.getState()))
                                addressSb.append(address.getState());
                            orgAddressTv.setText(addressSb.toString());
                        }else {
                            orgAddressTv.setVisibility(View.GONE);
                            addressIv.setVisibility(View.GONE);
                        }
                        if(StringUtils.isEmpty(organization.getWebLink()))
                            weblinkIv.setVisibility(View.GONE);
                        if(StringUtils.isEmpty(organization.getFbLink()))
                            fbIv.setVisibility(View.GONE);
                        if(StringUtils.isEmpty(organization.getTwitterLink()))
                            twitterIv.setVisibility(View.GONE);
                        if(StringUtils.isEmpty(organization.getInstaLink()))
                            instaIv.setVisibility(View.GONE);
                        if(StringUtils.isEmpty(organization.getYoutubeLink()))
                            youtubeIv.setVisibility(View.GONE);
                    }
                }
                progDialog.dismiss();
            }
        });
    }

    @Override
    public void populateDataNotFound() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataLayout.setVisibility(View.GONE);
                noDataTv.setVisibility(View.VISIBLE);
                progDialog.dismiss();
            }
        });
    }

    @Override
    public void populateDialog(String imgUrl) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
                LayoutInflater factory = LayoutInflater.from(getContext());
                final View view = factory.inflate(R.layout.layout_full_pet_image, null);
                ImageView imageView = view.findViewById(R.id.dialog_imageview);
                /*
                int ht = PawDoptImageUtil.getImgHt(imgUrl);
                int wd = PawDoptImageUtil.getImgWd(imgUrl);
                imageView.getLayoutParams().width = (int) PawDoptImageUtil.dpFromPx(getContext(), ht);
                imageView.getLayoutParams().height = (int) PawDoptImageUtil.dpFromPx(getContext(), wd);
                */
                Picasso.get().load(imgUrl).fit().centerInside().into(imageView);
                alertadd.setView(view);
                alertadd.show();
            }
        });
    }

    @Override
    public void openEmail(boolean hasBody, String toEmail, String subject, String body) {
        if(hasBody){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:"+toEmail+"?subject=" + subject + "&body=" + body);
            intent.setData(data);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:"+toEmail);
            intent.setData(data);
            startActivity(intent);
        }
    }

    @Override
    public void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
