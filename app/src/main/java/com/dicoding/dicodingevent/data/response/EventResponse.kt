package com.dicoding.dicodingevent.data.response

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @field:SerializedName("listEvents")
    val listEvents: List<ListEventsItem?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)


