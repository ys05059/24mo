package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.HomeLayoutBinding
import com.example.a24mo.databinding.RecommendWineFragment1Binding

class Recommend_First_Fragment : Fragment(){
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineFragment1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RecommendWineFragment1Binding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val slide_delay: Long = 500 //다음 step으로 넘어갈때 딜레이시간
        if(!vm.Recommend_First_Tag.equals("")){
            find_view_byName(vm.Recommend_First_Tag)?.setBackgroundResource(R.drawable.button_round_white_red)
        }

        val ParentFragment : Recommend_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Recommend_Fragment
        ParentFragment.invisible_back_btn(true)

        val listener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!vm.Recommend_First_Tag.equals("")) {//선택된것이 없음. =>선택시 바로 테두리 빨갛게
                    // 기존 선택된 버튼의 테두리 제거 후 새로 선택한 버튼에 테두리 적용
                    find_view_byName(vm.Recommend_First_Tag)?.setBackgroundResource(R.drawable.button_less_round_white)
                }
                v?.setBackgroundResource(R.drawable.button_round_white_red)
                // viewmodel에 선택한 태그 정보 저장
                vm.Recommend_First_Tag = find_name_byId(v?.id)
                vm.Recommend_Is_Back = 1
                Toast.makeText((activity as MainActivity), find_name_byId(v?.id)+" 태그가 선택되었습니다",
                    Toast.LENGTH_SHORT).show()

                // 프래그먼트 전환
                Handler(Looper.getMainLooper()).postDelayed({
                    (activity as MainActivity).changeRecommendFragment(2,0)
            }, slide_delay)
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

    fun find_view_byName(tag : String): View?{
        var v :View? = null
        when(tag){
            "달달한" -> v = binding.tag1
            "선물하기 좋은" -> v = binding.tag2
            "가장 많이 팔린" -> v = binding.tag3
            "드라이한" -> v = binding.tag4
        }
        return v
    }

    fun find_name_byId(view_id : Int?) :String {
        var result : String = "null"
        when(view_id){
            binding.tag1.id -> result = binding.tag1.getText().toString().substring(1)
            binding.tag2.id -> result = binding.tag2.getText().toString().substring(1)
            binding.tag3.id -> result = binding.tag3.getText().toString().substring(1)
            binding.tag4.id -> result = binding.tag4.getText().toString().substring(1)
        }
        return result
    }


}