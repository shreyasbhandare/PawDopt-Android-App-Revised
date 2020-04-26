package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.Location.LocationService;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FavoritesFragmentPresenter implements PawDoptPresenter {

    private List<Pet> favPetList;
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private OkhttpProcessor okhttpProcessor;
    private List<SecurityUser> securityUsers;
    private LocationService locationService;

    public FavoritesFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.favPetList = new ArrayList<>();
        this.okhttpProcessor = new OkhttpProcessor();
        this.locationService = new LocationService(context);
    }

    public void populateFavoritesList(){
        // rest api call to pawdopt service
        this.favPetList.clear();
        securityUsers = securityUserRepository.getAllSecurityUsers();

        if(securityUsers!=null && !securityUsers.isEmpty() && securityUsers.get(0).getToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/pet?username="+securityUsers.get(0).getUsername()
                    +"&access_token=" + securityUsers.get(0).getToken();

            okhttpProcessor.get(url, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_DATA);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Do what you want to do with the response.

                        Type listType = new TypeToken<List<Pet>>() {}.getType();
                        List<Pet> tempPetList = GSON.getGson().fromJson(Objects.requireNonNull(Objects.requireNonNull(response.body()).string()), listType);

                        favPetList.add(null);
                        for (int i = 0; i < tempPetList.size(); i++) {
                            int id = tempPetList.get(i).getPetid();
                            String name = tempPetList.get(i).getName();
                            String breed = tempPetList.get(i).getBreed();
                            String image = tempPetList.get(i).getImage();
                            long distance=0;

                            if(tempPetList.get(i).getOrganization()!=null && tempPetList.get(i).getOrganization().getAddress()!=null){
                                distance = locationService.getDistanceInMiles(tempPetList.get(i).getOrganization().getAddress());
                            }

                            Pet newPet = new Pet(id, name, breed, image, distance);
                            favPetList.add(newPet);
                        }
                        // call populateRV() method on view to update recycler view
                        if(favPetList.size() <= 1)
                            view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_FAVORITES);
                        else
                            view.populateRV(favPetList);
                    } else {
                        // Request not successful
                        view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_FAVORITES);
                    }
                }
            });
        }
    }

    @Override
    public void addUserFavorite(Pet pet, int pos) {

    }

    public interface View{
        void populateRV(List<Pet> favPetList);
        void showErrorTV(String errType);
    }
}
