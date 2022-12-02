package com.example.a24mo

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.example.a24mo.shoppingCart.ShoppingCartDialogFragment

class MainActivity : FragmentActivity() {
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button);

        button.setOnClickListener{
            val fragment1 = ShoppingCartDialogFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.linearlayout, fragment1)
                .commit()
        }
    }
}