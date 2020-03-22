package com.sbhandare.pawdopt.Presenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Model.Page;
import com.sbhandare.pawdopt.Model.PageDetails;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragmentPresenter {
    private List<Pet> petList;
    private long totalResults;
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    OkhttpProcessor okhttpProcessor;
    private int pageCt;
    List<SecurityUser> securityUsers;

    public SearchFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.petList = new ArrayList<>();
        this.okhttpProcessor = new OkhttpProcessor();
        pageCt = 0;
        totalResults = 0;
    }

    public void populatePetList(){
        // rest api call to pawdopt service
        securityUsers = securityUserRepository.getAllSecurityUsers();

        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet?page="+pageCt+"&access_token=" + securityUsers.get(0).getToken();

            okhttpProcessor.get(url, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    System.out.println("failure");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Do what you want to do with the response.
                        if (response.body() != null) {
                            Page petsPage = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Page.class);
                            if (petsPage != null && petsPage.getListObj() != null && petsPage.getPageDetails() != null) {
                                List<Pet> tempPetList = (List<Pet>) (Object) petsPage.getListObj();
                                PageDetails pageDetails = (PageDetails) petsPage.getPageDetails();
                                totalResults = pageDetails.getTotalResults();
                                petList.add(null);

                                for (int i = 0; i < tempPetList.size(); i++) {
                                    String name = tempPetList.get(i).getName();
                                    String breed = tempPetList.get(i).getBreed();
                                    String image = tempPetList.get(i).getImage();

                                    Pet newPet = new Pet(name, breed, image);
                                    petList.add(newPet);
                                }
                            }
                        }

                        // call populateRV() method on view to update recycler view
                        view.populateRV(petList, totalResults);
                    } else {
                        // Request not successful
                        System.out.println("no success");
                    }
                }
            });
        }
    }

    public void getMorePets(){
        ++pageCt;
        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet?page="+pageCt+"&access_token="+ securityUsers.get(0).getToken();

            okhttpProcessor.get(url, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    System.out.println("failure");
                    if(pageCt>0)
                        --pageCt;
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Do what you want to do with the response.
                        if (response.body() != null) {
                            Page petsPage = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Page.class);
                            if (petsPage != null && petsPage.getListObj() != null) {
                                List<Pet> tempPetList = (List<Pet>) (Object) petsPage.getListObj();

                                for (int i = 0; i < tempPetList.size(); i++) {
                                    String name = tempPetList.get(i).getName();
                                    String breed = tempPetList.get(i).getBreed();
                                    String image = tempPetList.get(i).getImage();

                                    Pet newPet = new Pet(name, breed, image);
                                    petList.add(newPet);
                                }
                                view.updateRV();
                            }
                        }
                    } else {
                        // Request not successful
                        System.out.println("no success");
                        if(pageCt>0)
                            --pageCt;
                    }
                }
            });
        }
    }


    public interface View{
        void populateRV(List<Pet> petList, long totalResults);
        void updateRV();
    }
}
