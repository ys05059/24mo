package Main

import android.util.Log
import androidx.lifecycle.*
import Util.WineDTO
import Util.WineRemoteDataSource
import kotlinx.coroutines.*

class MainViewModel :  ViewModel(){
    private var _liveWineDetail = MutableLiveData<WineDTO>()
    // field를 생성하는 동시에 field의 값을 할당, =로 대입한 변수의 getter는 field의 값을 리턴
    val wineDetail : MutableLiveData<WineDTO>
        get() = _liveWineDetail

    //코루틴하면서 생성
    val wineService = WineRemoteDataSource.getWineService()
    var job : Job? = null

    // 추천 관련 data들
    var Recommend_First_Tag  :String = ""
    var Recommend_Second_Tag : String = ""
    var Recommend_Is_Back : Int = 1

//    var R_first_tag : String
//        get() = Recommend_First_Tag
//        set(tag) {
//            Recommend_First_Tag = tag
//        }



    fun getWineDetail(Wid : Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response  = wineService.getWineDetail(Wid)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    _liveWineDetail.value= response.body()!!
                    Log.d("Test" , "코루틴 테스팅 중" +_liveWineDetail.value.toString())

                }
            }
        }
    }





    // 콜백 사용
//    fun getWineDetail(Wid : Int) {
//        WineRepository.getWineDetail(Wid,object : WineRepository.GetDataCallback<WineDTO>{
//            override fun onSuccess(data: WineDTO?) {
//                data?.let{
//                    _liveWineDetail = it
//                    Log.d("MainVM","_liveWineDetail : "+_liveWineDetail.toString())
//                }
//            }
//
//            override fun onFailure(throwable: Throwable) {
//                throwable.printStackTrace()
//            }
//        })
//    }
//
//    //test용
//    fun setWD(Wid : Int){
//        _liveWineDetail = WineDTO(Integer.toString(Wid),"와인이름1")
//    }
}