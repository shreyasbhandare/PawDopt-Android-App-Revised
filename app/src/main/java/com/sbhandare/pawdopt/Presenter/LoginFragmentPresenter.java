package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.RoomDB.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginFragmentPresenter implements PawDoptPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private UserRepository userRepository;
    private OkhttpProcessor okhttpProcessor;

    public LoginFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.userRepository = new UserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void login(String username, String password) throws IOException {
        String url = "https://pawdopt.herokuapp.com/api/v1/oauth/token?grant_type=password&username="+username+"&password="+password;

        okhttpProcessor.post(url, "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                view.showLoginErrorToast();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        Oauth2Token oauth2Token = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);

                        if(oauth2Token!=null) {
                            securityUserRepository.deleteAllSecurityUsers();
                            userRepository.deleteAllUsers();
                            securityUserRepository.insertSecurityUser(username, oauth2Token.getAccess_token(), oauth2Token.getRefresh_token(), password);

                            //rest requeest to get user details by passing username
                            String urlUser = "https://pawdopt.herokuapp.com/api/v1/user/" + username + "?access_token="+oauth2Token.getAccess_token();
                            okhttpProcessor.get(urlUser, new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    view.showLoginErrorToast();
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if(response.isSuccessful()){
                                        if(response.body() != null){
                                            User user = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), User.class);
                                            if(user != null){
                                                userRepository.insertUser(user.getId(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getImage());
                                            }
                                        }
                                        view.loadMainActivity();
                                    }else
                                        view.showLoginErrorToast();
                                }
                            });
                        }
                    }
                } else {
                    view.showLoginErrorToast();
                }
            }
        });
    }

    @Override
    public void addUserFavorite(Pet pet, int pos) {

    }

    public interface View{
        void loadMainActivity();
        void showLoginErrorToast();
    }
}
