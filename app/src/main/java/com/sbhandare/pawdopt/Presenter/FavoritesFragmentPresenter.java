package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.GeoPoint;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.RoomDB.Repository.PetRepository;
import com.sbhandare.pawdopt.Service.Location.LocationService;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragmentPresenter {

    private List<Pet> favPetList;
    private View view;
    private Context context;
    private OkhttpProcessor okhttpProcessor;
    private LocationService locationService;
    private PetRepository petRepository;

    public FavoritesFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.favPetList = new ArrayList<>();
        this.okhttpProcessor = new OkhttpProcessor();
        this.locationService = new LocationService(context);
        this.petRepository = new PetRepository(context);
    }

    public void populateFavoritesList(){
        List<Pet> tempPetList = petRepository.getAllPets();
        if(tempPetList != null && !tempPetList.isEmpty()){
            favPetList.add(null);
            for (int i = 0; i < tempPetList.size(); i++) {
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
                favPetList.add(newPet);
            }
            view.populateRV(favPetList);
        } else {
            //no favorites
            view.showErrorTV(PawDoptUtil.ERR_TYPE_NO_FAVORITES);
        }
    }

    public void updateFavorites(Pet pet){
        if(favPetList.isEmpty()){
            favPetList.add(null);
            favPetList.add(pet);
            view.populateRV(favPetList);
        }
        else {
            favPetList.add(pet);
            view.updateRV();
        }
    }

    public interface View{
        void populateRV(List<Pet> favPetList);
        void updateRV();
        void showErrorTV(String errType);
    }
}
