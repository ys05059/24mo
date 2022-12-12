package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.CartItem
import Util.price_format
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineFragmentResultBinding

class Recommend_Result_Fragment : Fragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineFragmentResultBinding? = null
    private val binding get() = _binding!!
    private val  TAG = "Recommend_result_Frag"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendWineFragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.firstTag.text = "#"+vm.Recommend_First_Tag
        binding.secondTag.text ="#"+vm.Recommend_Second_Tag

        Log.d(TAG, "여기요")
        vm.getRecommnedList(vm.Recommend_First_Tag,vm.Recommend_Second_Tag)

        val ParentFragment : Recommend_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Recommend_Fragment
        ParentFragment.invisible_back_btn(false)

        binding.recommendRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        vm.recommendList.observe(viewLifecycleOwner,Observer{
            if(!vm.recommendList.value.isNullOrEmpty()){
                val recommendListAdapter= RecommendListAdapter(vm.recommendList)
                binding.recommendRecyclerView.adapter = recommendListAdapter
            }
        })

        if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
        {
            binding.basket.setText("0")
        }
        else{
            binding.basket.setText(vm.recommendList.value?.size.toString())
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // 추천 리스트 DB로 부터 받아오기
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    override fun showdetail(item : CartItem){
//        //상세정보창 띄우기
//    }

}

//구현해야할것
//1. 상세검색 UI (가격대는 최대값 최솟값 설정해서 뷰모델에 넘기기)
//<추천페이지 결과>
//3. 장바구니 담기버튼
//4. 결제하기 버튼 ->카드 ->결제(홈으로)
