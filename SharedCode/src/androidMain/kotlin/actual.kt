package com.sampleemployee

import com.sampleemployee.model.MainData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//val apiHelper = ApiHelperImpl(RetrofitBuilder.apiInterface)

actual fun platformName(): String {
    return "Android"
}

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

/*
actual suspend fun getDetailsFromServer() : MainData {
   // return apiHelper.getEmployeeDetails()
}*/
