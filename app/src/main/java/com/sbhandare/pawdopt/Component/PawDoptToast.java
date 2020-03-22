package com.sbhandare.pawdopt.Component;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sbhandare.pawdopt.R;

public class PawDoptToast {
    public static void showFavoritesToast(Context context, String name){
        Toast toast = Toast.makeText(context, name+" added to Favorites!", Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_rounded_backround);
        view.setPadding(16,16,16,16);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        text.setTextSize(16);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
