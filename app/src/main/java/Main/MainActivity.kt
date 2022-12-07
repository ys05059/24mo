package Main

import Fragment.*
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
import android.view.View
import com.example.a24mo.R
import com.example.a24mo.databinding.HomeLayoutBinding

class MainActivity : AppCompatActivity(){

    lateinit var transaction : FragmentTransaction
    var presentFragment :Fragment? = null
    lateinit var fragmentManager: FragmentManager
    lateinit var homeLayoutBinding: HomeLayoutBinding
    lateinit var Recommend_transaction : FragmentTransaction
    var Recommend_presentFragment :Fragment? = null
    lateinit var viewModel  : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeLayoutBinding = HomeLayoutBinding.inflate(layoutInflater)
        setContentView(homeLayoutBinding.root) //홈화면 레이아웃 xml

        fragmentManager = supportFragmentManager
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        replaceTransaction(HomeFragment())
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

    fun changeRecommendFragment(step : Int){
//        if(Recommend_presentFragment == fragment) {
//            Toast.makeText((activity as MainActivity), "이미 해당 Fragment를 보여주고 있습니다.",
//                Toast.LENGTH_SHORT).show()
//            return
//        }
        Recommend_transaction =fragmentManager.beginTransaction()
        Recommend_transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        when(step){
            1 -> Recommend_transaction.replace(R.id.step_id,Recommend_First_Fragment()).commit()
            2 -> Recommend_transaction.replace(R.id.step_id, Recommend_Second_Fragment()).commit()
            3 -> Recommend_transaction.replace(R.id.step_id, Recommend_Result_Fragment()).commit()
        }
        Recommend_transaction.addToBackStack(null)
    }


}
