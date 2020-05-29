package com.sbhandare.pawdopt.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Service.GSON;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;
import com.sbhandare.pawdopt.Service.PawDoptURLBuilder;
import com.sbhandare.pawdopt.Util.PawDoptUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class BaseActivityPresenter {
    private View view;
    private Context context;
    private SharedPreferences sharedPreferences;
    private OkhttpProcessor okhttpProcessor;

    public BaseActivityPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PawDoptUtil.PAWDOPT_SHARED_PREFS, MODE_PRIVATE);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void getAccessToken(){
        String url = PawDoptURLBuilder.buildOauthTokenURL();

        okhttpProcessor.postOauth(url, "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    Oauth2Token oauth2Token = GSON.getGson().fromJson(Objects.requireNonNull(response.body()).string(), Oauth2Token.class);
                    if(oauth2Token!=null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("access_token", oauth2Token.getAccess_token());
                        editor.apply();
                    }
                } else {
                    // Request not successful
                }
            }
        });
    }

    public interface View{
    }
}
