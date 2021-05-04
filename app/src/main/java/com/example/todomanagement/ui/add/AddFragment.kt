package com.example.todomanagement.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentAddBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import timber.log.Timber

class AddFragment : Fragment() {

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化view model
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        val binding: FragmentAddBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_add, container, false)
        //设置fab和 日期时间选择控件
        binding.saveTaskFab.setOnClickListener {
            saveTask()
        }

        binding.btnDateTimePicker.setOnClickListener {
            pickTime()
            pickDate()
        }

        return binding.root
    }

    private fun pickTime() {
        val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(MaterialTimePicker.STYLE_NORMAL)
                .setMinute(MaterialTimePicker.STYLE_NORMAL)
                .build()

        timePicker.show(childFragmentManager, "选择时间")
        timePicker.addOnPositiveButtonClickListener {
            //获取时间
            Timber.d(timePicker.minute.toString())
        }
    }

    private fun pickDate() {
        val datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("选择日期")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()

        datePicker.show(childFragmentManager, "选择")
        //获取日期
        datePicker.addOnPositiveButtonClickListener {

        }
    }

    /**
     * 更新任务
     */
    private fun saveTask() {

    }
}