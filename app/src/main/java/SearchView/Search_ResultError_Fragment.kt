package SearchView

import Main.MainActivity
import Main.MainViewModel
import PaymentView.ShoppingCartDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailResultBinding
import com.example.a24mo.databinding.ResultErrorPageBinding

class Search_ResultError_Fragment : Fragment(){
    private lateinit var vm: MainViewModel
    private var _binding: ResultErrorPageBinding? = null
    private val binding get() = _binding!!
    private val  TAG = "Search_resultError_Frag"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResultErrorPageBinding.inflate(inflater, container, false)
        var view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val ParentFragment : Search_Main_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Search_Main_Fragment
        ParentFragment.invisible_back_btn(false)

        vm.shoppingCartList.observe(viewLifecycleOwner, Observer{
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

        return view

    }

}