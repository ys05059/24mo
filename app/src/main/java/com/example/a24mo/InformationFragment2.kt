package com.example.a24mo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.a24mo.databinding.FragmentInformationBinding


class InformationFragment2 : Fragment() {
    private  lateinit var viewModel : MainViewModel
    private lateinit var binding: FragmentInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //옵저버 생성해줘야함
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_information,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        

//        viewModel.setWineDetail(152589)
//        Log.d("데이터 테스트",viewModel.getWineDetail().toString())

        val defaultImage = androidx.loader.R.drawable.notification_action_background

        //데이터 수정
        val view = inflater.inflate(R.layout.fragment_information, container, false)
        val wname :TextView = view.findViewById(R.id.information_name)
        val wtype :TextView = view.findViewById(R.id.information_type)
        val wregion :TextView = view.findViewById(R.id.information_region)
        val wregion2 :TextView = view.findViewById(R.id.information_region2)

        val wimg :ImageView = view.findViewById(R.id.information_img)

//        Glide.with(this)
//            .load(url) // 불러올 이미지 url
//            .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
//            .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
//            .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
//            .circleCrop() // 동그랗게 자르기
//            .into(imageView) // 이미지를 넣을 뷰
        val wid = 152589
        var url = "https://wine21.speedgabia.com/WINE_MST/TITLE/0%d000/W0%d.jpg".format(wid/1000, wid)

        // 기존 GlideApp이 안돼서 Glide로 수정 -재웅
        Glide.with(this)
            .load(url)
            .placeholder(defaultImage)
            .error(defaultImage)
            .fallback(defaultImage)
            .into(wimg)


        //wimg.setImageResource()

        val wprice :TextView = view.findViewById(R.id.information_price)
        val wcapacity :TextView = view.findViewById(R.id.information_capacity)
        val wvariety :TextView = view.findViewById(R.id.information_variety)

        //val wsweetness :TextView = view.findViewById(R.id.information_name)
        //val wacidity :TextView = view.findViewById(R.id.information_name)
        //val wbody :TextView = view.findViewById(R.id.information_name)
        //val wtannin :TextView = view.findViewById(R.id.information_name)

        val walcohol :TextView = view.findViewById(R.id.information_alcohol)
        val wtemp :TextView = view.findViewById(R.id.information_temperature)

        val waroma :LinearLayout = view.findViewById(R.id.information_aroma)
        val wfood :LinearLayout = view.findViewById(R.id.information_food)


        //동적으로 ImageView 생성후 추가
        val aimg2 = ImageView(context)
        val imageLayoutParams = LinearLayout.LayoutParams(105,105) //ImageView LayoutSize
        val aimgurl = "https://www.wine21.com/02_images/icon/ico-berry.png"
        aimg2.layoutParams = imageLayoutParams


        Glide.with(this)
            .load(aimgurl)
            .into(aimg2)

        waroma.addView(aimg2)

        // 데이터 가져와서 입력
        //테스트
//        wname.setText("비에티, 로에로 아네이스")
//        wtype.setText("#레드와인")
//        wregion.setText("#이탈리아")
//        wregion2.setText("#피에몬테")
//        wprice.setText("63,000")
//        wcapacity.setText("750ml")
//        wvariety.setText("아네이스 100% 벨 꼴레 Bel Colle")
//        walcohol.setText("14%")
//        wtemp.setText("8~10C")



        return view
    }
}