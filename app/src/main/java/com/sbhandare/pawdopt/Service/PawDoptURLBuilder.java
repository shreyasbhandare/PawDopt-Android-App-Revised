package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.Model.GeoPoint;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.apache.commons.lang3.StringUtils;

public class PawDoptURLBuilder {
    private static String baseUrl = "https://pawdopt.herokuapp.com/api/v1/";

    public static String buildSearchListURL(String access_token, int page, String type, String location){
        StringBuilder searchUrlBuilder = new StringBuilder(baseUrl).append("pet?");
        searchUrlBuilder.append("access_token=").append(access_token).append("&");
        searchUrlBuilder.append("page=").append(page).append("&");
        if(!StringUtils.isEmpty(type))
            searchUrlBuilder.append("type=").append(type).append("&");
        if(!StringUtils.isEmpty(location))
            searchUrlBuilder.append("location=").append(location).append("&");

        searchUrlBuilder.setLength(searchUrlBuilder.length() - 1);
        return searchUrlBuilder.toString();
    }

    public static String buildLocation(int dist, GeoPoint pt){
        if(pt==null || dist==-1)
            return "";
        return dist+","+pt.getLattitude()+","+pt.getLongitude();
    }

    public static String buildType(String type){
        if(StringUtils.equals(type, PawDoptUtil.NO_TYPE_FILTER))
            return "";
        return type;
    }
}
