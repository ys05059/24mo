package Util

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.request.RequestOptions
import com.example.a24mo.R
import kotlinx.coroutines.CoroutineScope
import java.text.DecimalFormat

// 데이터 바인딩 사용할 때 glide로 이미지 띄우기 위함
object WineImageAdapter {
    val defaultImage = R.drawable.wine_ready_image
    @BindingAdapter("imageUrl")
    @JvmStatic fun loadImage(imageView: ImageView,url :String){
        GlideApp.with(imageView.context)
            .load(url)
            .apply(RequestOptions.fitCenterTransform())
            .placeholder(defaultImage)                  // 이미지 로딩 시작하기 전 표시할 이미지
            .error(defaultImage)                        // 로딩 에러 발생 시 표시할 이미지
            .fallback(defaultImage)                     // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
            .into(imageView)
    }
}

object WineFeatureSettingAdapter {
    @BindingAdapter("bindingAdapter_feature_setting")
    @JvmStatic
    fun featureBtnSetting(btn: Button, value: String) {
        var result = ""
        when (value) {
            "1" -> result = "낮음"
            "2" -> result = "조금 낮음"
            "3" -> result = "적당함"
            "4" -> result = "조금 높음"
            "5" -> result = "높음"
        }
        btn.text = result
    }
}

object WineBodySettingAdapter {
    @BindingAdapter("bindingAdapter_body_setting")
    @JvmStatic
    fun bodyBtnSetting(btn: Button, value: String) {
        var result = ""
        when (value) {
            "1" -> result = "가벼움"
            "2" -> result = "조금 가벼움"
            "3"-> result = "적당함"
            "4" -> result = "조금 묵직함"
            "5" -> result = "묵직함"
        }
        btn.text = result
    }
}

object WinePriceFormatAdapter{
    @BindingAdapter("bindingAdapter_price_format")
    @JvmStatic
    fun bodyBtnSetting(view: TextView, price: String) {
        val formatter  = DecimalFormat("#,###,###")
        view.text ="\\" +formatter.format(price.toInt()) + "원"
    }
}

object  WinePriceRangeAdapter{
    @BindingAdapter("bindingAdapter_price_range_setting")
    @JvmStatic
    fun bodyBtnSetting(view: TextView, price_str: String) {
        val price = price_str.toInt()
        if(price <30000){
            view.text = "3만원 이하"
        }else if(price <100000){
            view.text = "3만원 ~ 10만원"
        }else{
            view.text = "10만원 이상"
        }

    }
}