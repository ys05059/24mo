package Model
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
    @SerializedName("image_url") val W_image : String,
    @SerializedName("aroma_arr") val W_aroma_arr : Array<imageDTO>,
    @SerializedName("food_arr") val W_food_arr : Array<imageDTO>,
    var checked : Boolean
):Serializable {
    constructor() : this(
        "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "",""
        ,arrayOf(imageDTO())
        ,arrayOf(imageDTO())
        ,false
    )
}


data class imageDTO(
    @SerializedName("name") val name : String,
    @SerializedName("url") val url : String
) : Serializable{
    constructor() : this("","")
}

data class SearchWineParmeter(
    var name: String,
    var min_price : Int,
    var max_price : Int,
    var type: String,
    var region: String,
    var alcohol : Int,
    var food : String,
    var sweet : Int,
    var acidity : Int,
    var body : Int,
    var tannin : Int
){
    constructor() :this("",0,0,"","",0,"",-1,-1,-1,-1)
}

data class WineList(
    @SerializedName("status") val status : String,
    @SerializedName("data") val wine_list : ArrayList<WineDTO>
):Serializable{
    constructor() : this ("", ArrayList<WineDTO>())
}