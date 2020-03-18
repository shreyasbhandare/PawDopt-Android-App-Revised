package com.sbhandare.pawdopt.Model;

public class Organization
{
    private int organizationid;

    private String name;

    private String email;

    private String phone;

    private String bio;

    private String image;

    private String webLink;

    private String fbLink;

    private String twitterLink;

    private String instaLink;

    private String youtubeLink;

    private String petfinderCode;

    private Address address;

    public void setOrganizationid(int organizationid){
        this.organizationid = organizationid;
    }
    public int getOrganizationid(){
        return this.organizationid;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
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
    public void setWebLink(String webLink){
        this.webLink = webLink;
    }
    public String getWebLink(){
        return this.webLink;
    }
    public void setFbLink(String fbLink){
        this.fbLink = fbLink;
    }
    public String getFbLink(){
        return this.fbLink;
    }
    public void setTwitterLink(String twitterLink){
        this.twitterLink = twitterLink;
    }
    public String getTwitterLink(){
        return this.twitterLink;
    }
    public void setInstaLink(String instaLink){
        this.instaLink = instaLink;
    }
    public String getInstaLink(){
        return this.instaLink;
    }
    public void setYoutubeLink(String youtubeLink){
        this.youtubeLink = youtubeLink;
    }
    public String getYoutubeLink(){
        return this.youtubeLink;
    }
    public void setPetfinderCode(String petfinderCode){
        this.petfinderCode = petfinderCode;
    }
    public String getPetfinderCode(){
        return this.petfinderCode;
    }
    public void setAddress(Address address){
        this.address = address;
    }
    public Address getAddress(){
        return this.address;
    }
}
