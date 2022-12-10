package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.a24mo.R
import com.example.a24mo.databinding.HomeLayoutBinding

class HomeFragment : Fragment(){

    private  lateinit var viewModel : MainViewModel
    private  var _binding : HomeLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        // 이미지 슬라이더
        val Image_Slider = binding.imageSlider //레이아웃의 이미지슬라이드 뷰 ID
        var Event_Wite_list = Add_Image_list() //이미지슬라이드에 슬라이드할 이미지 추가함수.
        Image_Slider.setImageList(Event_Wite_list)
        Image_Slider.startSliding(3000) //이미지 넘기는 시간 조절.

        Image_Slider.setItemClickListener(object : ItemClickListener { //이미지 클릭시 이벤트처리. 상세정보 띄워야함.
            override fun onItemSelected(position: Int) {
                //와인 상세정보 출력창과 연결필요
                //선택한 이미지 = position(ex. 첫번재사진  -> 0 / 두번째사진 -> 1 ...)
                // 행사 상품 id 저장
                val eventWineArray =  mutableListOf<Int>()
                eventWineArray.add(156713)                                                      // 케이머스
                eventWineArray.add(168882)                                                      // 디코이
                eventWineArray.add(161502)                                                      // 몬테스
                eventWineArray.add(163213)                                                      // 돔페리뇽
                eventWineArray.add(167176)                                                      // 벨아사이
                viewModel.getWineDetail(eventWineArray[position])                                // wid 넘기면서 viewmodel에 해당 내용 넣어두기
                (activity as MainActivity).replaceTransaction(InformationFragment())
            }
        })
        // 와인 추천 버튼
        binding.RecommendWineBtn.setOnClickListener {
            (activity as MainActivity).replaceTransaction(Recommend_Fragment())
        }
        // 와인상세검색 버튼
        binding.DetailSearchBtn.setOnClickListener {
            (activity as MainActivity).replaceTransaction(Detail_Search_Fragment())
        }
        // 장바구니 버튼
        if(viewModel.shoppingCartList.value == null)
        {
            binding.ShoppingBtn.setText("0")
        }
        else{
            binding.ShoppingBtn.setText(viewModel.shoppingCartList.value?.size.toString())
        }
        binding.ShoppingBtn.setOnClickListener {
            ShoppingCartDialogFragment().show((activity as MainActivity).fragmentManager,"shoppingCart")
        }
        // 바코드 버튼
        binding.BarCord.setOnClickListener{show_Scanner()}

        //관리자메뉴
        binding.ManagerBtn.setOnClickListener{

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //이미지 슬라이드 이미지 추가함수.
    fun Add_Image_list (): ArrayList<SlideModel>{
        //SlideModel("url", "제목")
        var image_list = ArrayList<SlideModel>()
        //scaleType = FIT, CENTER_CROP ,CENTER_INSIDE
        //행사 와인 이미지 넣기
        image_list.add(
            SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0156000/W0156713.jpg",
                ScaleTypes.CENTER_INSIDE)
        )          // 케이머스
        image_list.add(
            SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0168000/W0168882.png",
                ScaleTypes.CENTER_INSIDE)
        )          // 디코이
        image_list.add(
            SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0161000/W0161502.jpg",
                ScaleTypes.CENTER_INSIDE)
        )          // 몬테스 알파
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/IMAGE/0163000/T0163213_001.png", ScaleTypes.CENTER_INSIDE))         // 돔페리뇽
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0167000/W0167176.png", ScaleTypes.CENTER_INSIDE))         // 벨 아사이
        return image_list
    }

    //바코드 다이얼로그 출력
    fun show_Scanner(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity as MainActivity)
        val inflater : LayoutInflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.barcode_dialog, null))
//        builder.setNegativeButton("돌아가기"){dialog,p1 -> dialog.cancel()}
        val alertDialog : AlertDialog = builder.create()
        alertDialog?.setCanceledOnTouchOutside(true)  //바깥 터치시 다이얼로그 사라짐
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()
    }

}