package Fragment

import Util.SalesDTO
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.databinding.FragmentAdminitemBinding
import java.text.DecimalFormat

class AdminListAdapter(private var adminList:LiveData<ArrayList<SalesDTO>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var recycleViewItems = ArrayList<SalesDTO>()
    private val  TAG = "AdminListAdapter"

    inner class MyViewHolder(val binding: FragmentAdminitemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(salesDTO: SalesDTO) = with (binding){
            // adapter로 넘어온 데이터 View로 바인딩해주기
            item = salesDTO
            adapter = this@AdminListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            FragmentAdminitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adminList.value?.get(position)?.let {
            // 데이터 바인딩하기 - inner class 메소드 호출
            (holder as MyViewHolder).bind(it)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return adminList.value?.size!!
    }

    fun setData(data : ArrayList<SalesDTO>){
        recycleViewItems = data
        notifyDataSetChanged()
    }

    fun price_format(price : String) : String {
        val formatter  = DecimalFormat("#,###,###")
        return "\\" +formatter.format(price.toInt()) + "원"
    }
}