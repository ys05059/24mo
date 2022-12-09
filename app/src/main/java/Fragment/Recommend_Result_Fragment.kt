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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineFragmentResultBinding

class Recommend_Result_Fragment : Fragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineFragmentResultBinding? = null
    private val binding get() = _binding!!
    private val  TAG = "Recommend_result"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendWineFragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.firstTag.text = vm.Recommend_First_Tag
        binding.secondTag.text = vm.Recommend_Second_Tag
        val ParentFragment : Recommend_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Recommend_Fragment
        ParentFragment.invisible_back_btn(false)

        val resultList= mutableListOf<ShoppingCart>()
        val recommendListAdapter= RecommendListAdapter(resultList)


        Log.d(TAG, vm.RecommendWineList.value.toString())

        resultList.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
            "프랑스산", "높음", "낮음", "가벼움", "중간", "\\35,000", "10%"))
        resultList.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
            "프랑스산", "높음", "낮음", "가벼움", "중간", "\\35,000", "10%"))
        binding.recommendRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recommendRecycler.adapter= recommendListAdapter

        if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
        {
            binding.basket.setText("0")
        }
        else{
            binding.basket.setText(vm.shoppingCartList.value?.size.toString())
        }

        return view
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
