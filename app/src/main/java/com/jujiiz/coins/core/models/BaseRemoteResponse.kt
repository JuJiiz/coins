package com.jujiiz.coins.core.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseRemoteResponse<T>(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: T?,
)