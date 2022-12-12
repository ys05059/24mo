package Util

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AdminDTO(
    @SerializedName("date") val A_date : String,
    @SerializedName("amount") val A_amount : String,
): Serializable {
    constructor() : this("", "")
}

data class SalesDTO(
    @SerializedName("Wid") val s_wid : String,
    @SerializedName("quantity") val s_quantity : String,
): Serializable {
    constructor() : this("", "")
}