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
import com.example.a24mo.databinding.TempDetailSearchRecyclerBinding


class DetailResultAdapter( val resultlist:MutableList<ShoppingCart>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(val binding: TempDetailSearchRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            TempDetailSearchRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.wineName.text = resultlist[position].name
        binding.wineImg.setImageResource(R.drawable.wine1)
        binding.wineCategory.text = resultlist[position].category
        binding.wineFrom.text = resultlist[position].from
        binding.wineBody.text = resultlist[position].body
        binding.wineTanin.text = resultlist[position].tannin
        binding.winePrice.text = resultlist[position].price
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
        return resultlist.size
    }
}