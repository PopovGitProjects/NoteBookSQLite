package com.example.notebooksqlite.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.notebooksqlite.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBManager(context: Context) {
    private val dbHelper = DBHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDB(){
        db = dbHelper.writableDatabase
    }
    suspend fun insertToDB(title: String, content: String, uri: String, time: String)
    = withContext(Dispatchers.IO){
        val values = ContentValues().apply {
            put(DBConstants.COLUMN_NAME_TITLE, title)
            put(DBConstants.COLUMN_NAME_CONTENT, content)
            put(DBConstants.COLUMN_IMAGE_URI, uri)
            put(DBConstants.COLUMN_TIME, time)
        }
        db?.insert(DBConstants.TABLE_NAME, null, values)
    }
    @SuppressLint("Range")
    suspend fun readDBData(searchText: String): ArrayList<Model> = withContext(Dispatchers.IO){
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
                val time = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_TIME))
                dataList.add(Model(
                    id.toString(),
                    title.toString(),
                    content.toString(),
                    uri.toString(),
                    time.toString()
                ))
            }
        }
        cursor?.close()
        return@withContext dataList
    }
    fun removeItemFromDB(id: Int){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(DBConstants.TABLE_NAME, selection, null)
    }
    suspend fun updateItemToDB(id: Int, title: String, content: String, uri: String, time: String)
    = withContext(Dispatchers.IO){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DBConstants.COLUMN_NAME_TITLE, title)
            put(DBConstants.COLUMN_NAME_CONTENT, content)
            put(DBConstants.COLUMN_IMAGE_URI, uri)
            put(DBConstants.COLUMN_TIME, time)
        }
        db?.update(DBConstants.TABLE_NAME, values, selection, null)
    }
    fun closeDb(){
        dbHelper.close()
    }
}