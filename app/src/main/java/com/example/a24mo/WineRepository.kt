package com.example.a24mo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// MVVM 패턴을 위해 데이터 통신을 하는 Repository
object WineRepository {
    private val remoteDataSource = WineRemoteDataSource

    //코루틴 방식
//    suspend fun getWineDetail(Wid :Int) = remoteDataSource.wineService.getWineDetail(Wid)




//    fun getWineDetail(Wid : Int, callback : GetDataCallback<WineDTO>){
//        remoteDataSource.getWineDetail(Wid,callback)
//    }

//    interface GetDataCallback<T>{
//        fun onSuccess(data:T?)
//        fun onFailure(throwable: Throwable)
//    }


    //Retrofit 객체 생성
//    private val client = WineRemoteDataSource.getInstance().create(WineService::class.java)
//    private val data : MutableLiveData<WineDTO> = MutableLiveData()

    // Wid로 WineDetail 가져오는 함수
//    fun setWineDetail(Wid : Int) {
////        val call : WineDTO = client.getWineDetail(Wid).execute().body()!!
//        client.getWineDetail(Wid).enqueue(object : Callback<WineDTO> {
////            override fun onResponse(call: Call<WineDTO>, response: Response<WineDTO>) {
////                if (response.isSuccessful) {
////                    // 정상적으로 통신이 성공된 경우
////                    data.value = response.body()!!
//                    data.postValue(response.body()!!)
//                    Log.d("YMC", "onResponse 성공: " + data.value.toString())                             //여기까지는 정상 출력되는데 뭐가문제야
//                } else {
//                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
//                    Log.d("YMC", "onResponse 실패")
//                }
//            }
//            override fun onFailure(call: Call<WineDTO>, t: Throwable) {
//                Log.d(ContentValues.TAG, "실패 : ${t.toString()}")
//            }
//        })
////        Log.d("YMC", "Repository에서 반환 성공: " + data.value.toString())
////        Log.d("YMC", "동기 반환값 : " + call.toString())
//    }
//
//    fun getWineDetail() : LiveData<WineDTO>{
//        Log.d("YMC", "Repository에서 반환 성공: " + data.value.toString())
//        return this.data
//    }
}