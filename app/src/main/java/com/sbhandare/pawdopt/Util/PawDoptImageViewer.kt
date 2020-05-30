package com.sbhandare.pawdopt.Util

import android.content.Context
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer

class PawDoptImageViewer {
    companion object {
        fun loadImages(context: Context, images: List<String>) {
            StfalconImageViewer.Builder(context, images) { view, image ->
                Picasso.get().load(image).into(view)
            }
            .show()
        }
    }
}