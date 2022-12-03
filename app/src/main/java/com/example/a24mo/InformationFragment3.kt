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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.databinding.FragmentInformation2Binding
import java.text.DecimalFormat


class InformationFragment3 : Fragment() {
    private  lateinit var vm : MainViewModel
    //view 바인딩을 위한 변수들
    private  var _binding : FragmentInformation2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("InformationFragment", "프래그먼트 전환 완료")
//        // 만약 값이 자동으로 바뀌게 하고 싶으면 옵저버 생성해줘야함 -> 이때 mutableLivaData가 바인딩되어있어야할듯
//        val nameObserver = Observer<String> { new_W_name ->
//            binding.informationName = new_W_name
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding = FragmentInformation2Binding.inflate(inflater,container,false)
        val view = binding.root
        val defaultImage = androidx.loader.R.drawable.notification_action_background
        var wid = 0

        //데이터 수정
        val formatter  = DecimalFormat("###,###")
        var count = 0
        val wnameObserver = Observer<WineDTO> { new_WineDetail ->
            // Update the UI, TextView인 경우
            wid =  new_WineDetail.Wid.toInt()
            binding.informationName.text = new_WineDetail.W_name
            binding.informationType.text = "#"+new_WineDetail.W_type
            binding.informationRegion.text = "#"+new_WineDetail.W_region
            binding.informationRegion2.text = "#"+new_WineDetail.W_region2
            binding.informationPrice.text = formatter.format(new_WineDetail.W_price.toInt()) + "원"
            binding.informationCapacity.text = new_WineDetail.W_capacity + "ml"
            binding.informationVariety.text = new_WineDetail.W_variety
            binding.informationTemperature.text = new_WineDetail.W_temperature
            if (new_WineDetail.W_alcohol == "0"){
                binding.informationAlcohol.text = "정보없음"
            }else
                binding.informationAlcohol.text = new_WineDetail.W_alcohol

            // ImageView인 경우
            addrating(binding.informationSweetness,new_WineDetail.W_sweetness.toInt())
            addrating(binding.informationAcidity, new_WineDetail.W_acidity.toInt())
            addrating(binding.informationBoddy, new_WineDetail.W_body.toInt())
            addrating(binding.informationTannin, new_WineDetail.W_tannin.toInt())

            // 동적 ImageView 추가
            for (image in new_WineDetail.W_aroma_arr){
                if(count >=3) break
                addimg(binding.informationAroma,image)
                count++
            }
            count =0
            for (image in new_WineDetail.W_food_arr){
                if(count >=3) break
                addimg(binding.informationFood,image)
                count++
            }
        }
        vm.wineDetail.observe(viewLifecycleOwner,wnameObserver)


        // 와인 이미지 띄우기
        val wimg : ImageView = view.findViewById(R.id.information_img)
        var url = "https://wine21.speedgabia.com/WINE_MST/TITLE/0%d000/W0%d.jpg".format(wid/1000, wid)

        GlideApp.with(this)
            .load(url)                                  // 불러올 이미지 url
            .placeholder(defaultImage)                  // 이미지 로딩 시작하기 전 표시할 이미지
            .error(defaultImage)                        // 로딩 에러 발생 시 표시할 이미지
            .fallback(defaultImage)                     // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
//            .circleCrop()                             // 동그랗게 자르기
            .into(wimg)                                 // 이미지를 넣을 뷰

//        //동적으로 ImageView 생성후 추가
//
//        addimg(waroma, "https://www.wine21.com/02_images/icon/ico-berry.png")
//        addimg(waroma, "https://www.wine21.com/02_images/icon/ico-berry.png")
//        addimg(waroma, "https://www.wine21.com/02_images/icon/ico-berry.png")
//        addimg(wfood, "https://www.wine21.com/02_images/icon/ico-berry.png")
//        addimg(wfood, "https://www.wine21.com/02_images/icon/ico-berry.png")
//        addimg(wfood, "https://www.wine21.com/02_images/icon/ico-berry.png")

        return view
    }

    //parent = waroma, wfood
    //imgurl = "https://www.wine21.com/02_images/icon/ico-berry.png"
    fun addimg(parent:LinearLayout, imageDto: imageDTO) {
        val name = imageDto.name
        val imgurl = "https://www."+imageDto.url
        val ll = LinearLayout(context)
        val imgv = ImageView(context)
        val tv = TextView(context)

        val llLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        ll.layoutParams = llLayoutParams
        ll.orientation = LinearLayout.VERTICAL

        val imageLayoutParams = LinearLayout.LayoutParams(105,105) //ImageView LayoutSize
        imgv.layoutParams = imageLayoutParams

        val tvLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        tv.layoutParams = tvLayoutParams
        tv.setText(name)

        GlideApp.with(this)
            .load(imgurl)
            .into(imgv)

        ll.addView(imgv)
        ll.addView(tv)
//
        parent.addView(ll)
//        parent.addView(imgv)
    }

    //parent = wsweetness, wacidty ...
    //value = 0~5
    fun addrating(parent:LinearLayout, value:Int) {
        for (i: Int in 1..5) {
            val imgv = ImageView(context).apply {
                setImageResource(R.drawable.grade)
                layoutParams = ViewGroup.LayoutParams(
                    30, 30
                )
                alpha = 0.03f
                if (i<=value)
                    alpha = 0.2f * i
            }
            parent.addView(imgv)
        }
    }
}