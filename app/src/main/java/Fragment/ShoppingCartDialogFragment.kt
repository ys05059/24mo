package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.CartItem
import Util.price_format
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentShoppingCartDialogBinding
import kotlinx.coroutines.*


class ShoppingCartDialogFragment : DialogFragment(),ShoppingCartListAdapter.OnCartBtnClickListener {
    private val  TAG = "SC_DialogFragment"
    private  lateinit var vm : MainViewModel
    private var _binding: FragmentShoppingCartDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        // 풀스크린으로 보기 -> res/values/dialog_fullscreen.xml 참고
        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)

        //false로 설정해 주면 화면 밖 또는 뒤로가기 클릭 시 다이얼로그가 dismiss되지 않음
        isCancelable = true

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingCartDialogBinding.inflate(inflater,container,false)
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.cartRecyclerView.setHasFixedSize(true)
        Log.d(TAG, vm.shoppingCartList.value.toString())

        if(!vm.shoppingCartList.value.isNullOrEmpty()){
            val adapter = ShoppingCartListAdapter(vm.shoppingCartList,this)
            binding.cartRecyclerView.adapter = adapter

            // 최종 금액 최신화하기
            var total_price : Int = 0
            vm.shoppingCartList.observe(this, Observer{
                adapter.setData(it)
                // 선택된 와인들만 최종 금액 계산
                total_price = 0
                it.forEach{
                    total_price += it.wine.W_price.toInt() * it.count
                    Log.d(TAG,"W_price :  " + it.wine.W_price)
                    Log.d(TAG,"count :  " + it.count)

                }
                Log.d(TAG,"total_price :  " + total_price)
                binding.totalPrice.text= price_format(total_price.toString())
            })
        }

        // 닫기 버튼
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        // 결제 버튼
        binding.payButton.setOnClickListener {
            //(activity as MainActivity).replaceTransaction(PayingFragment())

        }
        return binding.root
    }

    override fun plusCount(item: CartItem) {
        vm.cartItem_count_plus(item)
    }
    override fun minusCount(item: CartItem){
        vm.cartItem_count_minus(item)
    }
}