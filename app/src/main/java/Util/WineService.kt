package Util

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WineService {
    // 와인 id로 상세정보 검색
    @FormUrlEncoded
    @POST("get_wine_by_wid.php")
    suspend fun getWineDetail(
        @Field("Wid") Wid: Int
    ): Response<WineDTO>

    // 와인 추천 리스트 받기
    @FormUrlEncoded
    @POST("get_recommend_wine_list.php")
    suspend fun getRecommendList(
        @Field("Tag1") Tag1: String,
        @Field("Tag2") Tag2: String
    ): Response<WineList>


    // 와인 검색 리스트 받기
    @FormUrlEncoded
    @POST("get_search_wine_list.php")
    suspend fun getSearchList(
        @Field("name") name :String,
        @Field("min_price") min_price : Int,
        @Field("max_price") max_price : Int,
        @Field("type") type: String,
        @Field("region") region: String,
        @Field("alcohol") alcohol : Int,
        @Field("food") food : String,
        @Field("sweetness") sweetness : Int,
        @Field("acidity") acidity : Int,
        @Field("body") body : Int,
        @Field("tannin") tannin : Int
    ): Response<WineList>

    // 최근 7일 일 매출합 조회
    @GET("get_daily_sum_list.php")
    suspend fun getDailySumList(): Response<DailySumListDTO>

    // 매출 상세 조회
    @FormUrlEncoded
    @POST("get_sales_list.php")
    suspend fun getSalesList(
        @Field("date") date: String
    ): Response<SalesListDTO>

}
