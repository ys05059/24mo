package com.example.a24mo

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {

        Log.d("WineDTO" , "My bind 들어왔다")
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
//                    .placeholder(placeholder)
//                    .error(placeholder)
//                    .fallback(placeholder)
                .into(view)
        }
    }
}
