package com.sbhandare.pawdopt.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.URL;

public class PawDoptImageUtil {
    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int getImgHt(String urlStr){
        URL url = null;
        try {
            url = new URL(urlStr);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getImgWd(String urlStr){
        URL url = null;
        try {
            url = new URL(urlStr);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
