package com.sbhandare.pawdopt.Model;

public class Organization {

    private long organizationid;

    private String name;

    private Address address;

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

    public long getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(long organizationid) {
        this.organizationid = organizationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getPetfinderCode() {
        return petfinderCode;
    }

    public void setPetfinderCode(String petfinderCode) {
        this.petfinderCode = petfinderCode;
    }
}
