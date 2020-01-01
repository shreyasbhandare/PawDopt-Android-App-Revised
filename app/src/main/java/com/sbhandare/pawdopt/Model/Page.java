package com.sbhandare.pawdopt.Model;

public class Page {

    private Object listObj;

    private Pagination pageDetails;

    public Page(Object listObj, Pagination pageDetails) {
        this.pageDetails = pageDetails;
        this.listObj = listObj;
    }

    public Pagination getPageDetails() {
        return pageDetails;
    }

    public void setPageDetails(Pagination pageDetails) {
        this.pageDetails = pageDetails;
    }

    public Object getListObj() {
        return listObj;
    }

    public void setListObj(Object listObj) {
        this.listObj = listObj;
    }
}