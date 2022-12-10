package Main

import Util.AdminDTO
import Util.CartItem
import android.util.Log
import androidx.lifecycle.*
import Util.WineDTO
import Util.WineRemoteDataSource
import android.view.Window
import kotlinx.coroutines.*
import java.time.LocalDate

class MainViewModel :  ViewModel(){
    private val  TAG = "MainViewModel"
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


    var Search_Is_Back : Int = 1
        //여기
    private val _RecommendWineList = MutableLiveData<ArrayList<CartItem>>()
    val RecommendWineList : LiveData<ArrayList<CartItem>> get() = _RecommendWineList
    fun addWine_RecommendList(wine : WineDTO){
        var recommendList = _RecommendWineList.value
        if(recommendList == null){
            recommendList = ArrayList<CartItem>()
        }
        var exist = false
        recommendList.forEach {
            if(it.wine.Wid == wine.Wid){
                it.count++
                exist= true
            }
        }
        if(!exist){
            recommendList?.add(CartItem(wine))
        }
        _RecommendWineList.value = recommendList!!
    }

    // 장바구니 리스트
    private val _shoppingCartList = MutableLiveData<ArrayList<CartItem>>()
    val shoppingCartList : LiveData<ArrayList<CartItem>> get() = _shoppingCartList

    // 장바구니에 와인 추가
    fun addWine_CartList(wine: WineDTO){
        var cartList =_shoppingCartList.value
        if( cartList == null){
            cartList = ArrayList<CartItem>()
        }
        // 이미 있는 장바구니에 있는 와인일 경우 따로 처리
        var exist = false
        cartList.forEach{
            if(it.wine.Wid == wine.Wid) {
                it.count++
                exist = true
            }
        }
        if(!exist){
            cartList?.add(CartItem(wine))
        }
        _shoppingCartList.value = cartList!!
        Log.d(TAG,"장바구니에 " + wine.Wid + " " + wine.W_name + " 가 추가되었습니다" )
        Log.d(TAG, shoppingCartList.value.toString())
    }

    fun count_plus(item:CartItem){
        var cartList =_shoppingCartList.value
        if( cartList != null){
            cartList.forEach {
                if(it.wine.Wid == item.wine.Wid) {
                    it.count++
                }
            }
        }
        _shoppingCartList.value = cartList!!
        Log.d(TAG,"장바구니의 " + item.wine.Wid + " " + item.wine.W_name + " 가 1개 추가되었습니다" )
    }

    fun count_minus(item:CartItem){
        var cartList =_shoppingCartList.value
        if( cartList != null){
            cartList.forEach {
                if(it.wine.Wid == item.wine.Wid && it.count>0) {
                    it.count--
                    Log.d(TAG,"장바구니의 " + item.wine.Wid + " " + item.wine.W_name + " 가 1개 삭제되었습니다" )
                }
            }
        }
        _shoppingCartList.value = cartList!!
    }

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


    //admin Data
    private val _dailyList = MutableLiveData<ArrayList<AdminDTO>>()
    val dailyList : LiveData<ArrayList<AdminDTO>> get() = _dailyList

    fun getAdminData(){
        job = CoroutineScope(Dispatchers.IO).launch {
            //임시
            val date = "2022-12-10"
            val response  = wineService.getDaily(date)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    //임시 반환값?
                    //_dailyList.value= response.body()!!
                    Log.d("Test" , "코루틴 테스팅 중" +_dailyList.value.toString())
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