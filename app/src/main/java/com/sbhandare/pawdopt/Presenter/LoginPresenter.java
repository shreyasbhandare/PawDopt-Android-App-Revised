package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;

    public LoginPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
    }

    public void validateCredentials(){

    }

    public boolean isAlreadyLoggedIn(String username){
        return securityUserRepository.getSecurityUserByUsername(username) != null;
    }

    public void login(String username, String password) throws IOException {
        String url = "https://pawdopt.herokuapp.com/api/v1/oauth/token?grant_type=password&username="+username+"&password="+password;

        post(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Something went wrong
                System.out.println("failure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Do what you want to do with the response.
                    if(response.body() != null) {
                        //System.out.println(Objects.requireNonNull(response.body()).string());
                        Gson gson = new Gson();
                        Oauth2Token oauth2Token = gson.fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);

                        if(securityUserRepository.getSecurityUserByUsername(username)==null) {
                            securityUserRepository.insertSecurityUser(username,
                                    oauth2Token.getAccess_token(),
                                    oauth2Token.getRefresh_token(),
                                    password);
                        }
                    }
                    view.loadMainActivity();
                } else {
                    System.out.println("no success");
                    // Request not successful
                }
            }
        });
    }

    private Call post(String url, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create("", JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Basic cGF3ZG9wdC1jbGllbnQtaWQ6cGF3ZG9wdC1jbGllbnQtc2VjcmV0")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public interface View{
        void loadMainActivity();
    }
}
