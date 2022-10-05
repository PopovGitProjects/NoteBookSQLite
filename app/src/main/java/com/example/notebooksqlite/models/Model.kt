package com.example.notebooksqlite.models

data class Model(
    val id: String,
    val title: String,
    val content: String,
    val uri: String,
    val time: String,
    var edit: Boolean = false
)