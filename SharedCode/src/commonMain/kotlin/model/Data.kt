package com.sampleemployee.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(val profile_image: String,val employee_name: String,val employee_salary: String ,val id: String, val employee_age: String)