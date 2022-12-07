package Util
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WineDTO(
    @SerializedName("Wid") val Wid : String,
    @SerializedName("name") val W_name : String,
    @SerializedName("type") val W_type : String,
    @SerializedName("region") val W_region : String,
    @SerializedName("region2") val W_region2 : String,
    @SerializedName("price") val W_price : String,
    @SerializedName("capacity") val W_capacity: String,
    @SerializedName("sweetness") val W_sweetness : String,
    @SerializedName("acidity") val W_acidity : String,
    @SerializedName("body") val W_body : String,
    @SerializedName("tannin") val W_tannin : String,
    @SerializedName("manufacturer") val W_manufacturer : String,
    @SerializedName("variety") val W_variety : String,
    @SerializedName("temperature") val W_temperature : String,
    @SerializedName("alcohol") val W_alcohol : String,
    @SerializedName("aroma_arr") val W_aroma_arr : Array<imageDTO>,
    @SerializedName("food_arr") val W_food_arr : Array<imageDTO>
):Serializable {
    constructor() : this(
        "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", ""
        ,arrayOf(imageDTO())
        ,arrayOf(imageDTO())
    )
//    constructor(Wid: String, W_name: String) : this(
//        Wid, W_name, "", "", "", "",
//        "", "", "", "", "", "", "", "", ""
//        ,arrayOf(imageDTO())
//        ,arrayOf(imageDTO())
//    )
}

data class imageDTO(
    @SerializedName("name") val name : String,
    @SerializedName("url") val url : String
) : Serializable{
    constructor() : this("","")
}