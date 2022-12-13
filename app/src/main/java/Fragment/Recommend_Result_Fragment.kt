package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.WineDTO
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

        Log.d(TAG, "viewModel로 RecommendList 받아오기")
        vm.getRecommnedList(vm.Recommend_First_Tag,vm.Recommend_Second_Tag)

//        상세 조회 페이지에 돌아가기 버튼 보이게 하기
//        val ParentFragment : Recommend_Fragment =
//            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Recommend_Fragment
//        ParentFragment.invisible_back_btn(false)

        binding.recommendRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        lateinit var recommendListAdapter :RecommendListAdapter
        vm.wineList.observe(viewLifecycleOwner,Observer{
            if(!vm.wineList.value.isNullOrEmpty()){
                recommendListAdapter= RecommendListAdapter(vm.wineList)
                binding.recommendRecyclerView.adapter = recommendListAdapter
            }
            // 각 상품 클릭 시 상세 페이지로 이동
            recommendListAdapter.setItemClickListener(object : RecommendListAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                    vm.setWineDetail(wineDTO)
                    val info_frag = InformationFragment()
                    info_frag.show(childFragmentManager,"Recommend_Result")
                }
            })
        })


        if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
        {
            binding.recommendCartBtn.setText("0")
        }else{
            binding.recommendCartBtn.setText(vm.wineList.value?.size.toString())
        }

        binding.recommendAddCartBtn.setOnClickListener {
            // 추천 리스트에서 체크 완료된 상품들 shopping Cart에 담기

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
