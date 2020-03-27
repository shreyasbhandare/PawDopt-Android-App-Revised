package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginPresenter implements PawDoptPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private OkhttpProcessor okhttpProcessor;

    public LoginPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void login(String username, String password) throws IOException {
        String url = "https://pawdopt.herokuapp.com/api/v1/oauth/token?grant_type=password&username="+username+"&password="+password;

        okhttpProcessor.post(url, "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Something went wrong
                System.out.println("failure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        Oauth2Token oauth2Token = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);

                        if(oauth2Token!=null) {
                            securityUserRepository.deleteAllSecurityUsers();
                            securityUserRepository.insertSecurityUser(username, oauth2Token.getAccess_token(), oauth2Token.getRefresh_token(), password);
                            view.loadMainActivity();
                        }
                    }
                } else {
                    // Request not successful
                    System.out.println("no success");
                }
            }
        });
    }

    @Override
    public void addUserFavorite(Pet pet, int pos) {

    }

    public interface View{
        void loadMainActivity();
    }
}
