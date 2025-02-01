package com.example.simpleapp.app

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    var id: String,
    var title: String,
    var content: String,
    var url: String,
    var headerImageUrl: String,
    var publishDate: String,
    var type: String,
    var topics: List<String>,
    var authors: List<String>
)