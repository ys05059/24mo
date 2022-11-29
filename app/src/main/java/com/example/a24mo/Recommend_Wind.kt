package com.example.a24mo

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.a24mo.databinding.RecommendWineFragment1Binding

class First_Fragment : Fragment() //step1.태그를 설정해주세요
{
    lateinit var activity : Recommend_Wind //액티비티의 변수와 함수를 쓸수있게 하기위함
    var select_count = 0 //몇개 선택되었는가?(버튼을 한개만 선택하도록 통제하기위함)
    var what_select = 0 //무엇이 선택되었는가. (기존 선택한 버튼을 취소하고 새로 버튼을 선택하기위함)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.recommend_wine_fragment1, container, false)
        val slide_delay : Long = 500 //다음 step으로 넘어갈때 딜레이시간
        activity = context as Recommend_Wind
        var tag_list = ArrayList<Button>()
        tag_list.add(view.findViewById<Button>(R.id.tag1))//달달한
        tag_list.add(view.findViewById<Button>(R.id.tag2))//선물하기좋은
        tag_list.add(view.findViewById<Button>(R.id.tag3))//가장많이팔린
        tag_list.add(view.findViewById<Button>(R.id.tag4))//드라이한

        if(activity.is_Back == 1 && activity.what_this_step == 1) //뒤로가기를 클릭했고, 현재 페이지가 step1이면 , 이미 선택한값은 빨간색 테두리 칠하기
        {
            if(activity.what_firstStep_select != -1)
            {
                tag_list[activity.what_firstStep_select].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = activity.what_firstStep_select
                select_count = 1
            }

        }

        tag_list[0].setOnClickListener {//달달한
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[0].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 0
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[0].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 0
            }

            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 0
                activity.changeFragment(2)
            }, slide_delay)

        }

        tag_list[1].setOnClickListener {//선물하기좋은
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[1].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 1
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[1].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 1
            }
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 1
                activity.changeFragment(2)
            }, slide_delay)
        }

        tag_list[2].setOnClickListener {//가장많이팔린
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[2].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 2
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[2].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 2
            }
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 2
                activity.changeFragment(2)
            }, slide_delay)
        }

        tag_list[3].setOnClickListener {//드라이한
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[3].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 3
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[3].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 3
            }
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 3
                activity.changeFragment(2)
            }, slide_delay)
        }
        return view
    }
}

class Second_Fragment: Fragment(){    //step2.태그를 설정해주세요
    lateinit var activity : Recommend_Wind //액티비티의 변수와 함수를 쓸수있게 하기위함
    var select_count = 0 //몇개 선택되었는가?(버튼을 한개만 선택하도록 통제하기위함)
    var what_select = 0 //무엇이 선택되었는가. (기존 선택한 버튼을 취소하고 새로 버튼을 선택하기위함)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.recommend_wine_fragment2, container, false)
        val slide_delay : Long = 500 //다음 step으로 넘어갈때 딜레이시간
        activity = context as Recommend_Wind
        var tag_list  = ArrayList<Button>()
        tag_list.add(view.findViewById<Button>(R.id.tag1)) //고기와 어울리는
        tag_list.add(view.findViewById<Button>(R.id.tag2)) //해산물과 어울리는
        tag_list.add(view.findViewById<Button>(R.id.tag3)) //과일과 어울리는
        tag_list.add(view.findViewById<Button>(R.id.tag4)) //치즈어울리는

        if(activity.is_Back == 1 && activity.what_this_step == 2) //뒤로가기를 클릭했고, 현재 페이지가 step2이면 , 이미 선택한값은 빨간색 테두리 칠하기
        {
            if(activity.what_secondStep_select != -1)
            {
                tag_list[activity.what_secondStep_select].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = activity.what_secondStep_select
                select_count = 1
            }
        }

        tag_list[0].setOnClickListener {//고기와 어울리는
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[0].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 0
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[0].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 0
            }

            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_secondStep_select = 0
                activity.changeFragment(0)
            }, slide_delay)

        }

        tag_list[1].setOnClickListener {//해산물과 어울리는
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[1].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 1
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[1].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 1
            }

            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_secondStep_select = 1
                activity.changeFragment(0)
            }, slide_delay)
        }

        tag_list[2].setOnClickListener {//과일과 어울리는
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[2].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 2
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[2].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 2
            }

            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_secondStep_select = 2
                activity.changeFragment(0)
            }, slide_delay)
        }

        tag_list[3].setOnClickListener {//치즈어울리는
            if(select_count == 0) //선택된것이 없음. =>선택시 바로 테두리 빨갛게
            {
                tag_list[3].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 3
                select_count += 1
            }
            else //한개가 선택됨. -> 기존에 선택된것이 없어지고, 새로 선택된것이 빨갛게
            {
                tag_list[what_select].setBackgroundResource(R.drawable.button_less_round_white) //기존에 것 없앰
                tag_list[3].setBackgroundResource(R.drawable.button_round_white_red)
                what_select = 3
            }

            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_secondStep_select = 3
                activity.changeFragment(0)
            }, slide_delay)
        }
        return view
    }

}


class Result_Fragment : Fragment(){
    lateinit var activity : Recommend_Wind
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.recommend_wine_fragment_result, container, false)
        activity = context as Recommend_Wind
        //태그설정
        var first_tag = view.findViewById<TextView>(R.id.first_tag)
        var second_tag = view.findViewById<TextView>(R.id.second_tag)
        when(activity.what_firstStep_select){
            0->{//달달한
                first_tag.setText("#달달한")
            }
            1 ->{//선물하기좋은
                first_tag.setText("#선물하기 좋은")
            }
            2->{//가장많이팔린
                first_tag.setText("#가장많이 팔린")
            }
            3->{//드라이한
                first_tag.setText("#드라이한")
            }

        }
        when(activity.what_secondStep_select){
            0->{//달달한
                second_tag.setText("#고기와 잘 어울리는")
            }
            1 ->{//선물하기좋은
                second_tag.setText("#해산물과 잘 어울리는")
            }
            2->{//가장많이팔린
                second_tag.setText("#과일과 잘 어울리는")
            }
            3->{//드라이한
                second_tag.setText("#치즈와 잘 어울리는")
            }
        }
        return view
    }
}

//추천액티비티
class Recommend_Wind : AppCompatActivity() {
    lateinit var go_back_btn: ImageButton
    var is_Back : Int = 0 //뒤로 선택하기를 눌렀는가? (0: 안누름 / 1 : 눌렀음)
    var what_this_step = 0 //현재 페이지가 step1 : 1/ step2: 2 / 결과 : 3
    var what_firstStep_select = -1 //첫번째 선택 (0:달달한 / 1: 선물하기좋은 /2: 가장많이 팔린 / 3: 드라이한)
    var what_secondStep_select = -1 //두번째 선택 (0:고기와 잘어울리는 / 1: 해산물과 잘어울리는 / 2: 과일과 잘어울리는 / 3: 치즈와 잘 어울리는)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend_wine_layout)

        //홈으로 가기 버튼
        val go_home_btn = findViewById<Button>(R.id.go_to_home_btn)
        go_home_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //뒤로가기버튼
        go_back_btn = findViewById<ImageButton>(R.id.go_back_btn)
        go_back_btn.setOnClickListener {
            is_Back = 1 //뒤로가기 True
            when(what_this_step)
            {
                2->{changeFragment(3)}//step2에서 뒤로가기시
                3->{changeFragment(4)}//추천결과 페이지에서 뒤로가기시
            }
        }
        //초기 프래그먼트 실행
        changeFragment(1)

    }

    //프래그먼트 전환함수
    fun changeFragment(step: Int) {
        when (step) {

            0 -> {//추천결과 리스트
                go_back_btn.visibility = View.VISIBLE
                what_this_step = 3
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.step_id, Result_Fragment())
                    .commit()
            }
            1 -> { //step1 (최초실행은 add)
                go_back_btn.visibility = View.INVISIBLE //뒤로가기버튼 비활성화
                what_this_step = 1
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .add(R.id.step_id, First_Fragment())
                    .commit()
            }
            2 -> { //step2
                go_back_btn.visibility = View.VISIBLE  //뒤로가기버튼 활성화
                what_this_step = 2
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.step_id, Second_Fragment())
                    .commit()
            }


            3 -> { //step2 에서 뒤로가기시 step1으로 돌아감. (애니메이션 효과를 바꾸기위해 추가)
                go_back_btn.visibility = View.INVISIBLE //뒤로가기버튼 비활성화
                what_this_step = 1
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_to_right,
                        R.anim.enter_from_right,
                        R.anim.exit_to_left
                    )
                    .replace(R.id.step_id, First_Fragment())
                    .commit()
            }

            4 -> { //결과 에서 뒤로가기시 step2으로 돌아감. (애니메이션 효과를 바꾸기위해 추가)
                go_back_btn.visibility = View.VISIBLE //뒤로가기버튼 비활성화
                what_this_step = 2
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_to_right,
                        R.anim.enter_from_right,
                        R.anim.exit_to_left
                    )
                    .replace(R.id.step_id, Second_Fragment())
                    .commit()
            }
        }
    }
}

//구현해야할것 .
//5. 목록창에서 장바구니를 담았을때 메인액티비티로 데이터 전송구현(아마 와인id만 주어지면 될것같음)
//6. 메인홈화면 장바구니 갯수 구현



