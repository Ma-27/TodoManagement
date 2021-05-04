package com.example.todomanagement.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        overviewViewModel =
                ViewModelProvider(this, OverviewModelFactory
                (requireNotNull(activity).application)).get(OverviewViewModel::class.java)

        val binding: FragmentOverviewBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_overview, container, false
        )
        binding.lifecycleOwner = this
        binding.allViewModel = overviewViewModel

        //设置fab点击响应，导航到addFragment
        binding.addTaskFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_all_to_addFragment)
        }
        return binding.root
    }
}