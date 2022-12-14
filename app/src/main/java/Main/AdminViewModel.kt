package Main

import Util.DailySumDTO
import Util.SalesDTO
import Util.WineRemoteDataSource
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class AdminViewModel : ViewModel() {
    private val  TAG = "AdminViewModel"
    val wineService = WineRemoteDataSource.getWineService()
    var job : Job? = null


    //admin Data
    private val _dailySumList = MutableLiveData<ArrayList<DailySumDTO>>()
    val dailySumList : LiveData<ArrayList<DailySumDTO>> get() = _dailySumList

    // 매출 정보
    private val _salesList = MutableLiveData<ArrayList<SalesDTO>>()
    val SalesList : LiveData<ArrayList<SalesDTO>> get() = _salesList

    fun getDailySumList(){
        job = CoroutineScope(Dispatchers.IO).launch {
            //임시
            val response  = wineService.getDailySumList()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    _dailySumList.value= response.body()!!.dailySumList
                    Log.d("Test" , "코루틴 테스팅 중" +_dailySumList.value.toString())
                }
            }
        }
    }

    fun getSalesList(date: String){
        job = CoroutineScope(Dispatchers.IO).launch {
            //임시
            val response  = wineService.getSalesList(date)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    //임시 반환값?
                    _salesList.value= response.body()!!.salesList
                    Log.d("Test" , "salesList 테스팅 중" +_salesList.value.toString())
                }
            }
        }
    }

}