package RecommendView

import HomeView.InformationFragment
import PaymentView.ShoppingCartDialogFragment
import Main.MainActivity
import Main.MainViewModel
import Model.WineDTO
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        lateinit var recommendListAdapter : RecommendListAdapter
        vm.wineList.observe(viewLifecycleOwner, Observer{
            if(!vm.wineList.value.isNullOrEmpty()){
                recommendListAdapter= RecommendListAdapter(vm.wineList)
                binding.recommendRecyclerView.adapter = recommendListAdapter
            }
            recommendListAdapter.setItemClickListener(object :
                RecommendListAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                    vm.setWineDetail(wineDTO)                                   // 상세 조회할 와인 정보 넘겨주기
                    val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                    info_frag.show(childFragmentManager,"Recommend_Result")
                }
            })
            // 체크박스 눌렀을 때 반응하기
            recommendListAdapter.setCheckBoxClickListener(object :
                RecommendListAdapter.OnCheckBoxClickListener {
                override fun onClick(v: View, position: Int,checked : Boolean) {
                    vm.wineList.value?.get(position)?.checked = checked
                    Log.d(TAG,vm.wineList.value?.get(position)?.W_name +" 이 " + vm.wineList.value?.get(position)?.checked +"로 변경되었습니다")
                }
            })
        })

        // 장바구니 item 개수 업데이트
        vm.shoppingCartList.observe(viewLifecycleOwner,Observer{
            if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
            {
                binding.CartListBtn.setText("0")
            }
            else{
                binding.CartListBtn.setText(vm.get_cartItem_count().toString())
            }
        })


        // 장바구니 버튼 클릭시 액션
        binding.CartListBtn.setOnClickListener{
//            (activity as MainActivity).replaceTransaction(ShoppingCartDialogFragment())
            ShoppingCartDialogFragment().show((activity as MainActivity).fragmentManager,"shoppingCart")
        }

        // 담기 버튼 구현
        binding.recommendAddCartBtn.setOnClickListener {
            vm.addWineList_CartList()
            // 체크 됐던 item들 해제
            vm.reset_WineList_Checked()
            // 화면 새로 refresh하기
            binding.recommendRecyclerView.removeAllViewsInLayout()
            vm.wineList.observe(viewLifecycleOwner, Observer{
                if(!vm.wineList.value.isNullOrEmpty()){
                    recommendListAdapter= RecommendListAdapter(vm.wineList)
                    binding.recommendRecyclerView.adapter = recommendListAdapter
                }
                recommendListAdapter.setItemClickListener(object :
                    RecommendListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                        vm.setWineDetail(wineDTO)                                   // 상세 조회할 와인 정보 넘겨주기
                        val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                        info_frag.show(childFragmentManager,"Recommend_Result")
                    }
                })
                // 체크박스 눌렀을 때 반응하기
                recommendListAdapter.setCheckBoxClickListener(object :
                    RecommendListAdapter.OnCheckBoxClickListener {
                    override fun onClick(v: View, position: Int,checked : Boolean) {
                        vm.wineList.value?.get(position)?.checked = checked
                        Log.d(TAG,vm.wineList.value?.get(position)?.W_name +" 이 " + vm.wineList.value?.get(position)?.checked +"로 변경되었습니다")
                    }
                })
            })

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
