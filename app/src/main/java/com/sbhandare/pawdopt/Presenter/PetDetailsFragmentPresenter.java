package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PetDetailsFragmentPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private OkhttpProcessor okhttpProcessor;
    private List<SecurityUser> securityUsers;
    private Pet pet;
    private long distance;

    public PetDetailsFragmentPresenter(View view, Context context, long distance){
        this.view = view;
        this.context = context;
        this.distance = distance;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void populatePetDetails(int petId){
        securityUsers = securityUserRepository.getAllSecurityUsers();

        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet/"+petId+"?access_token=" + securityUsers.get(0).getToken();
            JSONObject userInfoBody = getUserInfoBody(securityUsers.get(0).getUsername());

            okhttpProcessor.postWithUserInfo(url,userInfoBody.toString(), new Callback() {
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

    private JSONObject getUserInfoBody(String username){
        JSONObject userInfoObject = new JSONObject();

        try {
            userInfoObject.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfoObject;
    }

    public interface View{
        void populateUI(Pet pet);
        void populateDataNotFound();
        void populateDialog(String imgUrl);
        void openEmail(boolean hasBody, String toEmail, String subject, String body);
        void openUrl(String url);
    }
}
