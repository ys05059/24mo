package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.CartItem
import Util.WineDTO
import Util.price_format
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        Log.d(TAG, "장바구니에 " +vm.get_cartItem_count().toString() + "개가 있습니다")

        if(!vm.shoppingCartList.value.isNullOrEmpty()){
            val shoppingCartListAdapter = ShoppingCartListAdapter(vm.shoppingCartList,this)
            binding.cartRecyclerView.adapter = shoppingCartListAdapter

            // 최종 금액 최신화하기
            var total_price : Int = 0
            var total_count :Int
            vm.shoppingCartList.observe(this, Observer{
                shoppingCartListAdapter.setData(it)
                // 선택된 와인들만 최종 금액 계산
                total_price = 0
                total_count = 0
                it.forEach{
                    total_price += it.wine.W_price.toInt() * it.count
                    total_count += it.count
                    Log.d(TAG,"W_price :  " + it.wine.W_price)
                    Log.d(TAG,"count :  " + it.count)
                }
                Log.d(TAG,"total_price :  " + total_price)
                binding.totalPrice.text= price_format(total_price.toString())

                binding.totalCount.text ="총 "+total_count.toString() +" 병"
            })

            shoppingCartListAdapter.setItemClickListener(object : ShoppingCartListAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int,wineDTO: WineDTO) {
                    vm.setWineDetail(wineDTO)                                   // 상세 조회할 와인 정보 넘겨주기
                    val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                    info_frag.show(childFragmentManager,"Information")
                }
            })
        }

        // ItemTouchHelper 구현 (스와이프해서 삭제하기 위함)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Adapter에 아이템 삭제 요청
                (binding.cartRecyclerView.adapter as ShoppingCartListAdapter).deleteItem(viewHolder.adapterPosition)
//                Log.d(TAG,"check : " +vm.shoppingCartList.value?.get(viewHolder.adapterPosition))
                Log.d(TAG,"check : " +viewHolder.adapterPosition)
            }
        }).apply {
            // ItemTouchHelper에 RecyclerView 설정
            attachToRecyclerView(binding.cartRecyclerView)
        }

        // 닫기 버튼
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        // 결제 버튼
        binding.payButton.setOnClickListener {
            PayingFragment().show((activity as MainActivity).fragmentManager,"PayingFragment")
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        dismiss()
    }

    override fun plusCount(item: CartItem) {
        vm.cartItem_count_plus(item)
    }
    override fun minusCount(item: CartItem){
        vm.cartItem_count_minus(item)
    }
    override fun deleteItem(item: CartItem) {
        vm.delete_cartItem(item)
    }

}