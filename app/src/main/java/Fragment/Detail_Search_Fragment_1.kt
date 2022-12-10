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
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailSearch1Binding


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


        //가격대
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
                    vm.Change_font_size(binding.priceRange,"price")
                }
            })
        }


        //음식
        binding.wineFood.setOnClickListener {
            binding.wineFood.setBackgroundResource(R.drawable.button_round_white_red)
            val dialog = FoodDialog(activity as MainActivity)

            dialog.Show()
            dialog.setOnClickedListener(object : FoodDialog.ButtonClickListener{
                override fun onClicked(select_index: Int) {
                    when(select_index){
                        //뷰모델에 값는것인데 일단 선택된음식 String으로 넘기겠습니다.
                        0 -> vm.Detail_food = "고기류"
                        1 ->vm.Detail_food = "해산물"
                        2 ->vm.Detail_food = "치즈류"
                        3 ->vm.Detail_food = "과일류"
                        4 ->vm.Detail_food = "디저트류"
                        5 ->vm.Detail_food = "기타"
                    }
                    Log.d("음식선택", "${vm.Detail_food}")
                    vm.Change_font_size(binding.wineFood, "food")
                }
            })
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

