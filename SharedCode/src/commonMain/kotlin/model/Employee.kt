package com.sampleemployee.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(val data: Array<Data>,val status: String)