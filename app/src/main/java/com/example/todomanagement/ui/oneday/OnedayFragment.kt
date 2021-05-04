package com.example.todomanagement.ui.oneday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentOnedayBinding

class OnedayFragment : Fragment() {

    private lateinit var onedayViewModel: OnedayViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        onedayViewModel =
                ViewModelProvider(this).get(OnedayViewModel::class.java)
        val binding: FragmentOnedayBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_oneday, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.onedayViewModel = onedayViewModel

        return binding.root
    }
}