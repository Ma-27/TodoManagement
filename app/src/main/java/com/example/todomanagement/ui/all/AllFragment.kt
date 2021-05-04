package com.example.todomanagement.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentAllBinding

class AllFragment : Fragment() {

    private lateinit var allViewModel: AllViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allViewModel =
            ViewModelProvider(this).get(AllViewModel::class.java)
        val binding: FragmentAllBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all, container, false
        )

        binding.lifecycleOwner = this
        binding.allViewModel = allViewModel

        //设置fab点击响应，导航到addFragment
        binding.addTaskFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_all_to_addFragment)
        }
        return binding.root
    }
}