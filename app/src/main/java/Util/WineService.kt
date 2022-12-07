package Util

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WineService {
//    @FormUrlEncoded
//    @POST("get_wine_by_wid.php")
//    fun getWineDetail(
//        @Field("Wid") Wid: Int
//    ): Call<WineDTO>
//
    // 코루틴 방식
    @FormUrlEncoded
    @POST("get_wine_by_wid.php")
    suspend fun getWineDetail(
        @Field("Wid") Wid: Int
    ): Response<WineDTO>
}