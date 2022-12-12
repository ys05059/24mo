package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailResultBinding

class Detail_Search_Fragment_result : Fragment() {
    private lateinit var vm: MainViewModel
    private var _binding: FragmentDetailResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailResultBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val ParentFragment : Detail_Search_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Detail_Search_Fragment
        ParentFragment.invisible_back_btn(false)

        binding.DetailResultRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        //더미데이터
        var resultList = mutableListOf<ShoppingCart>()
        resultList.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
            "프랑스산", "높음", "낮음", "가벼움", "중간", "\\35,000", "10%"))

        resultList.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
            "프랑스산", "높음", "낮음", "가벼움", "중간", "\\35,000", "10%"))

        var resultAdapter = DetailResultAdapter(resultList)

        binding.DetailResultRecyclerView.adapter = resultAdapter



        binding.basket.setText("0")
//        if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
//        {
//            binding.basket.setText("0")
//        }
//        else{
//            binding.basket.setText(vm.recommendList.value?.size.toString())
//        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}