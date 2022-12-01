package com.example.a24mo.shoppingCart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.R
import com.example.a24mo.databinding.ShoppingCartRecyclerviewBinding
import com.example.fitapet.navfragment.ShoppingCart

class ShoppingCartListAdapter(val shoppingCart:MutableList<ShoppingCart>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: ShoppingCartRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){

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
        val binding = (holder as MyViewHolder).binding
        binding.wineName.text=shoppingCart[position].name
        binding.wineImg.setImageResource(R.drawable.wine1)
        binding.wineCategory.text=shoppingCart[position].category
        binding.wineFrom.text=shoppingCart[position].from
        binding.wineSweet.text=shoppingCart[position].sweet
        binding.wineSour.text=shoppingCart[position].sour
        binding.wineBody.text=shoppingCart[position].body
        binding.wineTanin.text=shoppingCart[position].tanin
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
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return shoppingCart.size
    }
}
