package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R

//가격대 다이얼로그
class PriceDialog(context: Context){
    private val dialog = Dialog(context)
    //레이아웃을 보여줌.
    fun Show(){
        dialog.setContentView(R.layout.select_price_range)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        val min_edit = dialog.findViewById<EditText>(R.id.minP)//최소금액입력 edittext
        val max_edit = dialog.findViewById<EditText>(R.id.maxP) //최대금액입력 edittext
        val done = dialog.findViewById<Button>(R.id.enter) //입력버튼
        //입력버튼시 리스너
        done.setOnClickListener {
            onClickedListener.onClicked(min_edit.text.toString().toInt(), max_edit.text.toString().toInt())
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{ //3
        fun onClicked(min_money: Int, max_money:Int) //인터페이스를 통해 프래그먼트에서 값을 받을수있음.
    }
    private lateinit var onClickedListener: ButtonClickListener //1
    fun setOnClickedListener(listener: ButtonClickListener) { //2
        onClickedListener = listener
    }
    //흐름 : 다이얼로그에서 입력 버튼클릭시 -> onClickedListener(1) 변수가 setOnClickedListener함수(2)를 통해 ButtonClickListener(3)로 초기화됨.
    //이 ButtonClickListener(3) 은 인터페이스 이므로 다시 기존의 프래그먼트(상세검색_1)에서 구현함으로써 프래그먼트에서도 값을 전달받게됨. -> 뷰모델저장
}



//음식 다이얼로그
class FoodDialog(context: Context){
    private val dialog = Dialog(context)
    //레이아웃을 보여줌.
    fun Show(){
        dialog.setContentView(R.layout.detail_food)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //다이얼로그 테두리 둥글게

        var count_Item = 0 //1개만 선택가능
        var select_index = 0
        val FoodList  = ArrayList<Button>()
        FoodList.add(dialog.findViewById<Button>(R.id.food_meat))//고기류 0
        FoodList.add(dialog.findViewById<Button>(R.id.food_sea))//해산물 1
        FoodList.add(dialog.findViewById<Button>(R.id.food_cheeze)) //치즈류 2
        FoodList.add(dialog.findViewById<Button>(R.id.food_fruit))//과일류 3
        FoodList.add(dialog.findViewById<Button>(R.id.food_dessert))// 디저트류 4
        FoodList.add(dialog.findViewById<Button>(R.id.food_others))//기타 5
        val done = dialog.findViewById<Button>(R.id.done_btn) //완료버튼

        for (list in FoodList){
            Change_font_size(list)
        }
        //반복문 안에넣으면 에러가남
        FoodList[0].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[0].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[0].setBackgroundResource(R.drawable.button_round_white_red)
            }
            select_index = 0
        }

        FoodList[1].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[1].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[1].setBackgroundResource(R.drawable.button_round_white_red)

            }
            select_index = 1
        }

        FoodList[2].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[2].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[2].setBackgroundResource(R.drawable.button_round_white_red)

            }
            select_index = 2
        }

        FoodList[3].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[3].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[3].setBackgroundResource(R.drawable.button_round_white_red)

            }
            select_index = 3
        }

        FoodList[4].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[4].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[4].setBackgroundResource(R.drawable.button_round_white_red)

            }
            select_index = 4
        }

        FoodList[5].setOnClickListener {
            if(count_Item == 0)
            {
                FoodList[5].setBackgroundResource(R.drawable.button_round_white_red)
                count_Item++

            }
            else{
                FoodList[select_index].setBackgroundResource(R.drawable.button_round_white)
                FoodList[5].setBackgroundResource(R.drawable.button_round_white_red)

            }
            select_index = 5
        }


        //완료버튼시 리스너
        done.setOnClickListener {
            onClickedListener.onClicked(select_index)
            //뭘선택했는지는 Detail_Search_Fragment1에서 뷰모델로 넘김
            dialog.dismiss()
        }
        dialog.show()
    }
    interface ButtonClickListener{
        fun onClicked(select_index:Int) //인터페이스를 통해 프래그먼트에서 값을 받을수있음.
    }
    private lateinit var onClickedListener: ButtonClickListener
    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
    //클릭이벤트 작동방식은 위의 가격대 다이얼로그와 동일

    fun Change_font_size(btn : Button){
        var content = btn.text.toString() + " "
        var spanningString : SpannableString = SpannableString(content)
        val start : Int = content.indexOf(" ") //문자 폰트를 바꿀 시작지점
        val end : Int = content.length //끝지점
        //0.5f ->기존보다 0.5배
        spanningString.setSpan(RelativeSizeSpan(0.5f),start,end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        btn.setText(spanningString)
    }


}
