package RecommendView

import Model.WineDTO
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineRecyclerviewBinding

class RecommendListAdapter(private val recommendList: LiveData<ArrayList<WineDTO>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val  TAG = "RecommendListAdapter"
    inner class MyViewHolder(val binding: RecommendWineRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(wineDTO: WineDTO) = with(binding){
                // adapter로 넘어온 데이터 View로 바인딩해주기
                wine = wineDTO
            }
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
        recommendList.value?.get(position)?.let {
            (holder as MyViewHolder).bind(it)
            Log.d(TAG,"onBindViewHolder에서" +it.Wid + " " + it.W_name + "가 확인됨")
        }

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
            checkBoxClickListener.onClick(it,position,checked)
        }

        // 리스트 아이템 선택시 상세정보 페이지에 데이터 넘겨주기
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position,recommendList.value?.get(position)!!)
        }

    }


    override fun getItemCount(): Int {
        return recommendList.value?.size!!
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int,wineDTO: WineDTO)
    }
    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    interface OnCheckBoxClickListener{
        fun onClick(v:View,position:Int,checked : Boolean)
    }
    private lateinit var checkBoxClickListener: OnCheckBoxClickListener
    fun setCheckBoxClickListener(onCheckBoxClickListener: OnCheckBoxClickListener){
        this.checkBoxClickListener = onCheckBoxClickListener
    }

    fun resetAllCheckBox(){
        for(i in 1..itemCount){

            recommendList.value?.get(i)
        }
    }


}
