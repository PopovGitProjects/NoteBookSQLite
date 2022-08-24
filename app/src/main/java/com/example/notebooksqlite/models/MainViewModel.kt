package com.example.notebooksqlite.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class MainViewModel : ViewModel() {
    val data: MutableLiveData<Model> by lazy {
        MutableLiveData<Model>()
    }
}