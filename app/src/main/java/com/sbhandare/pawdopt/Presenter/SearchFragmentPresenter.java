package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Model.Page;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchFragmentPresenter {
    private List<Pet> petList;
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;

    public SearchFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.petList = new ArrayList<>();
    }

    public void populatePetList(){
        // rest api call to pawdopt service
        List<SecurityUser> securityUsers = securityUserRepository.getAllSecurityUsers();

        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet?access_token=" + securityUsers.get(0).getToken();

            get(url, new Callback() {
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
                            //System.out.println(Objects.requireNonNull(response.body()).string());
                            Gson gson = new Gson();
                            Page petsPage = gson.fromJson(Objects.requireNonNull(response.body()).string(), Page.class);
                            if(petsPage!=null && petsPage.getListObj()!=null) {
                                List<Pet> tempPetList = (List<Pet>)(Object)petsPage.getListObj();

                                for(int i=0; i<tempPetList.size(); i++){
                                    String name = tempPetList.get(i).getName();
                                    String breed = tempPetList.get(i).getBreed();
                                    String image = tempPetList.get(i).getImage();

                                    Pet newPet = new Pet(name,breed,image);
                                    petList.add(newPet);
                                }
                            }

                            //System.out.println(petsPage.getListObj());
                        }

                        // call populateRV() method on view to update recycler view

                        view.populateRV(petList);
                    } else {
                        System.out.println("no success");
                        // Request not successful
                    }
                }
            });
        }


    }

    private Call get(String url, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public interface View{
        void populateRV(List<Pet> petList);
    }
}
