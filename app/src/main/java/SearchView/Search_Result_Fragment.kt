package SearchView

import HomeView.InformationFragment
import Main.MainActivity
import Main.MainViewModel
import PaymentView.ShoppingCartDialogFragment
import RecommendView.RecommendListAdapter
import Model.WineDTO
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailResultBinding

class Search_Result_Fragment : Fragment() {
    private lateinit var vm: MainViewModel
    private var _binding: FragmentDetailResultBinding? = null
    private val binding get() = _binding!!
    private val  TAG = "Search_result_Frag"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailResultBinding.inflate(inflater, container, false)
        var view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        val error_binding = ResultErrorPageBinding.inflate(inflater, container, false)

        Log.d(TAG, "viewModel로 SearchList 받아오기")
        vm.getSearchList(vm.Detail_Parameter)

        // 뒤로가기 버튼 보이게 하기
        val ParentFragment : Search_Main_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Search_Main_Fragment
        ParentFragment.invisible_back_btn(false)

        binding.DetailResultRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        lateinit var searchListAdapter : RecommendListAdapter


        vm.wineList.observe(viewLifecycleOwner, Observer{
            if(!vm.wineList.value.isNullOrEmpty()){
                searchListAdapter= RecommendListAdapter(vm.wineList)
                binding.DetailResultRecyclerView.adapter = searchListAdapter
                searchListAdapter.setItemClickListener(object : RecommendListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                        vm.setWineDetail(wineDTO)                                   // 상세 조회할 와인 정보 넘겨주기
                        val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                        info_frag.show(childFragmentManager,"Recommend_Result")
                    }
                })
                // 체크박스 눌렀을 때 반응하기
                searchListAdapter.setCheckBoxClickListener(object : RecommendListAdapter.OnCheckBoxClickListener{
                    override fun onClick(v: View, position: Int,checked : Boolean) {
                        vm.wineList.value?.get(position)?.checked = checked
                        Log.d(TAG,vm.wineList.value?.get(position)?.W_name +" 이 " + vm.wineList.value?.get(position)?.checked +"로 변경되었습니다")
                    }
                })
            } // 검색 결과가 없을 경우
            else{
                Log.d(TAG,"test 중 - 들어왔다" )
//                Toast.makeText((activity as MainActivity), "검색 결과가 없습니다",
//                    Toast.LENGTH_SHORT).show()
//                view =error_binding.root
            }
        })

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
        binding.addCartBtn.setOnClickListener {
            vm.addWineList_CartList()
            // 체크 됐던 item들 해제
            vm.reset_WineList_Checked()
            // 화면 새로 refresh하기
            binding.DetailResultRecyclerView.removeAllViewsInLayout()
            vm.wineList.observe(viewLifecycleOwner, Observer{
                if(!vm.wineList.value.isNullOrEmpty()){
                    searchListAdapter= RecommendListAdapter(vm.wineList)
                    binding.DetailResultRecyclerView.adapter = searchListAdapter
                }
                searchListAdapter.setItemClickListener(object : RecommendListAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                        vm.setWineDetail(wineDTO)                                   // 상세 조회할 와인 정보 넘겨주기
                        val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                        info_frag.show(childFragmentManager,"Recommend_Result")
                    }
                })
                // 체크박스 눌렀을 때 반응하기
                searchListAdapter.setCheckBoxClickListener(object : RecommendListAdapter.OnCheckBoxClickListener{
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

}