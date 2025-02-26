package com.sbhandare.pawdopt.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

@Entity(tableName = "pet")
public class Pet
{
    @PrimaryKey
    @NonNull
    private long petid;

    private long petfinderid;

    private String name;

    private String breed;

    private String typeCode;

    private String gender;

    private String age;

    private String color;

    private String coat;

    private String size;

    private String bio;

    private String image;

    private String image2;

    private String image3;

    private String image4;

    private String isVaccinated;

    private String isSpayedNeutered;

    private String isGoodWithCats;

    private String isGoodWithChildren;

    private String isGoodWithDogs;

    private String isAdoptable;

    private String isDeclawed;

    private String isHouseTrained;

    private String isSpecialNeeds;

    private String tags;

    @Ignore
    private Organization organization;

    private long distance;

    @SerializedName("currentUserFav")
    private boolean isCurrentUserFav;

    private double latitude;

    private double longitude;

    public Pet(){

    }

    public Pet(long id, String name, String breed, String img, long distance, double latitude, double longitude){
        this.petid = id;
        this.name = name;
        this.breed = breed;
        this.image = img;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setPetid(long petid){
        this.petid = petid;
    }
    public long getPetid(){
        return this.petid;
    }
    public void setPetfinderid(long petfinderid){
        this.petfinderid = petfinderid;
    }
    public long getPetfinderid(){
        return this.petfinderid;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setBreed(String breed){
        this.breed = breed;
    }
    public String getBreed(){
        return this.breed;
    }
    public void setTypeCode(String typeCode){
        this.typeCode = typeCode;
    }
    public String getTypeCode(){
        return this.typeCode;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return this.gender;
    }
    public void setAge(String age){
        this.age = age;
    }
    public String getAge(){
        return this.age;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return this.color;
    }
    public void setCoat(String coat){
        this.coat = coat;
    }
    public String getCoat(){
        return this.coat;
    }
    public void setSize(String size){
        this.size = size;
    }
    public String getSize(){
        return this.size;
    }
    public void setBio(String bio){
        this.bio = bio;
    }
    public String getBio(){
        return this.bio;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public String getImage2() {
        return image2;
    }
    public void setImage2(String image2) {
        this.image2 = image2;
    }
    public String getImage3() {
        return image3;
    }
    public void setImage3(String image3) {
        this.image3 = image3;
    }
    public String getImage4() {
        return image4;
    }
    public void setImage4(String image4) {
        this.image4 = image4;
    }
    public void setIsVaccinated(String isVaccinated){
        this.isVaccinated = isVaccinated;
    }
    public String getIsVaccinated(){
        return this.isVaccinated;
    }
    public void setIsSpayedNeutered(String isSpayedNeutered){
        this.isSpayedNeutered = isSpayedNeutered;
    }
    public String getIsSpayedNeutered(){
        return this.isSpayedNeutered;
    }
    public void setIsGoodWithCats(String isGoodWithCats){
        this.isGoodWithCats = isGoodWithCats;
    }
    public String getIsGoodWithCats(){
        return this.isGoodWithCats;
    }
    public void setIsGoodWithChildren(String isGoodWithChildren){
        this.isGoodWithChildren = isGoodWithChildren;
    }
    public String getIsGoodWithChildren(){
        return this.isGoodWithChildren;
    }
    public void setIsGoodWithDogs(String isGoodWithDogs){
        this.isGoodWithDogs = isGoodWithDogs;
    }
    public String getIsGoodWithDogs(){
        return this.isGoodWithDogs;
    }
    public void setIsAdoptable(String isAdoptable){
        this.isAdoptable = isAdoptable;
    }
    public String getIsAdoptable(){
        return this.isAdoptable;
    }
    public void setIsDeclawed(String isDeclawed){
        this.isDeclawed = isDeclawed;
    }
    public String getIsDeclawed(){
        return this.isDeclawed;
    }
    public void setIsHouseTrained(String isHouseTrained){
        this.isHouseTrained = isHouseTrained;
    }
    public String getIsHouseTrained(){
        return this.isHouseTrained;
    }
    public void setIsSpecialNeeds(String isSpecialNeeds){
        this.isSpecialNeeds = isSpecialNeeds;
    }
    public String getIsSpecialNeeds(){
        return this.isSpecialNeeds;
    }
    public void setTags(String tags){
        this.tags = tags;
    }
    public String getTags(){
        return this.tags;
    }
    public void setOrganization(Organization organization){
        this.organization = organization;
    }
    public Organization getOrganization(){
        return this.organization;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public boolean isCurrentUserFav() {
        return isCurrentUserFav;
    }

    public void setCurrentUserFav(boolean currentUserFav) {
        isCurrentUserFav = currentUserFav;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

