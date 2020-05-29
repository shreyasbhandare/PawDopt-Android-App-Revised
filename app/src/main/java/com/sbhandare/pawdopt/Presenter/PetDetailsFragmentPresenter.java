package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;
import com.sbhandare.pawdopt.Service.PawDoptURLBuilder;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class PetDetailsFragmentPresenter {
    private View view;
    private Context context;
    private OkhttpProcessor okhttpProcessor;
    private Pet pet;
    private long distance;
    private String token;

    public PetDetailsFragmentPresenter(View view, Context context, long distance){
        this.view = view;
        this.context = context;
        this.distance = distance;
        this.okhttpProcessor = new OkhttpProcessor();
        this.token = context.getSharedPreferences(PawDoptUtil.PAWDOPT_SHARED_PREFS, MODE_PRIVATE).getString("access_token","");
    }

    public void populatePetDetails(long petId, boolean isFav){
        if(token != null) {
            String url = PawDoptURLBuilder.buildPetDetailsURL(petId, token);

            okhttpProcessor.post(url,"", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("failure");
                    view.populateDataNotFound();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            pet = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(),Pet.class);
                            if(pet!=null) {
                                pet.setDistance(distance);
                                pet.setCurrentUserFav(isFav);
                                view.populateUI(pet);
                            }
                            else
                                view.populateDataNotFound();
                        }
                    } else {
                        System.out.println("no success");
                        view.populateDataNotFound();
                    }
                }
            });
        }
    }

    public void openFullImage(){
        if(pet.getImage()!=null)
            view.populateDialog(pet.getImage());
    }

    public void sendEmailWithBody(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getEmail()))
            view.openEmail(true,pet.getOrganization().getEmail(),"Checking in about "+pet.getName(),"Hello, is "+pet.getName()+" available for adoption?");
    }

    public void openWeblink(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getWebLink())) {
            String url = pet.getOrganization().getWebLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            view.openUrl(url);
        }
    }

    public void openFb(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getFbLink())) {
            String url = pet.getOrganization().getFbLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            view.openUrl(url);
        }
    }

    public void openTwitter(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getTwitterLink())) {
            String url = pet.getOrganization().getTwitterLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            view.openUrl(url);
        }
    }

    public void openInstagram(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getInstaLink())) {
            String url = pet.getOrganization().getInstaLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            view.openUrl(url);
        }
    }

    public void openYoutube(){
        if(pet.getOrganization()!=null && !StringUtils.isEmpty(pet.getOrganization().getYoutubeLink())) {
            String url = pet.getOrganization().getYoutubeLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            view.openUrl(url);
        }
    }

    public Pet getPet(){
        return pet;
    }

    public interface View{
        void populateUI(Pet pet);
        void populateDataNotFound();
        void populateDialog(String imgUrl);
        void openEmail(boolean hasBody, String toEmail, String subject, String body);
        void openUrl(String url);
    }
}
