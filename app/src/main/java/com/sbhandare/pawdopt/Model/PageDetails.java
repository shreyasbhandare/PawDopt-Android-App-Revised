package com.sbhandare.pawdopt.Model;

public class PageDetails
{
    private int currentPage;

    private int resultsPerPage;

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getCurrentPage(){
        return this.currentPage;
    }
    public void setResultsPerPage(int resultsPerPage){
        this.resultsPerPage = resultsPerPage;
    }
    public int getResultsPerPage(){
        return this.resultsPerPage;
    }
}
