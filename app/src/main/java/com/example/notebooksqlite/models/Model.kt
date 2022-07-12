package com.example.notebooksqlite.models

import android.net.Uri

data class Model(
    val id: String,
    val title: String,
    val content: String,
    val uri: String
)