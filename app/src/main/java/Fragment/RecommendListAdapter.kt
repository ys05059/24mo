package Fragment

import Util.CartItem
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineRecyclerviewBinding

class RecommendListAdapter( val recommendlist:MutableList<ShoppingCart>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(val binding: RecommendWineRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            RecommendWineRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.wineName.text = recommendlist[position].name
        binding.wineImg.setImageResource(R.drawable.wine1)
        binding.wineCategory.text = recommendlist[position].category
        binding.wineFrom.text = recommendlist[position].from
        binding.wineSweet.text = recommendlist[position].sweet
        binding.wineSour.text = recommendlist[position].sour
        binding.wineBody.text = recommendlist[position].body
        binding.wineTanin.text = recommendlist[position].tannin
        binding.winePrice.text = recommendlist[position].price
        binding.wineAlcohol.text = recommendlist[position].alcohol
        //체크박스 이벤트 리스너
        binding.checkbox.setOnClickListener {
            var checked: Boolean = binding.checkbox.isChecked
            if(checked)//체크되었음
            {
                binding.WineLists.setBackgroundResource(R.drawable.button_round_white_red)
            }
            else//체크해제
            {
                binding.WineLists.setBackgroundResource(R.drawable.button_round_white)
            }

        }
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return recommendlist.size
    }
}
