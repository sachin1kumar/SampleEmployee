package com.sampleemployee

import com.sampleemployee.model.MainData
import kotlinx.coroutines.CoroutineDispatcher

expect fun platformName(): String
internal expect val ApplicationDispatcher: CoroutineDispatcher

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks on ${platformName()}"
}

/*
suspend fun getEmployeeDetails(): MainData {
    val client = HttpClient()
    val response: MainData = client.get("http://dummy.restapiexample.com/api/v1/employees")
    client.close()
    return response
}*/
