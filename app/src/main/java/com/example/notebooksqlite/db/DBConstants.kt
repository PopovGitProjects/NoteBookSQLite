package com.example.notebooksqlite.db

import android.provider.BaseColumns

object DBConstants: BaseColumns {
    const val TABLE_NAME = "entry"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_IMAGE_URI = "image_uri"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MyDB.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_TITLE TEXT, " +
            "$COLUMN_NAME_CONTENT TEXT," +
            "$COLUMN_IMAGE_URI TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST $TABLE_NAME"

}