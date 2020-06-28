package com.sampleemployee.model

import kotlinx.serialization.Serializable

@Serializable
data class MainData(val data: Array<Data>,val status: String)