package Main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import Fragment.InformationFragment
import Fragment.Recommend_First_Fragment
import com.example.a24mo.R
import com.example.a24mo.databinding.HomeLayoutBinding

class MainActivity : AppCompatActivity(){
    //화면전환 인텐트 관련 코드
    lateinit var requestLaunch : ActivityResultLauncher<Intent>

    lateinit var transaction : FragmentTransaction
    var presentFragment :Fragment? = null
    lateinit var fragmentManager: FragmentManager
    lateinit var homeLayoutBinding: HomeLayoutBinding

    lateinit var viewModel  : MainViewModel

    //슬라이드할 이미지 리스트
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeLayoutBinding = HomeLayoutBinding.inflate(layoutInflater)
        setContentView(homeLayoutBinding.root) //홈화면 레이아웃 xml

        fragmentManager = supportFragmentManager
        var informationFragment = InformationFragment()
        val recommend_First_Fragment =Recommend_First_Fragment()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    //홈화면 관련 버튼
        //이미지 슬라이드
        val Image_Slider = homeLayoutBinding.imageSlider //레이아웃의 이미지슬라이드 뷰 ID
        var Event_Wite_list = Add_Image_list() //이미지슬라이드에 슬라이드할 이미지 추가함수.
        Image_Slider.setImageList(Event_Wite_list)
        Image_Slider.startSliding(3000) //이미지 넘기는 시간 조절.

        Image_Slider.setItemClickListener(object : ItemClickListener{ //이미지 클릭시 이벤트처리. 상세정보 띄워야함.
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
                replaceTransaction(informationFragment)
            }
        })
        //액티비티 화면전환 관련
        requestLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode == RESULT_OK)
            {

            }
        }

        //와인검색,추천 버튼
        homeLayoutBinding.RecommendWineBtn.setOnClickListener {
            replaceTransaction(recommend_First_Fragment)
        }  // 와인추천버튼

        var Search_Wine = homeLayoutBinding.DetailSearchBtn // 와인상세검색 버튼
        Search_Wine.setOnClickListener {

        }

        //장바구니, 바코드 버튼
        var Shopping_Basket : Button = homeLayoutBinding.ShoppingBasket // 장바구니버튼
        Shopping_Basket.setOnClickListener {

        }

        var Bar_Cord : Button = homeLayoutBinding.BarCord //바코드 버튼
        Bar_Cord.setOnClickListener{show_Scanner()}

        //관리자메뉴
        var manager_menu : ImageButton = homeLayoutBinding.ManagerBtn
        manager_menu.setOnClickListener {

        }
    }

    //이미지 슬라이드 이미지 추가함수.
    fun Add_Image_list (): ArrayList<SlideModel>{
        //SlideModel("url", "제목")
        var image_list = ArrayList<SlideModel>()
        //scaleType = FIT, CENTER_CROP ,CENTER_INSIDE
        //행사 와인 이미지 넣기
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0156000/W0156713.jpg",ScaleTypes.FIT))          // 케이머스
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0168000/W0168882.png",ScaleTypes.FIT))          // 디코이
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0161000/W0161502.jpg",ScaleTypes.FIT))          // 몬테스 알파
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0163000/W0163213.jpg", ScaleTypes.FIT))         // 돔페리뇽
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0167000/W0167176.png", ScaleTypes.FIT))         // 벨 아사이
        return image_list
    }

    //바코드 다이얼로그 출력
    fun show_Scanner(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater : LayoutInflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.barcode_dialog, null))
//        builder.setNegativeButton("돌아가기"){dialog,p1 -> dialog.cancel()}
        val alertDialog : AlertDialog = builder.create()
        alertDialog?.setCanceledOnTouchOutside(true)  //바깥 터치시 다이얼로그 사라짐
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()
    }


    fun replaceTransaction(fragment: Fragment) {
        if(presentFragment == fragment) {
            Toast.makeText(this, "이미 해당 Fragment를 보여주고 있습니다.",
                Toast.LENGTH_SHORT).show()
            return
        }
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
        presentFragment = fragment
        transaction.addToBackStack(null)
    }
}
