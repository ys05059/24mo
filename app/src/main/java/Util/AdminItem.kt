package Util

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailySumDTO(
    @SerializedName("date") val A_date : String,
    @SerializedName("amount") val A_amount : String,
): Serializable {
    constructor() : this("", "")
}

data class DailySumListDTO(
    @SerializedName("status") val status : String,
    @SerializedName("data") val dailySumList : ArrayList<DailySumDTO>
): Serializable {
    constructor() : this("", ArrayList())
}


data class SalesDTO(
    @SerializedName("date") val s_date : String,
    @SerializedName("wid") val s_wid : Int,
    @SerializedName("quantity") val s_quantity : Int,
): Serializable {
    constructor() : this("", 0,0)
}

data class SalesListDTO(
    @SerializedName("status") val status : String,
    @SerializedName("data") val salesList : ArrayList<SalesDTO>
): Serializable {
    constructor() : this("", ArrayList())
}

data class InsertResult(
    @SerializedName("status") val status : String
): Serializable{
    constructor() : this("")
}