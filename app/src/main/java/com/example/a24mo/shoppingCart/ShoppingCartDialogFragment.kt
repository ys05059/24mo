package com.example.a24mo.shoppingCart

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentShoppingCartDialogBinding
import com.example.fitapet.navfragment.ShoppingCart

class ShoppingCartDialogFragment : Fragment() {
    private var _binding: FragmentShoppingCartDialogBinding? = null
    private val binding get() = _binding!!
    val shoppingCarts= mutableListOf<ShoppingCart>()
    val shoppingCartListAdapter= ShoppingCartListAdapter(shoppingCarts)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingCartDialogBinding.inflate(inflater,container,false)
//        var actionBar = (activity as MainActivity?)!!.supportActionBar
//
        //actionBar?.setCustomView(R.id.menu_friend)

//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)

        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리1","R.drawable.wine1","레드와인",
                                        "프랑스산", "높음", "낮음", "가벼움", "중간"))
        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리2","R.drawable.wine2","화이트와인",
                                        "프랑스산", "중간", "중간", "무거움", "낮음"))
        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리3","R.drawable.wine1","로제와인",
                                        "프랑스산", "높음", "높음", "중간", "중간"))
        shoppingCarts.add(ShoppingCart("와인 이름 들어갈 자리4","R.drawable.wine2","레드와인",
                                        "프랑스산", "낮음", "낮음", "가벼움", "높음"))


        binding.recylcerView.layoutManager= LinearLayoutManager(requireContext())
        binding.recylcerView.adapter=shoppingCartListAdapter
        shoppingCartListAdapter.setItemClickListener(object : ShoppingCartListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                //loadFragment()
            }

        })

        return binding.root
    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}