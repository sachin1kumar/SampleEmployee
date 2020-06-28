package com.manager.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleemployee.api.CommonApi
import com.sampleemployee.model.Data
import com.sampleemployee.model.MainData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {

    private val detail = MutableLiveData<Array<Data>>()
    private val api = CommonApi()
    fun fetchDetail() {
        //val apiHelper = ApiHelperImpl(RetrofitBuilder.apiInterface)
        viewModelScope.launch {
            try {
                //val details = apiHelper.getEmployeeDetails()
                //detail.postValue(getEmployeeDetailsFromServer())
                api.getEmployeeDetails(
                    success = {
                        launch(Dispatchers.Main) {
                            Log.e("Sachin:","fetchDetail:success:"+ it[0].employee_name)
                            detail.postValue(it)
                        }
                    },
                    failure = ::handleError
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDetail(): LiveData<Array<Data>> {
        return detail
    }

    private fun handleError(ex: Throwable?) {
        ex?.printStackTrace()
    }

}