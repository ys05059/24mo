package Main

import Util.CartItem
import android.util.Log
import androidx.lifecycle.*
import Util.WineDTO
import Util.WineRemoteDataSource
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Window
import android.widget.Button
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
    var Detail_food : String = ""
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

    //특정문자열 글자 크기 바꿈
    fun Change_font_size(btn : Button, tag: String){
        //태그만 임의로 설정하셔서 파라미터로 넘기면서 사용하시면 될것같습니다.
        lateinit var content :String //버튼에 출력할 텍스트내용
        var start : Int = 0  //특정 문자열을 바꿀 시작위치

        //텍스트내용 btn.text.toString() 쓰지않는 이유
        // -> 필터를 설정후, 다시들어가서 설정시 문자열뒤에 계속붙음 (layout의 xml파일의 초기 텍스트로 초기화 필요)
        when(tag){

            "price"-> content = "      가격대" + "    \t ${minPrice}만원~${maxPrice}만원 사이"
            "food" -> content = "\n음식"+"\n ${Detail_food}"
        }
        var spanningString : SpannableString = SpannableString(content)

        if(tag == "price") //가격대는 줄바꿈 \n이 아닌  띄어쓰기 \t이므로
        {
            start = content.indexOf("\t ")
        }
        else
        {
            start = content.indexOf(" ")
        }
        val end : Int = content.length //끝지점
        //0.5f ->기존보다 0.5배
        spanningString.setSpan(RelativeSizeSpan(0.5f),start,end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자 크기 바꾸기
//        spanningString.setSpan(ForegroundColorSpan(Color.parseColor("#000000")),start,end,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자색상바꾸기
//        spanningString.setSpan(StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자 스타일바꾸기(굵게, 기울이기등)
        btn.setText(spanningString)
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