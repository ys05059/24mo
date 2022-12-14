package Util

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyDTO(
    @SerializedName("date") val A_date : String,
    @SerializedName("amount") val A_amount : String,
): Serializable {
    constructor() : this("", "")
}

data class SalesDTO(
    @SerializedName("date") val s_date : String,
    @SerializedName("wname") val s_wname : String,
    @SerializedName("quantity") val s_quantity : String,
): Serializable {
    constructor() : this("", "","")
}