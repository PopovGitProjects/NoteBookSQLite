package com.example.notebooksqlite.screens

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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
        myDBManager = DBManager(requireContext())
        return builder
            .setTitle("Delete note")
            .setIcon(R.drawable.remove)
            .setMessage("Are you sure you want to delete the entry?")
            .setNegativeButton("No"){
                    dialog, _ -> dialog.cancel()
            }
            .setPositiveButton("Yes"){ _, _ ->
                delete()
            }
            .create()
    }
    private fun delete(){
        val id = dataModel.data.value!!.id.toInt()
        myDBManager?.removeItemFromDB(id)
        Toast.makeText(requireContext(), "Note deleted!", Toast.LENGTH_SHORT).show()
        val fragmentManager = parentFragmentManager
        val mainFragment = MainFragment()
        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, mainFragment, "Tag").commit()
    }
    override fun onResume() {
        super.onResume()
        myDBManager?.openDB()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDBManager?.closeDb()
        dataModel.data.value = null
    }
}