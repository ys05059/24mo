package Util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.request.RequestOptions

object WineImageAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic fun loadImage(imageView: ImageView,url :String){
        GlideApp.with(imageView.context)
            .load(url)
            .apply(RequestOptions.fitCenterTransform())
            .into(imageView)
    }
}