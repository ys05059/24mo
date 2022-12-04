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

    // 장바구니 LIST
    private val _shoppingCartList = MutableLiveData<ArrayList<CartItem>>()
    val shoppingCartList : LiveData<ArrayList<CartItem>> get() = _shoppingCartList

    // 장바구니에 와인 추가
    fun addWine_CartList(wine: WineDTO){
        var cartList =_shoppingCartList.value
        if( cartList == null){
            cartList = ArrayList<CartItem>()
        }
        cartList?.add(CartItem(wine))
        _shoppingCartList.value = cartList!!
        Log.d(TAG,"장바구니에 " + wine.Wid + " " + wine.W_name + " 가 추가되었습니다" )
        Log.d(TAG, shoppingCartList.value.toString())
    }

    // 장바구니에서 와인 삭제
    fun deleteWine_CartList(Wid:Int){
        val iter = _shoppingCartList.value!!.iterator()
        lateinit var item: CartItem
        while(iter.hasNext()){
            item = iter.next()
            if(item.wine.Wid.equals(Wid)){
                _shoppingCartList.value!!.remove(item)
                Log.d(TAG,"장바구니에서 " + item.wine.Wid + " " + item.wine.W_name + " 가 삭제되었습니다" )
            }
        }
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