package com.sbhandare.pawdopt.Presenter;

import android.content.Context;

public class AccountFragmentPresenter {

    private View view;
    private Context context;

    public AccountFragmentPresenter(View view, Context context){
        this.view = view;
        this.context = context;
    }

    public interface View{
    }
}
