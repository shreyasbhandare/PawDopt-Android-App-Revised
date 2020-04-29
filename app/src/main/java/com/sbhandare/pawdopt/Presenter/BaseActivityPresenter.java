package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.RoomDB.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseActivityPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private UserRepository userRepository;
    private OkhttpProcessor okhttpProcessor;

    public BaseActivityPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.userRepository = new UserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public boolean isUserAlreadyLoggedIn(){
        //List<SecurityUser> securityUserList = securityUserRepository.getAllSecurityUsers();
        return securityUserRepository.getAllSecurityUsers() != null && !securityUserRepository.getAllSecurityUsers().isEmpty();
    }

    public void refreshAccessToken(){
        SecurityUser securityUser = securityUserRepository.getAllSecurityUsers().get(0);

        if(securityUser!=null && securityUser.getRefreshToken()!=null) {
            String url = "https://pawdopt.herokuapp.com/api/v1/oauth/token?grant_type=refresh_token&refresh_token=" + securityUser.getRefreshToken();

            okhttpProcessor.post(url, "", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Something went wrong
                    System.out.println("failure");
                    view.loadLoginAcitivty();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Oauth2Token oauth2Token = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);

                            if(oauth2Token!=null) {
                                securityUser.setRefreshToken(oauth2Token.getRefresh_token());
                                securityUser.setToken(oauth2Token.getAccess_token());
                                securityUserRepository.updateSecurityUser(securityUser);
                            }
                            view.loadMainActivity();
                        }
                    } else {
                        // Request not successful
                        System.out.println("no success");
                        view.loadLoginAcitivty();
                    }
                }
            });
        }
    }

    public interface View{
        void loadLoginAcitivty();
        void loadMainActivity();
    }
}
