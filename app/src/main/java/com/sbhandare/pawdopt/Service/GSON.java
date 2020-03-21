package com.sbhandare.pawdopt.Service;

import com.google.gson.Gson;

public class GSON
{
    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }
}
