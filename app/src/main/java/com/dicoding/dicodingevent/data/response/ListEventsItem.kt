package com.dicoding.dicodingevent.data.response

data class ListEventsItem(
    val summary: String? = null,
    val mediaCover: String? = null,
    val registrants: Int? = null,
    val imageLogo: String? = null,
    val link: String? = null,
    val description: String? = null,
    val ownerName: String? = null,
    val cityName: String? = null,
    val quota: Int? = null,
    val name: String? = null,
    val id: Int? = null,
    val beginTime: String? = null,
    val endTime: String? = null,
    val category: String? = null
)