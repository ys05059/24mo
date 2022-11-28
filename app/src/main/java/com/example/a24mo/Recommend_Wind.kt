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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.a24mo.databinding.RecommendWineFragment1Binding

class First_Fragment : Fragment() //step1.태그를 설정해주세요
{
    lateinit var activity : Recommend_Wind
    var select_count = 0 //몇개 선택되었는가?(버튼을 한개만 선택하도록 통제하기위함)
    var what_select = 0 //무엇이 선택되었는가. (버튼을 한개만 선택하도록 통제하기위함)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.recommend_wine_fragment1, container, false)
        activity = context as Recommend_Wind
        var tag1 = view.findViewById<Button>(R.id.tag1) //달달한
        tag1.setOnClickListener {
             tag1.setBackgroundResource(R.drawable.button_round_white_red)
            //조금 딜레이를 줌
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 0
                activity.changeFragment(2)
            }, 1000)

        }
        var tag2 = view.findViewById<Button>(R.id.tag2) //선물하기좋은
        tag2.setOnClickListener {
            tag2.setBackgroundResource(R.drawable.button_round_white_red)
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 1
                activity.changeFragment(2)
            }, 1000)
        }
        var tag3 = view.findViewById<Button>(R.id.tag3) //가장많이팔린
        tag3.setOnClickListener {
            tag3.setBackgroundResource(R.drawable.button_round_white_red)
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 2
                activity.changeFragment(2)
            }, 1000)
        }
        var tag4 = view.findViewById<Button>(R.id.tag4) //드라이한
        tag4.setOnClickListener {
            tag4.setBackgroundResource(R.drawable.button_round_white_red)
            Handler(Looper.getMainLooper()).postDelayed({
                activity.what_firstStep_select = 3
                activity.changeFragment(2)
            }, 1000)
        }
        return view
    }
}


class Second_Fragment: Fragment(){    //step2.태그를 설정해주세요
    lateinit var binding: RecommendWineFragment1Binding
    lateinit var activity : Recommend_Wind
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.recommend_wine_fragment2, container, false)
        activity = context as Recommend_Wind
        var tag1 = view.findViewById<Button>(R.id.tag1) //고기와 어울리는
        tag1.setOnClickListener {
            tag1.setBackgroundResource(R.drawable.button_round_white_red)
            activity.what_secondStep_select = 0
            //추천와인목록 출력필요
        }
        var tag2 = view.findViewById<Button>(R.id.tag2) //해산물과 어울리는
        tag2.setOnClickListener {
            tag2.setBackgroundResource(R.drawable.button_round_white_red)
            activity.what_secondStep_select = 1
            //추천와인목록 출력필요
        }
        var tag3 = view.findViewById<Button>(R.id.tag3) //과일과 어울리는
        tag3.setOnClickListener {
            tag3.setBackgroundResource(R.drawable.button_round_white_red)
            activity.what_secondStep_select = 2
            //추천와인목록 출력필요
        }
        var tag4 = view.findViewById<Button>(R.id.tag4) //치즈어울리는
        tag4.setOnClickListener {
            tag4.setBackgroundResource(R.drawable.button_round_white_red)
            activity.what_secondStep_select = 3
            //추천와인목록 출력필요
        }
        return view
    }

}

class Recommend_Wind : AppCompatActivity() {
    lateinit var go_back_btn : ImageButton
    var what_firstStep_select=0 //첫번째 선택 (0:달달한 / 1: 선물하기좋은 /2: 가장많이 팔린 / 3: 드라이한)
    var what_secondStep_select = 0 //두번째 선택 (0:고기와 잘어울리는 / 1: 해산물과 잘어울리는 / 2: 과일과 잘어울리는 / 3: 치즈와 잘 어울리는)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend_wine_layout)

        //홈으로 가기 버튼
        val go_home_btn = findViewById<Button>(R.id.go_to_home_btn)
        go_home_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        go_back_btn = findViewById<ImageButton>(R.id.go_back_btn)
        go_back_btn.setOnClickListener{
            changeFragment(1)
        }

        //초기 프래그먼트 실행
        changeFragment(1)
    }
    fun changeFragment(step:Int){//프래그먼트 전환함수
        when(step){
            1 -> { //step1
                go_back_btn.visibility = View.INVISIBLE //뒤로가기버튼 비활성화
                supportFragmentManager.beginTransaction()
                    .replace(R.id.step_id, First_Fragment())
                    .commit()
            }
            2 -> { //step2
                go_back_btn.visibility = View.VISIBLE  //뒤로가기버튼 활성화
                supportFragmentManager.beginTransaction()
                    .replace(R.id.step_id, Second_Fragment())
                    .commit()
            }
        }

    }


}


//구현해야할것 .
//1. step2에서 뒤로가기시 step1결과 유지.(프래그먼트 상태유지) (Fragment 네비게이션을 통한 show hide방식 사용해야할것같음.)
//2. step1에서 유지된 결과 수정시(다른버튼 클릭시) 체크해제되고 그버튼이 활성화되게끔 수정해야함.(list에 버튼들을 넣고 what_select, select_count기반 핸들링
//3. 추천된결과 목록창 만들어야함 (이떄 뜨는 리스트는 what_firstStep_select와 second로 태그기반으로 추천, 위에 태그도 이 변수기반으로 출력) -> 프래그먼트로 해야함.
//4. step전환 애니메이션
//5. 목록창에서 장바구니를 담았을때 메인액티비티로 데이터 전송구현(아마 와인id만 주어지면 될것같음)
//6. 메인홈화면 장바구니 갯수 구현


