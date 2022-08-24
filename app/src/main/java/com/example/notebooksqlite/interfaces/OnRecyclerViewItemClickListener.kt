package com.example.notebooksqlite.interfaces

import com.example.notebooksqlite.models.Model

interface OnRecyclerViewItemClickListener {
    fun onClick(item: Model)
}