package com.dicoding.dicodingevent.data.response

data class EventResponse(
	val listEvents: List<ListEventsItem?>? = null,
	val error: Boolean? = null,
	val message: String? = null
)


