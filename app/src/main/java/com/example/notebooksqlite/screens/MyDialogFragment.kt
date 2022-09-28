package com.example.notebooksqlite.screens

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.notebooksqlite.R
import com.example.notebooksqlite.db.DBManager
import com.example.notebooksqlite.models.MainViewModel

class MyDialogFragment : DialogFragment() {

    private var myDBManager: DBManager? = null
    private val dataModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val  builder = AlertDialog.Builder(requireContext())
        return builder
            .setTitle("Delete note")
            .setIcon(R.drawable.remove)
            .setMessage("Are you sure you want to delete the entry?")
            .setNegativeButton("No", null)
            .setPositiveButton("Yes"){ _: DialogInterface, _: Int ->
                myDBManager?.removeItemFromDB(dataModel.data.value!!.id)
                Log.d("My", "dataModel = ${dataModel.data.value?.id}")
                Toast.makeText(requireContext(), "Note deleted!", Toast.LENGTH_SHORT).show()
            }
            .create()
    }
    override fun onResume() {
        super.onResume()
        myDBManager?.openDB()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDBManager?.closeDb()
    }
}