package com.sbhandare.pawdopt.Service;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkhttpProcessor {

    public Call get(String url, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call post(String url, String bodyStr, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call postOauth(String url, String bodyStr, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Basic cGF3ZG9wdC1jbGllbnQtaWQ6cGF3ZG9wdC1jbGllbnQtc2VjcmV0")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}