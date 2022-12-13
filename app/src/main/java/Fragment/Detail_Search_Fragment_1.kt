package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.util.Log
import android.os.Bundle
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
    private val TAG = "Detail_Search_Frag_1 : "


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
                    vm.Detail_Parameter.min_price = min_money   // 최소 가격
                    vm.Detail_Parameter.max_price = max_money   // 최대 가격
//                    vm.minPrice = min_money //최솟값
//                    vm.maxPrice = max_money //최댓값
                    Log.d("가격대", "min: ${vm.Detail_Parameter.min_price} max: ${vm.Detail_Parameter.max_price}")
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
                        0 -> vm.Detail_Parameter.food = "고기"
                        1 ->vm.Detail_Parameter.food  = "해산물"
                        2 ->vm.Detail_Parameter.food  = "치즈"
                        3 ->vm.Detail_Parameter.food  = "과일"
                        4 ->vm.Detail_Parameter.food  = "디저트"
                        5 ->vm.Detail_Parameter.food  = "기타"
                    }
                    Log.d("음식선택", "${vm.Detail_Parameter.food}")
                    vm.Change_font_size(binding.wineFood, "food")
                }
            })
        }

        //맛 설정
        binding.wineOthers.setOnClickListener {
            binding.wineOthers.setBackgroundResource(R.drawable.button_round_white_red)
            val dialog = TasteDialog(activity as MainActivity)
            dialog.Show()
            dialog.setOnClickedListener(object : TasteDialog.ButtonClickListener{
                override fun onClicked(sweet:Int, acid:Int, body:Int, tanin:Int) {
                    //1 : 낮음 / 2: 보통 / 3: 높음 / 0 :상관없음(default)
                    vm.Detail_Parameter.sweet = sweet
                    vm.Detail_Parameter.acidity = acid
                    vm.Detail_Parameter.body= body
                    vm.Detail_Parameter.tannin = tanin

                    Log.d("Taste", "${vm.Detail_Parameter.sweet}${vm.Detail_Parameter.acidity}${vm.Detail_Parameter.body}${vm.Detail_Parameter.tannin}")
                    vm.Change_font_size(binding.wineOthers, "others")
                }
            })
        }

        //검색 버튼
        binding.searchBtn.setOnClickListener {
            Log.d(TAG,vm.Detail_Parameter.toString())
            vm.Detail_Parameter.name = binding.wineNameSearch.text.toString()
            (activity as MainActivity).changeDetailSearchFragment(2, 0)
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

