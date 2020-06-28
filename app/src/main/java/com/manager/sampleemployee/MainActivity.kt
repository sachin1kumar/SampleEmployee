package com.manager.sampleemployee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manager.myapplication.viewmodel.EmployeeViewModel
import com.sampleemployee.api.CommonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var api: CommonApi
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        api = CommonApi()
        //initViewModel()
        initApiCall()
    }

    private fun initViewModel() {
        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel::class.java)
    }

    private fun initApiCall() {
       // employeeViewModel.getDetail().observe(this, Observer {

        //})

        api.getEmployeeDetails(
            success = {
                launch(Dispatchers.Main) {
                    Log.e("Sachin:","fetchDetail:success:"+ it[0].employee_name)
                    findViewById<TextView>(R.id.tv_text).text = it[0].employee_name
                }
            },
            failure = ::handleError
        )

        //fetch messages
        //employeeViewModel.fetchDetail()
    }

    private fun handleError(ex: Throwable?) {
        ex?.printStackTrace()
    }
}