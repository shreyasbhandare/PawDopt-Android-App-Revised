package com.sbhandare.pawdopt.Model;

import java.util.List;

public class Page
{
    private List<Pet> listObj;

    private PageDetails pageDetails;

    public void setListObj(List<Pet> listObj){
        this.listObj = listObj;
    }
    public List<Pet> getListObj(){
        return this.listObj;
    }
    public void setPageDetails(PageDetails pageDetails){
        this.pageDetails = pageDetails;
    }
    public PageDetails getPageDetails(){
        return this.pageDetails;
    }
}