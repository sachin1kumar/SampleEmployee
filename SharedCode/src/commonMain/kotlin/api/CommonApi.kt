package com.sampleemployee.api

import com.sampleemployee.ApplicationDispatcher
import com.sampleemployee.model.Data
import com.sampleemployee.model.MainData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommonApi {

    private val httpClient = HttpClient()

    fun getEmployeeDetails(success: (Array<Data>) -> Unit, failure: (Throwable?) -> Unit){
        GlobalScope.launch(ApplicationDispatcher){
            try {
                val url = "http://dummy.restapiexample.com/api/v1/employees"
                val json = httpClient.get<String>(url)
                print("Sachin:"+json);
                Json.nonstrict.parse(MainData.serializer(), json)
                    .data
                    .also(success)
            } catch (ex: Exception){
                failure(ex)
            }
        }
    }
}