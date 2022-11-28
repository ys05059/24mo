package com.example.a24mo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes

class MainActivity : AppCompatActivity(){
    //화면전환 인텐트 관련 코드
    lateinit var requestLaunch : ActivityResultLauncher<Intent>
    val Recommend_wine_code = 100 //액티비티를 구분할 코드들
    val DetailSearch_wine_code = 200
    val manager_menu_code = 200

    //슬라이드할 이미지 리스트
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout) //홈화면 레이아웃 xml

    //홈화면 관련 버튼
        //이미지 슬라이드
        val Image_Slider = findViewById<ImageSlider>(R.id.image_slider) //레이아웃의 이미지슬라이드 뷰 ID
        var Event_Wite_list = Add_Image_list() //이미지슬라이드에 슬라이드할 이미지 추가함수.
        Image_Slider.setImageList(Event_Wite_list)
        Image_Slider.startSliding(3000) //이미지 넘기는 시간 조절.

        Image_Slider.setItemClickListener(object : ItemClickListener{ //이미지 클릭시 이벤트처리. 상세정보 띄워야함.
            override fun onItemSelected(position: Int) {
                //와인 상세정보 출력창과 연결필요
                //선택한 이미지 = position(ex. 첫번재사진  -> 0 / 두번째사진 -> 1 ...)
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
        var Recommend_Wine : Button = findViewById(R.id.Recommend_Wine_btn) // 와인추천버튼
        Recommend_Wine.setOnClickListener {
            val intent = Intent(this, Recommend_Wind::class.java )
            requestLaunch.launch(intent)
        }
        var Search_Wine : Button = findViewById(R.id.Detail_Search_btn) // 와인상세검색 버튼
        Search_Wine.setOnClickListener {

        }

        //장바구니, 바코드 버튼
        var Shopping_Basket : Button = findViewById(R.id.Shopping_Basket) // 장바구니버튼
        Shopping_Basket.setOnClickListener {

        }

        var Bar_Cord : Button = findViewById(R.id.BarCord) //바코드 버튼
        Bar_Cord.setOnClickListener{show_Scanner()}

        //관리자메뉴
        var manager_menu : ImageButton = findViewById(R.id.Manager_Btn)
        manager_menu.setOnClickListener {

        }
    }

    //이미지 슬라이드 이미지 추가함수.
    fun Add_Image_list (): ArrayList<SlideModel>{
        //SlideModel("url", "제목")
        var image_list = ArrayList<SlideModel>()
        //scaleType = FIT, CENTER_CROP ,CENTER_INSIDE
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0172000/W0172249.png", ScaleTypes.FIT))
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/IMAGE/0166000/T0166442_003.png",ScaleTypes.FIT))
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/TITLE/0172000/W0172322.png", ScaleTypes.FIT))
        image_list.add(SlideModel("https://wine21.speedgabia.com/WINE_MST/IMAGE/0170000/T0170722_001.png", ScaleTypes.FIT))
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

}
