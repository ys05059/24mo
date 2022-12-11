package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailSearch1Binding

class KindDialog(context: Context){
    private val dialog = Dialog(context)
    //레이아웃을 보여줌.
    fun ShowKind(){
        dialog.setContentView(R.layout.detail_search_dialog1)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        val close = dialog.findViewById<Button>(R.id.closeButton) //입력버튼
        //입력버튼시 리스너
        close.setOnClickListener {
//            onClickedListener.onClicked(min_edit.text.toString().toInt(), max_edit.text.toString().toInt())
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{ //3
        fun onClicked(min_money: Int, max_money:Int) //인터페이스를 통해 프래그먼트에서 값을 받을수있음.
    }
    private lateinit var onClickedListener: ButtonClickListener //1
    fun setOnClickedListener(listener: ButtonClickListener) { //2
        onClickedListener = listener
    }
    //흐름 : 다이얼로그에서 입력 버튼클릭시 -> onClickedListener(1) 변수가 setOnClickedListener함수(2)를 통해 ButtonClickListener(3)로 초기화됨.
    //이 ButtonClickListener(3) 은 인터페이스 이므로 다시 기존의 프래그먼트(상세검색_1)에서 구현함으로써 프래그먼트에서도 값을 전달받게됨. -> 뷰모델저장
}


class AlcoholDialog(context: Context){
    private val dialog = Dialog(context)
    //레이아웃을 보여줌.
    fun ShowAlcohol(){
        dialog.setContentView(R.layout.detail_search_dialog2)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        val close = dialog.findViewById<Button>(R.id.closeButton) //입력버튼
        //입력버튼시 리스너
        close.setOnClickListener {
//            onClickedListener.onClicked(min_edit.text.toString().toInt(), max_edit.text.toString().toInt())
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{ //3
        fun onClicked(min_money: Int, max_money:Int) //인터페이스를 통해 프래그먼트에서 값을 받을수있음.
    }
    private lateinit var onClickedListener: ButtonClickListener //1
    fun setOnClickedListener(listener: ButtonClickListener) { //2
        onClickedListener = listener
    }
    //흐름 : 다이얼로그에서 입력 버튼클릭시 -> onClickedListener(1) 변수가 setOnClickedListener함수(2)를 통해 ButtonClickListener(3)로 초기화됨.
    //이 ButtonClickListener(3) 은 인터페이스 이므로 다시 기존의 프래그먼트(상세검색_1)에서 구현함으로써 프래그먼트에서도 값을 전달받게됨. -> 뷰모델저장
}

//가격대 다이얼로그를 위해 클래스를 만듬
class PriceDialog(context: Context){
    private val dialog = Dialog(context)
    //레이아웃을 보여줌.
    fun Show(){
        dialog.setContentView(R.layout.select_price_range)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        val min_edit = dialog.findViewById<EditText>(R.id.minP)//최소금액입력 edittext
        val max_edit = dialog.findViewById<EditText>(R.id.maxP) //최대금액입력 edittext
        val done = dialog.findViewById<Button>(R.id.enter) //입력버튼
        //입력버튼시 리스너
        done.setOnClickListener {
            onClickedListener.onClicked(min_edit.text.toString().toInt(), max_edit.text.toString().toInt())
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{ //3
        fun onClicked(min_money: Int, max_money:Int) //인터페이스를 통해 프래그먼트에서 값을 받을수있음.
    }
    private lateinit var onClickedListener: ButtonClickListener //1
    fun setOnClickedListener(listener: ButtonClickListener) { //2
        onClickedListener = listener
    }
    //흐름 : 다이얼로그에서 입력 버튼클릭시 -> onClickedListener(1) 변수가 setOnClickedListener함수(2)를 통해 ButtonClickListener(3)로 초기화됨.
    //이 ButtonClickListener(3) 은 인터페이스 이므로 다시 기존의 프래그먼트(상세검색_1)에서 구현함으로써 프래그먼트에서도 값을 전달받게됨. -> 뷰모델저장
}

//초창기 프래그먼트
class Detail_Search_Fragment_1 : Fragment() {

    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentDetailSearch1Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailSearch1Binding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        //뒤로가기
        val ParentFragment : Detail_Search_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Detail_Search_Fragment
        ParentFragment.invisible_back_btn(true)



        binding.priceRange.setOnClickListener{
            binding.priceRange.setBackgroundResource(R.drawable.button_round_white_red) //선택시 테두리 빨갛게.
            val dialog = PriceDialog(activity as MainActivity)
            dialog.Show()
            //뷰모델에 값을 전달하기위함.
            dialog.setOnClickedListener(object : PriceDialog.ButtonClickListener{
                override fun onClicked(min_money: Int, max_money: Int) {
                    vm.minPrice = min_money //최솟값
                    vm.maxPrice = max_money //최댓값
                    Log.d("가격대", "min: ${vm.minPrice} max: ${vm.maxPrice}")
                }
            })
        }

        binding.wineKind.setOnClickListener {
            binding.wineKind.setBackgroundResource(R.drawable.button_round_white_red)
            val dialog = KindDialog(activity as MainActivity)
            dialog.ShowKind()
        }

        binding.wineAlcohol.setOnClickListener {
            binding.wineAlcohol.setBackgroundResource(R.drawable.button_round_white_red)
            val dialog = AlcoholDialog(activity as MainActivity)
            dialog.ShowAlcohol()
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

