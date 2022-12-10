package Main

import Util.CartItem
import android.util.Log
import androidx.lifecycle.*
import Util.WineDTO
import Util.WineRemoteDataSource
import android.view.Window
import kotlinx.coroutines.*

class MainViewModel :  ViewModel(){
    private val  TAG = "MainViewModel"
    val wineService = WineRemoteDataSource.getWineService()
    var job : Job? = null

    // 와인 1개 상세정보 받아옴
    private var _liveWineDetail = MutableLiveData<WineDTO>()
    val wineDetail : MutableLiveData<WineDTO> get() = _liveWineDetail                                    // field를 생성하는 동시에 field의 값을 할당, =로 대입한 변수의 getter는 field의 값을 리턴

    // 와인 추천 리스트 상세정보 받아옴
    private  var _liveRecommendList = MutableLiveData<ArrayList<WineDTO>>()
    val recommendList : LiveData<ArrayList<WineDTO>>  get() = _liveRecommendList

    // 추천 관련 변수들
    var Recommend_First_Tag  :String = ""
    var Recommend_Second_Tag : String = ""
    var Recommend_Is_Back : Int = 1

    //상세검색 관련 변수들
    var minPrice : Int = 0
    var maxPrice : Int = 0
    var Search_Is_Back : Int = 1

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

    fun setWineDetail(wine: WineDTO){
        _liveWineDetail.value = wine
    }

    // Retrofit 비동기 통신 - Wid로 와인 상세정보 받아오기
    fun getWineDetail(Wid : Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response  = wineService.getWineDetail(Wid)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    _liveWineDetail.value= response.body()!!
                    Log.d(TAG , "getWineDetail 테스트 : " +_liveWineDetail.value.toString())
                }
            }
        }
    }

    // Retrofit 비동기 통신 - Tag 2개로 추천 와인 리스트 받아오기
    fun getRecommnedList(Tag1 : String, Tag2 : String){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = wineService.getRecommendList(Tag1,Tag2)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    _liveRecommendList.value = response.body()!!.recommend_list
                    Log.d(TAG , "getRecommendList 테스트 : " +_liveRecommendList.value.toString())
                }
            }

        }
    }

    fun cartItem_count_plus(item:CartItem){
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

    fun cartItem_count_minus(item:CartItem){
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