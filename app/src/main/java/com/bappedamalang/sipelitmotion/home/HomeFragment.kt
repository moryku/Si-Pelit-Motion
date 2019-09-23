package com.bappedamalang.sipelitmotion.home

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bappedamalang.sipelitmotion.R

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}