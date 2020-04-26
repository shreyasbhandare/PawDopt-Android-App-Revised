package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.RoomDB.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.AES;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupFragmentPresenter {
    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private UserRepository userRepository;
    private OkhttpProcessor okhttpProcessor;

    public SignupFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.userRepository = new UserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void signUpAndLogin(String fName, String lName, String email, String pass){
        String signupUrl = "https://pawdopt.herokuapp.com/api/v1/user/register";
        String passEncoded = AES.encrypt(pass);
        JSONObject signUpUserObject = getSignUpBody(fName,lName,email,passEncoded);
        if(signUpUserObject != null && !StringUtils.isEmpty(signUpUserObject.toString())) {
            okhttpProcessor.signUpPost(signupUrl, signUpUserObject.toString(), new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    view.showSignupErrorToast();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            Oauth2Token oauth2Token = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);

                            if(oauth2Token!=null) {
                                securityUserRepository.deleteAllSecurityUsers();
                                userRepository.deleteAllUsers();
                                securityUserRepository.insertSecurityUser(email, oauth2Token.getAccess_token(), oauth2Token.getRefresh_token(), passEncoded);

                                //rest requeest to get user details by passing username
                                String urlUser = "https://pawdopt.herokuapp.com/api/v1/user/" + email + "?access_token="+oauth2Token.getAccess_token();
                                okhttpProcessor.get(urlUser, new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        view.showSignupErrorToast();
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
                                            view.showSignupErrorToast();
                                    }
                                });
                            }
                        }
                    }else
                        view.showSignupErrorToast();
                }
            });
        }
    }

    private JSONObject getSignUpBody(String fName, String lName, String email, String passEncoded){
        JSONObject signUpUserObject = new JSONObject();
        try {
            JSONObject userObj = new JSONObject();
            userObj.put("firstName",fName);
            userObj.put("lastName",lName);
            userObj.put("email",email);
            userObj.put("image","");

            JSONObject securityUserObj = new JSONObject();
            securityUserObj.put("username",email);
            securityUserObj.put("password",passEncoded);


            signUpUserObject.put("user", userObj);
            signUpUserObject.put("securityUser",securityUserObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return signUpUserObject;
    }

    public interface View{
        void loadMainActivity();
        void showSignupErrorToast();
    }
}
