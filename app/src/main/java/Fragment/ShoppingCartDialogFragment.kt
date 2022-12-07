package Fragment

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.d(TAG, vm.shoppingCartList.value.toString())

        if(!vm.shoppingCartList.value.isNullOrEmpty()){
            val adapter = ShoppingCartListAdapter(vm.shoppingCartList,this)
            binding.recyclerView.adapter = adapter

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


//        var actionBar = (activity as MainActivity?)!!.supportActionBar
//
        //actionBar?.setCustomView(R.id.menu_friend)

//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)

//        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
//            "프랑스산", "높음", "낮음", "가벼움", "중간", "\\35,000", "10%"))
//        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리2","R.drawable.wine2","화이트와인",
//            "프랑스산", "중간", "중간", "무거움", "낮음","\\50,000", "10%"))
//        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리3","R.drawable.wine1","로제와인",
//            "프랑스산", "높음", "높음", "중간", "중간", "\\25,000", "10%"))
//        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리4","R.drawable.wine2","레드와인",
//            "프랑스산", "낮음", "낮음", "가벼움", "높음", "\\45,000", "10%"))

//        binding.recyclerView.adapter = shoppingCartListAdapter
//        shoppingCartListAdapter.setItemClickListener(object : ShoppingCartListAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                //loadFragment()
//            }
//        })

        return binding.root
    }

    override fun plusCount(item: CartItem) {
        vm.count_plus(item)
    }
    override fun minusCount(item: CartItem){
        vm.count_minus(item)
    }

    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}