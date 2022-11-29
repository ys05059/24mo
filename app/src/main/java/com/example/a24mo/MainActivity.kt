package com.example.a24mo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.a24mo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var transaction: FragmentTransaction
    var presentFragment: Fragment? = null
    lateinit var fragmentManager : FragmentManager
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var informationFragment = InformationFragment()

        fragmentManager = supportFragmentManager
        binding.btnShow.setOnClickListener {
            replaceTransaction(informationFragment)
        }
    }

    fun replaceTransaction(fragment: Fragment) {
        if (presentFragment == fragment ) {
            Toast.makeText(this, "이미 해당 Fragment를 보여주고 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        transaction = fragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, fragment).commit()
        presentFragment = fragment
    }
}