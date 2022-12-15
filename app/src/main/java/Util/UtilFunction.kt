package Util

import java.text.DecimalFormat

fun feature_fun(value : String): String{
    var result = ""
    when(value){
        "1" -> result = "낮음"
        "2" -> result = "조금 낮음"
        "3" -> result = "적당함"
        "4" -> result = "조금 높음"
        "5" -> result = "높음"
    }
    return result
}

fun body_fun(value : String): String{
    var result = ""
    when(value){
        "1" -> result = "가벼움"
        "2" -> result = "조금 가벼움"
        "3"-> result = "적당함"
        "4" -> result = "조금 묵직함"
        "5" -> result = "묵직함"
    }
    return result
}

fun price_format(price : String) : String {
    val formatter  = DecimalFormat("#,###,###")
    return "\\" +formatter.format(price.toInt()) + "원"
}