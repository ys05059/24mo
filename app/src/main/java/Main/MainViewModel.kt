package Main

import Model.SearchWineParmeter
import Model.CartItem
import android.util.Log
import androidx.lifecycle.*
import Model.WineDTO
import Model.WineRemoteDataSource
import android.os.Build
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.Button
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class MainViewModel :  ViewModel(){
    private val  TAG = "MainViewModel"
    val wineService = WineRemoteDataSource.getWineService()
    var job : Job? = null

    // 와인 1개 상세정보 받아옴
    private var _liveWineDetail = MutableLiveData<WineDTO>()
    val wineDetail : MutableLiveData<WineDTO> get() = _liveWineDetail                                    // field를 생성하는 동시에 field의 값을 할당, =로 대입한 변수의 getter는 field의 값을 리턴

    // 와인 추천 리스트 상세정보 받아옴
    private  var _liveWineList = MutableLiveData<ArrayList<WineDTO>>()
    val wineList : MutableLiveData<ArrayList<WineDTO>>  get() = _liveWineList

    // 추천 관련 변수들
    var Recommend_First_Tag  :String = ""
    var Recommend_Second_Tag : String = ""
    var Recommend_Is_Back : Int = 1

    //상세검색 관련 변수들
    var Search_Is_Back : Int = 0

    var Detail_Parameter = SearchWineParmeter() // 와인 검색창에 들어가는 파라미터들 다 class로 묶었음

    // 장바구니 리스트
    private var _shoppingCartList = MutableLiveData<ArrayList<CartItem>>()
    val shoppingCartList : MutableLiveData<ArrayList<CartItem>> get() = _shoppingCartList

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

    fun addWineList_CartList(){
        var cartList =_shoppingCartList.value
        var tempItem : CartItem
        var exist = false
        var temp_Wid :String
        if( cartList == null){
            cartList = ArrayList<CartItem>()
        }
        _liveWineList.value?.forEach{
            temp_Wid = it.Wid
            if(it.checked){
                exist = false
                // 장바구니에 담겨있는지 확인, 담겨있다면 1개 추가, 없다면 담기
                cartList.forEach{
                    if(it.wine.Wid == temp_Wid) {
                        it.count++
                        exist = true
                    }
                }
                if(!exist){
                    tempItem = CartItem(1,it)
                    cartList?.add(tempItem)
                    Log.d(TAG,"장바구니에 " + it.Wid + " " + it.W_name + " 가 추가되었습니다" )
                }else{

                    Log.d(TAG, it.W_name +"은 이미 담겨있고 하나 추가했습니다")
                }
            }
        }
        _shoppingCartList.value = cartList!!
        Log.d(TAG, "쇼핑카트 리스트 : "+shoppingCartList.value.toString())
    }

    fun setWineDetail(wine: WineDTO){
        _liveWineDetail.value = wine
    }
    // LocalDate 사용을 위해 버전 맞춰주기. 지금 최소버전 21인데 LocalDate는 26부터 가능

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveSales(){
        var cartList =_shoppingCartList.value
        val date : String =LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//        val format = SimpleDateFormat("y-m-d")
//        val date :String = format.format(now)
        if( cartList != null){
            cartList.forEach {
                Log.d(TAG," date :" + date + " wid : " + it.wine.Wid.toInt() + " count: "+ it.count)
                job = CoroutineScope(Dispatchers.IO).launch {
                    val response  = wineService.insertSales(date,it.wine.Wid.toInt(),it.count)
                    withContext(Dispatchers.Main){
                        if(response.isSuccessful){
                            Log.d(TAG , "saveSales 테스트 : "+ it.wine.W_name +" 의 insert "+response.body()!!.status)
                        }
                    }
                }
            }
        }
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
            Log.d(TAG,Tag1 + " "+Tag2 )
            val response = wineService.getRecommendList(Tag1,Tag2)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    if(response.body()!!.status.equals("404")){
                        _liveWineList.value = ArrayList<WineDTO>()
                    }else{
                        _liveWineList.value = response.body()!!.wine_list
                    }
                    Log.d(TAG , "getRecommendList 테스트 : " +_liveWineList.value.toString())
                }
            }

        }
    }
    fun getSearchList(swp : SearchWineParmeter){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = wineService.getSearchList(swp.name,swp.min_price,swp.max_price,swp.type,swp.region,swp.alcohol,swp.food,swp.sweet,swp.acidity,swp.body,swp.tannin)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    // 데이터가 없을 경우
                    if(response.body()!!.status.equals("404")){
                        _liveWineList.value = ArrayList<WineDTO>()
                    }else{
                        _liveWineList.value = response.body()!!.wine_list
                    }
                        Log.d(TAG , "getSearchList 테스트 : " +_liveWineList.value.toString())
                }
            }

        }
    }
    fun get_cartItem_count(): Int {
        var cartList =_shoppingCartList.value
        var count = 0
        if( cartList != null){
            cartList.forEach {
                count +=it.count
            }
        }
        Log.d(TAG,"장바구니에 " + count + " 개 담겨있습니다")
        return count
    }

    fun cartItem_count_plus(item: CartItem){
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

    fun cartItem_count_minus(item: CartItem){
        var cartList =_shoppingCartList.value
        if( cartList != null){
            cartList.forEach {
                if(it.wine.Wid == item.wine.Wid && it.count>1) {
                    it.count--
                    Log.d(TAG,"장바구니의 " + item.wine.Wid + " " + item.wine.W_name + " 가 1개 삭제되었습니다" )
                }
            }
        }
        _shoppingCartList.value = cartList!!
    }

    fun delete_cartItem(item: CartItem){
        var cartList =_shoppingCartList.value
        if(cartList != null){
            cartList.forEach{
                if(it.wine.Wid == item.wine.Wid) {
                    cartList.remove(item)
                }
            }
        }
        _shoppingCartList.value = cartList!!
        Log.d(TAG,item.wine.W_name +" 가 장바구니에서 삭제되었습니다")
    }

    fun reset_WineList_Checked(){
        wineList.value?.forEach{
            it.checked = false
        }
    }

    fun Change_font_size(btn : Button, tag: String){
        //태그만 임의로 설정하셔서 파라미터로 넘기면서 사용하시면 될것같습니다.
        lateinit var content :String //버튼에 출력할 텍스트내용
        var start : Int = 0  //특정 문자열을 바꿀 시작위치
        var temp_taste = arrayOf<String>("","","","")

        if(tag == "others")
        {
            when(Detail_Parameter.sweet)
            {
                0->temp_taste[0] = "상관없음"
                1->temp_taste[0] = "낮음"
                2 -> temp_taste[0] = "중간"
                3 -> temp_taste[0] = "높음"
            }

            when(Detail_Parameter.acidity)
            {
                0->temp_taste[1] = "상관없음"
                1->temp_taste[1] = "낮음"
                2 -> temp_taste[1] = "중간"
                3 -> temp_taste[1] = "높음"
            }

            when(Detail_Parameter.body)
            {
                0->temp_taste[2] = "상관없음"
                1->temp_taste[2] = "가벼움"
                2 -> temp_taste[2] = "중간"
                3 -> temp_taste[2] = "무거움"
            }
            when(Detail_Parameter.tannin)
            {
                0->temp_taste[3] = "상관없음"
                1->temp_taste[3] = "가벼움"
                2 -> temp_taste[3] = "중간"
                3 -> temp_taste[3] = "무거움"
            }
        }
        //텍스트내용 btn.text.toString() 쓰지않는 이유
        // -> 필터를 설정후, 다시들어가서 설정시 문자열뒤에 계속붙음 (layout의 xml파일의 초기 텍스트로 초기화 필요)

//        가격: 최댓값이 상관없음일시 오류가 나므로 따로 태그를 빼서처리
        if(tag == "price")
        {
            if(Detail_Parameter.max_price > 200000)
            {
                content = "     가격대" + "    \t ${Detail_Parameter.min_price}원 ~ 상관없음"
            }
            else{
                content = "     가격대" + "    \t ${Detail_Parameter.min_price}원 ~ ${Detail_Parameter.max_price}원"
            }
        }

        if(tag == "alcohol")
        {
            when(Detail_Parameter.alcohol)
            {
                0 -> content = "\n도수"
                1-> content = "\n도수" + "\n 도수낮음"
                2->content = "\n도수" + "\n 도수중간"
                3->content = "\n도수" + "\n 도수높음"
            }
        }
        when(tag){
            "country" -> content = "\n생산지"+"\n ${Detail_Parameter.region}"
            "country_reset" ->content = "\n생산지"

            "food" -> content = "\n음식"+"\n ${Detail_Parameter.food}"
            "food_reset" ->content = "\n음식"
            "others" ->content = "   당도/산도/바디/타닌" + "\n     ${temp_taste[0]}/${temp_taste[1]}/${temp_taste[2]}/${temp_taste[3]}"
            "others_reset"->content = "   당도/산도/바디/타닌"

            "price_reset" -> content = "      가격대"
            "alcohol_reset" -> content ="\n도수"

            "kind"-> content ="\n종류" + "\n ${Detail_Parameter.type}"
            "kind_reset" -> content = "\n종류"

        }
        if(tag == "price" && Detail_Parameter.max_price>= 200000){
            content ="     가격대" + "    \t ${Detail_Parameter.min_price}원 ~ 상관없음"
        }
        var spanningString : SpannableString = SpannableString(content)

        if(tag == "price") //가격대는 줄바꿈 \n이 아닌  띄어쓰기 \t이므로
        {
            start = content.indexOf("\t ")
        }
        else if(tag == "others")
        {
            start = content.indexOf("\n     ")
        }
        else
        {
            start = content.indexOf("\n ")
        }
        val end : Int = content.length //끝지점
        //0.5f ->기존보다 0.5배
        if(tag == "others")
        {
            spanningString.setSpan(RelativeSizeSpan(0.7f),start,end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }else if(tag == "price_reset" || tag == "food_reset" || tag == "others_reset" || tag=="country_reset"|| tag=="alcohol_reset"||tag =="kind_reset"){
//            spanningString.setSpan(RelativeSizeSpan(1.0f),start,end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자 크기 바꾸기
            btn.text = content
        }
        else{
            if(tag == "alcohol" && Detail_Parameter.alcohol == 0)
            {
                btn.text = "\n도수"
            }
            else{
                spanningString.setSpan(RelativeSizeSpan(0.4f),start,end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자 크기 바꾸기
            }
        }
//        spanningString.setSpan(ForegroundColorSpan(Color.parseColor("#000000")),start,end,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자색상바꾸기
//        spanningString.setSpan(StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //글자 스타일바꾸기(굵게, 기울이기등)
        btn.setText(spanningString)
    }

    fun resetShoppingCartList(){
        _shoppingCartList = MutableLiveData(ArrayList())
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