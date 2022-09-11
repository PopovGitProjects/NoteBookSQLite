package com.example.notebooksqlite.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.notebooksqlite.models.Model

class DBManager(context: Context) {
    private val dbHelper = DBHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDB(){
        db = dbHelper.writableDatabase
    }

    fun insertToDB(title: String, content: String, uri: String){
        val values = ContentValues().apply {
            put(DBConstants.COLUMN_NAME_TITLE, title)
            put(DBConstants.COLUMN_NAME_CONTENT, content)
            put(DBConstants.COLUMN_IMAGE_URI, uri)
        }
        db?.insert(DBConstants.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readDBData(searchText: String): ArrayList<Model>{
        val dataList = ArrayList<Model>()
        val selection = "${DBConstants.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(DBConstants.TABLE_NAME,
            null,
            selection,
            arrayOf("%$searchText%"),
            null,
            null,
            null
        )
        with(cursor){
            while (this?.moveToNext()!!){
                val id = cursor?.getString(cursor.getColumnIndex(BaseColumns._ID))
                val title = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME_TITLE))
                val content = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME_CONTENT))
                val uri = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_IMAGE_URI))
                dataList.add(Model(id.toString(), title.toString(), content.toString(), uri.toString()))
            }
        }
        cursor?.close()
        return dataList
    }
    fun removeItemFromDB(id: String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(DBConstants.TABLE_NAME, selection, null)
    }
    fun updateItemToDB(id: Int, title: String, content: String, uri: String){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DBConstants.COLUMN_NAME_TITLE, title)
            put(DBConstants.COLUMN_NAME_CONTENT, content)
            put(DBConstants.COLUMN_IMAGE_URI, uri)
        }
        db?.update(DBConstants.TABLE_NAME, values, selection, null)
    }
    fun closeDb(){
        dbHelper.close()
    }
}