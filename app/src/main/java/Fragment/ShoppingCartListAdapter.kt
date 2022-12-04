package Fragment

import Util.CartItem
import Util.WineDTO
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.R
import com.example.a24mo.databinding.ShoppingCartRecyclerviewBinding
import java.text.DecimalFormat

class ShoppingCartListAdapter(private var shoppingCart:LiveData<ArrayList<CartItem>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var recycleViewItems = ArrayList<CartItem>()
    private val  TAG = "ShoppingCartListAdapter"

    inner class MyViewHolder(val binding: ShoppingCartRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        // adapter로 넘어온 데이터 View로 바인딩해주기
        fun bind(cartItem : CartItem) = with (binding){
            item = cartItem
            adapter = this@ShoppingCartListAdapter
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
        }
    }
//        binding.wineName.text=shoppingCart[position].name
//        binding.wineImg.setImageResource(R.drawable.wine1)
//        binding.wineCategory.text=shoppingCart[position].category
//        binding.wineFrom.text=shoppingCart[position].from
//        binding.wineSweet.text=shoppingCart[position].sweet
//        binding.wineSour.text=shoppingCart[position].sour
//        binding.wineBody.text=shoppingCart[position].body
//        binding.wineTanin.text=shoppingCart[position].tannin
//        binding.winePrice.text=shoppingCart[position].price
//        binding.wineAlcohol.text=shoppingCart[position].alcohol
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return shoppingCart.value?.size!!
    }

    fun setData(data : ArrayList<CartItem>){
        recycleViewItems = data
        notifyDataSetChanged()
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