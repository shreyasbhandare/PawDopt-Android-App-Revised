package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

import java.util.List;

public class AccountFragmentPresenter {

    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private OkhttpProcessor okhttpProcessor;

    public AccountFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void logout(){
        securityUserRepository.deleteAllSecurityUsers();
        view.loadLoginAcitivity();
    }

    public interface View{
        void loadLoginAcitivity();
    }
}
