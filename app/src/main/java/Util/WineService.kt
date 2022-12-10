package Util

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.time.LocalDate
import java.util.Date

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

    //일매출 총합, 일 매출 상세
    @FormUrlEncoded
    @POST("get_daily.php")
    suspend fun getDaily(
        @Field("date") date: String
    ): Response<AdminDTO>

    @FormUrlEncoded
    @POST("get_daily_sales.php")
    suspend fun getDailySales(
        @Field("date") date: String
    ): Response<SalesDTO>

    @FormUrlEncoded
    @POST("get_weekly.php")
    suspend fun getWeekly(
        @Field("date") date: String
    ): Response<AdminDTO>

    @FormUrlEncoded
    @POST("get_monthly.php")
    suspend fun getMonthly(
        @Field("date") date: String
    ): Response<AdminDTO>
}