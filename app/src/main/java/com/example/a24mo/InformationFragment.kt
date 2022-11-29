package com.example.a24mo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        GlideApp.with(this)
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

        GlideApp.with(this)
            .load(aimgurl)
            .into(aimg2)

        waroma.addView(aimg2)

        wname.setText("비에티, 로에로 아네이스")
        wtype.setText("#레드와인")
        wregion.setText("#이탈리아")
        wregion2.setText("#피에몬테")
        wprice.setText("63,000")
        wcapacity.setText("750ml")
        wvariety.setText("아네이스 100% 벨 꼴레 Bel Colle")
        walcohol.setText("14%")
        wtemp.setText("8~10C")

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}