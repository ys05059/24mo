package Model
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 와인 조회 API Remote Data source
object WineRemoteDataSource {
    private val gson = GsonBuilder().setLenient().create()

    fun getWineService() : WineService {
        return Retrofit.Builder()
            .baseUrl("http://52.78.233.108/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WineService::class.java)
    }

//    private const val IP_ADDRESS = "52.78.233.108"

//    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://52.78.233.108/")
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()


//    val wineService = retrofit.create(WineService::class.java)


//    fun getWineDetail(Wid : Int, callback: WineRepository.GetDataCallback<WineDTO>){
//        wineService.getWineDetail(Wid).enqueue(object : Callback<WineDTO> {
//            override fun onResponse(call: Call<WineDTO>, response: Response<WineDTO>) {
//                if (response.isSuccessful) {
////                    mutableWine.value = response.body()
//                    callback.onSuccess(response.body())
//                    Log.d("RDS","Response 성공 response.body: "+response.body().toString())
////                    Log.d("RDS","Response 성공 mutableWine : "+ mutableWine.value.toString())
//                }
//            }
//            override fun onFailure(call: Call<WineDTO>, t: Throwable) {
//                callback.onFailure(t)
//            }
//        })
//    }



}