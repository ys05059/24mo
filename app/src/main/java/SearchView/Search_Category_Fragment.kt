package SearchView

import Fragment.*
import Main.MainActivity
import Main.MainViewModel
import PaymentView.ShoppingCartDialogFragment
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailSearch1Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//초창기 프래그먼트
class Search_Category_Fragment : Fragment() {

    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentDetailSearch1Binding? = null
    private val binding get() = _binding!!
    private val TAG = "Detail_Search_Frag_1 : "


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailSearch1Binding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        //뒤로가기
        val ParentFragment : Search_Main_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Search_Main_Fragment
        ParentFragment.invisible_back_btn(true)
        //가격대

        //이미 설정
        if(!(vm.Detail_Parameter.min_price==0 && vm.Detail_Parameter.max_price ==0)){
            binding.priceRange.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.priceRange,"price")}
        //설정안됨
        else{
            binding.priceRange.setBackgroundResource(R.drawable.button_less_round_white)
        }
        binding.priceRange.setOnClickListener{
            // 이미 가격대를 설정했을 경우
            if(!(vm.Detail_Parameter.min_price==0 && vm.Detail_Parameter.max_price ==0)){
                binding.priceRange.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.min_price =0
                vm.Detail_Parameter.max_price =0
                vm.Change_font_size(binding.priceRange,"price_reset")
            }else{
                binding.priceRange.setBackgroundResource(R.drawable.button_round_white_red)
                val dialog = PriceDialog(activity as MainActivity)
                dialog.Show()
                //뷰모델에 값을 전달하기위함.
                dialog.setOnClickedListener(object : PriceDialog.ButtonClickListener {
                    override fun onClicked(min_money: Int, max_money: Int) {
                        vm.Detail_Parameter.min_price = min_money   // 최소 가격
                        vm.Detail_Parameter.max_price = max_money   // 최대 가격

                        Log.d("가격대", "min: ${vm.Detail_Parameter.min_price} max: ${vm.Detail_Parameter.max_price}")
                        vm.Change_font_size(binding.priceRange,"price")
                    }
                })
            }
        }

        //음식

        if(!vm.Detail_Parameter.food.equals("")){
            binding.wineFood.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.wineFood,"food")}
        //설정안됨
        else{
            binding.wineFood.setBackgroundResource(R.drawable.button_less_round_white)
        }
        binding.wineFood.setOnClickListener {
            // 이미 설정된 음식이 있을 경우
            if(!vm.Detail_Parameter.food.equals("")){
                // 테두리 빼기
                binding.wineFood.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.food = ""
                vm.Change_font_size(binding.wineFood,"food_reset")
            }else {
                val dialog = FoodDialog(activity as MainActivity)
                dialog.Show()
                dialog.setOnClickedListener(object : FoodDialog.ButtonClickListener {
                    override fun onClicked(select_index: Int) {
                        when (select_index) {
                            //뷰모델에 값는것인데 일단 선택된음식 String으로 넘기겠습니다.
                            0 -> vm.Detail_Parameter.food = "고기"
                            1 -> vm.Detail_Parameter.food = "해산물"
                            2 -> vm.Detail_Parameter.food = "치즈"
                            3 -> vm.Detail_Parameter.food = "과일"
                            4 -> vm.Detail_Parameter.food = "디저트"
                            5 -> vm.Detail_Parameter.food = "기타"
                        }
                        Log.d("음식선택", "${vm.Detail_Parameter.food}")
                        vm.Change_font_size(binding.wineFood, "food")
                        binding.wineFood.setBackgroundResource(R.drawable.button_round_white_red)
                    }
                })
            }
        }

        //종류
        if(!vm.Detail_Parameter.type.equals("")){
            binding.wineKind.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.wineKind,"kind")}
        //설정안됨
        else{
            binding.wineFood.setBackgroundResource(R.drawable.button_less_round_white)
        }

        binding.wineKind.setOnClickListener {
            // 이미 설정된 음식이 있을 경우
            if(!vm.Detail_Parameter.type.equals("")){
                // 테두리 빼기
                binding.wineKind.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.type = ""
                vm.Change_font_size(binding.wineKind,"kind_reset")
            }else {
                val dialog = kindDialog(activity as MainActivity)
                dialog.Show()
                dialog.setOnClickedListener(object : kindDialog.ButtonClickListener {
                    override fun onClicked(select_index: Int) {
                        when (select_index) {
                            //뷰모델에 값는것인데 일단 선택된음식 String으로 넘기겠습니다.
                            0 -> vm.Detail_Parameter.type = "레드"
                            1 -> vm.Detail_Parameter.type = "화이트"
                            2 -> vm.Detail_Parameter.type = "로제"
                            3 -> vm.Detail_Parameter.type = "스파클링"
                            4 -> vm.Detail_Parameter.type = "주정강화"
                        }
                        //Log.d("음식선택", "${vm.Detail_Parameter.food}")
                        vm.Change_font_size(binding.wineKind, "kind")
                        binding.wineKind.setBackgroundResource(R.drawable.button_round_white_red)
                    }
                })
            }
        }


        //생산지
        if(!vm.Detail_Parameter.region.equals("")){
            binding.wineCountry.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.wineCountry,"country")}
        //설정안됨
        else{
            binding.wineCountry.setBackgroundResource(R.drawable.button_less_round_white)
        }

        binding.wineCountry.setOnClickListener {
            // 이미 설정된 음식이 있을 경우
            if(!vm.Detail_Parameter.region.equals("")){
                // 테두리 빼기
                binding.wineCountry.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.region = ""
                vm.Change_font_size(binding.wineCountry,"country_reset")
            }else {
                val dialog = CountryDialog(activity as MainActivity)
                dialog.Show()
                dialog.setOnClickedListener(object : CountryDialog.ButtonClickListener {
                    override fun onClicked(select_index: Int) {
                        when (select_index) {
                            //뷰모델에 값는것인데 일단 선택된음식 String으로 넘기겠습니다.
                            0 -> vm.Detail_Parameter.region = "프랑스"
                            1 -> vm.Detail_Parameter.region = "이탈리아"
                            2 -> vm.Detail_Parameter.region = "스페인"
                            3 -> vm.Detail_Parameter.region = "미국"
                            4 -> vm.Detail_Parameter.region = "칠레"
                            5 -> vm.Detail_Parameter.region = "기타"
                        }
                        //Log.d("음식선택", "${vm.Detail_Parameter.food}")
                        vm.Change_font_size(binding.wineCountry, "country")
                        binding.wineCountry.setBackgroundResource(R.drawable.button_round_white_red)
                    }
                })
            }
        }

        //도수
        if(vm.Detail_Parameter.alcohol != 0){
            binding.wineAlcohol.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.wineAlcohol,"country")}
        //설정안됨
        else{
            binding.wineAlcohol.setBackgroundResource(R.drawable.button_less_round_white)
        }

        binding.wineAlcohol.setOnClickListener {
            // 이미 설정된 음식이 있을 경우
            if(vm.Detail_Parameter.alcohol != 0){
                // 테두리 빼기
                binding.wineAlcohol.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.alcohol = 0
                vm.Change_font_size(binding.wineAlcohol,"alcohol_reset")
            }else {
                val dialog = AlcoholDialog(activity as MainActivity)
                dialog.Show()
                dialog.setOnClickedListener(object : AlcoholDialog.ButtonClickListener {
                    override fun onClicked(select_index: Int) {
                        vm.Detail_Parameter.alcohol =0
                        when (select_index) {
                            //뷰모델에 값는것인데 일단 선택된음식 String으로 넘기겠습니다.
                            0 -> vm.Detail_Parameter.alcohol = 1
                            1 -> vm.Detail_Parameter.alcohol = 2
                            2 -> vm.Detail_Parameter.alcohol = 3
                        }
                        //Log.d("음식선택", "${vm.Detail_Parameter.food}")
                        vm.Change_font_size(binding.wineAlcohol, "alcohol")
                        binding.wineAlcohol.setBackgroundResource(R.drawable.button_round_white_red)
                    }
                })
            }
        }

        //맛 설정
        if(!(vm.Detail_Parameter.sweet == -1 && vm.Detail_Parameter.acidity == -1 && vm.Detail_Parameter.body == -1 && vm.Detail_Parameter.tannin == -1) ){
            binding.wineOthers.setBackgroundResource(R.drawable.button_round_white_red)
            vm.Change_font_size(binding.wineOthers,"others")}
        //설정안됨
        else{
            binding.wineOthers.setBackgroundResource(R.drawable.button_less_round_white)
        }
        binding.wineOthers.setOnClickListener {
            val dp = vm.Detail_Parameter
            // 맛을 설정한 경우
            if(!(dp.sweet==-1 && dp.acidity == -1&&dp.body == -1 && dp.tannin ==-1)){
                binding.wineOthers.setBackgroundResource(R.drawable.button_less_round_white)
                vm.Detail_Parameter.sweet = -1
                vm.Detail_Parameter.acidity = -1
                vm.Detail_Parameter.body = -1
                vm.Detail_Parameter.tannin = -1
                vm.Change_font_size(binding.wineOthers,"others_reset")
            }else{
                val dialog = TasteDialog(activity as MainActivity)
                dialog.Show()
                dialog.setOnClickedListener(object : TasteDialog.ButtonClickListener {
                    override fun onClicked(sweet:Int, acid:Int, body:Int, tanin:Int) {
                        //1 : 낮음 / 2: 보통 / 3: 높음 / 0 :상관없음(default)
                        vm.Detail_Parameter.sweet = sweet
                        vm.Detail_Parameter.acidity = acid
                        vm.Detail_Parameter.body= body
                        vm.Detail_Parameter.tannin = tanin

                        Log.d("Taste", "${vm.Detail_Parameter.sweet}${vm.Detail_Parameter.acidity}${vm.Detail_Parameter.body}${vm.Detail_Parameter.tannin}")
                        vm.Change_font_size(binding.wineOthers, "others")
                        binding.wineOthers.setBackgroundResource(R.drawable.button_round_white_red)
                    }
                })
            }

        }

        //장바구니
        binding.basket.setOnClickListener{
            ShoppingCartDialogFragment().show((activity as MainActivity).fragmentManager,"shoppingCart")
        }

        vm.shoppingCartList.observe(viewLifecycleOwner, Observer{
            if(vm.shoppingCartList.value == null) //장바구니가 비어있으면 0 (안할시 null인 n으로 표시됨)
            {
                binding.basket.setText("0")
            }
            else{
                binding.basket.setText(vm.get_cartItem_count().toString())
            }
        })
        //검색버튼
        binding.searchBtn.setOnClickListener {
            Log.d(TAG,vm.Detail_Parameter.toString())
            vm.Detail_Parameter.name = binding.wineNameSearch.text.toString()
            runBlocking {
                launch {
                    launch {
                        Log.d(TAG, "viewModel로 SearchList 받아오기")
                        vm.getSearchList(vm.Detail_Parameter)
                        delay(1000)
                    }.join()
                    (activity as MainActivity).changeDetailSearchFragment(2, 0)
                    vm.Search_Is_Back++
                }
//                    if (vm.wineList.value.isNullOrEmpty()){
//                        (activity as MainActivity).changeDetailSearchFragment(3, 0)
//                    }else{
//                    }
            }
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

