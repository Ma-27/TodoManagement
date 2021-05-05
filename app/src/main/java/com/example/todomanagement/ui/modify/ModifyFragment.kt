package com.example.todomanagement.ui.modify

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentModifyBinding
import com.example.todomanagement.util.setupSnackbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class ModifyFragment : Fragment() {
    private lateinit var viewModel: ModifyViewModel

    @SuppressLint("LogNotTimber")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //获得参数
        val args: ModifyFragmentArgs = ModifyFragmentArgs.fromBundle(requireArguments())

        //初始化view model
        viewModel = ViewModelProvider(this,
                ModifyViewModelFactory(requireNotNull(this.activity).application, args.taskId))
                .get(ModifyViewModel::class.java)

        val binding: FragmentModifyBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_modify, container, false)

        // 设置life cycle owner
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel = viewModel

        //设置fab和 日期时间选择控件
        binding.btnDateTimePicker.setOnClickListener {
            pickTime()
            pickDate()
        }

        /*
        viewModel.time.observe(viewLifecycleOwner, Observer {
            val timestamp = DateTimeFormatted.convertDateTimeToMillSec(it.date,it.hour,it.minute)
            binding.tvModifyShowTime.text = DateTimeFormatted.formatDateTimeString(timestamp)
        })

         */

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun pickTime() {
        val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(viewModel.time.value!!.hour)
                .setMinute(viewModel.time.value!!.hour)
                .build()

        timePicker.show(childFragmentManager, "选择时间")
        timePicker.addOnPositiveButtonClickListener {
            //获取时间
            viewModel.time.value!!.minute = timePicker.minute
            viewModel.time.value!!.hour = timePicker.hour
        }
    }

    private fun pickDate() {
        val datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("选择日期")
                        .setSelection(viewModel.time.value?.date)
                        .build()

        datePicker.show(childFragmentManager, "选择")
        //获取日期
        datePicker.addOnPositiveButtonClickListener {
            viewModel.time.value!!.date = datePicker.selection!!
        }
    }

}