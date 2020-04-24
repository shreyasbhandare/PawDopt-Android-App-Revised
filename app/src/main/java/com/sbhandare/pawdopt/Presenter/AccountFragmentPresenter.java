package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.RoomDB.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.RoomDB.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.OkhttpProcessor;

public class AccountFragmentPresenter {

    private View view;
    private Context context;
    private SecurityUserRepository securityUserRepository;
    private UserRepository userRepository;
    private OkhttpProcessor okhttpProcessor;

    public AccountFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
        this.securityUserRepository = new SecurityUserRepository(context);
        this.userRepository = new UserRepository(context);
        this.okhttpProcessor = new OkhttpProcessor();
    }

    public void populateUserInfo(){
        SecurityUser securityUser = securityUserRepository.getAllSecurityUsers().get(0);
        User user = userRepository.getAllUsers().get(0);

        if(securityUser != null && user != null){
            view.populateEmailTextView(securityUser.getUsername());
            view.populateNameTextView(user.getFirstName() + " " + user.getLastName());
        }
    }

    public void logout(){
        securityUserRepository.deleteAllSecurityUsers();
        userRepository.deleteAllUsers();
        view.loadLoginAcitivity();
    }

    public interface View{
        void loadLoginAcitivity();
        void populateEmailTextView(String email);
        void populateNameTextView(String name);
    }
}
