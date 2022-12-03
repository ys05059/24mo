package Fragment

import Main.MainViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineFragment2Binding

class Recommend_Second_Fragment : Fragment(){
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineFragment2Binding? = null
    private val binding get() = _binding!!
    var select_count = 0 //몇개 선택되었는가?(버튼을 한개만 선택하도록 통제하기위함)
//    var what_select = 0 //무엇이 선택되었는가. (기존 선택한 버튼을 취소하고 새로 버튼을 선택하기위함)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RecommendWineFragment2Binding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val slide_delay: Long = 500 //다음 step으로 넘어갈때 딜레이시간

//        if(activity.is_Back == 1 && activity.what_this_step == 1) //뒤로가기를 클릭했고, 현재 페이지가 step1이면 , 이미 선택한값은 빨간색 테두리 칠하기
//        {
//            if(activity.what_firstStep_select != -1)
//            {
//                tag_list[activity.what_firstStep_select].setBackgroundResource(R.drawable.button_round_white_red)
//                what_select = activity.what_firstStep_select
//                select_count = 1
//            }
//
//        }

        val listener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (select_count == 0) {//선택된것이 없음. =>선택시 바로 테두리 빨갛게
                    select_count++
                } else {
                    // 기존 선택된 버튼의 테두리 제거 후 새로 선택한 버튼에 테두리 적용
                    find_view_byId(vm.Recommend_First_Tag)?.setBackgroundResource(R.drawable.button_less_round_white)
                }
                v?.setBackgroundResource(R.drawable.button_round_white_red)
                // viewmodel에 선택한 태그 정보 저장
                vm.Recommend_First_Tag = v?.id!!
                // 프래그먼트 전환

            }
        }
        binding.tag1.setOnClickListener(listener)
        binding.tag2.setOnClickListener(listener)
        binding.tag3.setOnClickListener(listener)
        binding.tag4.setOnClickListener(listener)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun find_view_byId(view_id : Int?): View?{
        var v :View? = null
        when(view_id){
            binding.tag1.id -> v = binding.tag1
            binding.tag2.id -> v = binding.tag2
            binding.tag3.id -> v = binding.tag3
            binding.tag4.id -> v = binding.tag4
        }
        return v
    }
}