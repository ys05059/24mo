package Fragment

import Util.CartItem
import Util.WineDTO
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.databinding.ShoppingCartRecyclerviewBinding
import java.text.DecimalFormat

class ShoppingCartListAdapter(private var shoppingCart: MutableLiveData<ArrayList<CartItem>>, listener : OnCartBtnClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var recycleViewItems = ArrayList<CartItem>()
    private val  TAG = "ShoppingCartListAdapter"

    private val mCallback_CartBtn = listener

    inner class MyViewHolder(val binding: ShoppingCartRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(cartItem : CartItem) = with (binding){
            // adapter로 넘어온 데이터 View로 바인딩해주기
            item = cartItem
            adapter = this@ShoppingCartListAdapter

            // + 버튼 클릭시 아이템 개수 1개 빼기
            plusBtn.setOnClickListener {
                Log.d(TAG,cartItem.wine.W_name +"의 + 버튼이 클릭 되었습니다")
                mCallback_CartBtn.plusCount(cartItem)
            }
            minusBtn.setOnClickListener {
                Log.d(TAG,cartItem.wine.W_name +"의 - 버튼이 클릭 되었습니다")
                mCallback_CartBtn.minusCount(cartItem)
            }
        }
        fun deleteSetting(cartItem: CartItem,position: Int) =with (binding){
            deleteBtn.setOnClickListener {
                Log.d(TAG,cartItem.wine.W_name +" 삭제 버튼이 클릭 되었습니다")
                mCallback_CartBtn.deleteItem(cartItem)
                notifyDataSetChanged()
                Log.d(TAG,shoppingCart.value?.size!!.toString() + " 가 장바구니에 남았습니다")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ShoppingCartRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        shoppingCart.value?.get(position)?.let {
            // 데이터 바인딩하기 - inner class 메소드 호출
            (holder as MyViewHolder).bind(it)
            Log.d(TAG, "onBindViewHolder에서" + it.wine.Wid + " " + it.wine.W_name + " 가 확인됨")
            (holder as MyViewHolder).deleteSetting(it,position)
        }
        // 리스트 아이템 선택시 상세정보 페이지에 데이터 넘겨주기
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position,shoppingCart.value?.get(position)!!.wine)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int,wineDTO: WineDTO)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    // + 버튼 클릭 했을 때를 위한 인터페이스
    interface OnCartBtnClickListener{
        fun plusCount(item : CartItem)
        fun minusCount(item :CartItem)
        fun deleteItem(item:CartItem)
    }


    override fun getItemCount(): Int {
        return shoppingCart.value?.size!!
    }

    fun setData(data : ArrayList<CartItem>){
        recycleViewItems = data
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        Log.d(TAG,shoppingCart.value?.get(position)!!.wine.W_name+" 의 삭제 액션이 실행 되었습니다")
        mCallback_CartBtn.deleteItem(shoppingCart.value?.get(position)!!)
        notifyDataSetChanged()
        Log.d(TAG,shoppingCart.value?.size!!.toString() + " 가 장바구니에 남았습니다")
    }

    fun feature_fun(value : String): String{
        var result = ""
        when(value){
            "1" -> result = "낮음"
            "2" -> result = "조금 낮음"
            "3" -> result = "적당함"
            "4" -> result = "조금 높음"
            "5" -> result = "높음"
        }
        return result
    }

    fun body_fun(value : String): String{
        var result = ""
        when(value){
            "1" -> result = "가벼움"
            "2" -> result = "조금 가벼움"
            "3"-> result = "적당함"
            "4" -> result = "조금 묵직함"
            "5" -> result = "묵직함"
        }
        return result
    }

    fun price_format(price : String) : String {
        val formatter  = DecimalFormat("#,###,###")
        return "\\" +formatter.format(price.toInt()) + "원"
    }

}