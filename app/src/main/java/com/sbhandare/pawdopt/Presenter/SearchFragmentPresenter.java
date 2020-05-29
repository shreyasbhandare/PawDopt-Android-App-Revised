package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.GeoPoint;
import com.sbhandare.pawdopt.Model.Page;
import com.sbhandare.pawdopt.Model.PageDetails;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.RoomDB.Repository.PetRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.Location.LocationService;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;
import com.sbhandare.pawdopt.Service.PawDoptURLBuilder;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragmentPresenter {
    private List<Pet> petList;
    private long totalResults;
    private View view;
    private Context context;
    private OkhttpProcessor okhttpProcessor;
    private int pageCt;
    private int distance;
    private String type;
    private LocationService locationService;
    private String token;
    private PetRepository petRepository;
    private Set<Long> favPetIdSet;

    public SearchFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.petList = new ArrayList<>();
        this.okhttpProcessor = new OkhttpProcessor();
        this.petRepository = new PetRepository(context);
        this.locationService = new LocationService(context);
        this.token = context.getSharedPreferences(PawDoptUtil.PAWDOPT_SHARED_PREFS, MODE_PRIVATE).getString("access_token","");
        pageCt = 0;
        distance = PawDoptUtil.NO_DISTANCE_FILTER;
        type = PawDoptUtil.NO_TYPE_FILTER;
        totalResults = 0;
        favPetIdSet = petRepository.getAllPetIds();
    }

    public void populatePetList(){
        // rest api call to pawdopt service
        if(token != null) {
            String category = PawDoptURLBuilder.buildType(type);
            String location = PawDoptURLBuilder.buildLocation(distance, locationService.getCurrentLatLong());
            String url = PawDoptURLBuilder.buildSearchListURL(token, pageCt, category, location);

            okhttpProcessor.post(url,"", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_DATA);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {

                        Page petsPage = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Page.class);
                        if (petsPage != null && petsPage.getListObj() != null && petsPage.getPageDetails() != null) {
                            List<Pet> tempPetList = petsPage.getListObj();
                            PageDetails pageDetails = petsPage.getPageDetails();
                            totalResults = pageDetails.getTotalResults();
                            petList.clear();
                            petList.add(null);

                            for (int i = 0; i < tempPetList.size(); i++) {
                                if(favPetIdSet.contains(tempPetList.get(i).getPetid()))
                                    continue;
                                long id = tempPetList.get(i).getPetid();
                                String name = tempPetList.get(i).getName();
                                String breed = tempPetList.get(i).getBreed();
                                String image = tempPetList.get(i).getImage();
                                double lat = tempPetList.get(i).getLatitude();
                                double lon = tempPetList.get(i).getLongitude();
                                long distance = 0;

                                if(lat != -1 && lon != -1){
                                    distance = locationService
                                            .getDistanceInMiles(new GeoPoint(lat, lon));
                                }

                                Pet newPet = new Pet(id, name, breed, image, distance, lat, lon);
                                petList.add(newPet);
                            }
                        }
                        view.populateRV(petList, totalResults);
                    } else {
                        // Request not successful
                        view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_DATA);
                    }
                }
            });
        }
    }

    public void getMorePets(){
        ++pageCt;
        if(token != null) {
            String category = PawDoptURLBuilder.buildType(type);
            String location = PawDoptURLBuilder.buildLocation(distance, locationService.getCurrentLatLong());
            String url = PawDoptURLBuilder.buildSearchListURL(token, pageCt, category, location);

            okhttpProcessor.post(url,"", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    System.out.println("failure");
                    if(pageCt>0)
                        --pageCt;
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null ) {
                        Page petsPage = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Page.class);
                        if (petsPage != null && petsPage.getListObj() != null) {
                            List<Pet> tempPetList = petsPage.getListObj();

                            for (int i = 0; i < tempPetList.size(); i++) {
                                if(favPetIdSet.contains(tempPetList.get(i).getPetid()))
                                    continue;
                                long id = tempPetList.get(i).getPetid();
                                String name = tempPetList.get(i).getName();
                                String breed = tempPetList.get(i).getBreed();
                                String image = tempPetList.get(i).getImage();
                                double lat = tempPetList.get(i).getLatitude();
                                double lon = tempPetList.get(i).getLongitude();
                                long distance = 0;

                                if(lat != -1 && lon != -1){
                                    distance = locationService
                                            .getDistanceInMiles(new GeoPoint(lat, lon));
                                }

                                Pet newPet = new Pet(id, name, breed, image, distance, lat, lon);
                                petList.add(newPet);
                            }
                            view.updateRV();
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

    public void addUserFavorite(Pet pet, int pos){
        petRepository.insertPet(pet);
        petList.remove(pos);
        view.removeFavoriteFromRV(pet.getName(), pos, petList.size());
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public interface View{
        void populateRV(List<Pet> petList, long totalResults);
        void updateRV();
        void removeFavoriteFromRV(String name, int pos, int size);
        void showErrorTV(String errType);
    }
}