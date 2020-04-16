package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

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

    public PetDetailsFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void populatePetDetails(int petId){
        securityUsers = securityUserRepository.getAllSecurityUsers();

        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet/"+petId+"?access_token=" + securityUsers.get(0).getToken();

            okhttpProcessor.get(url, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    System.out.println("failure");
                    view.populateDataNotFound();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Do what you want to do with the response.
                        if (response.body() != null) {
                            pet = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(),Pet.class);
                            if(pet!=null)
                                view.populateUI(pet);
                            else
                                view.populateDataNotFound();
                        }
                        // call populateRV() method on view to update recycler view
                    } else {
                        // Request not successful
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

    public void addFavorite(){

    }

    public interface View{
        void populateUI(Pet pet);
        void populateDataNotFound();
        void populateDialog(String imgUrl);
        void openEmail(boolean hasBody, String toEmail, String subject, String body);
    }
}
