package com.example.notebooksqlite.screens

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.notebooksqlite.OnRecyclerViewItemClickListener
import com.example.notebooksqlite.R
import com.example.notebooksqlite.constants.Const
import com.example.notebooksqlite.databinding.FragmentAddBinding
import com.example.notebooksqlite.db.DBManager

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    private var tempImageUri = "empty"

    private var myDBManager: DBManager? = null


    private val launcher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()){
            imageUri: Uri? ->
            binding.imgAdded.load(imageUri){
                crossfade(true)
                transformations(RoundedCornersTransformation(
                    topLeft = 10f,
                    topRight = 10f,
                    bottomLeft = 10f,
                    bottomRight = 10f
                ))
            }
        tempImageUri = imageUri.toString()
        }

    companion object {
        fun newInstance() = AddFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        if (requireContext() != null){
            myDBManager = DBManager(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAction()
    }
    private fun buttonAction() = with(binding){
        fabAddImage.setOnClickListener {
            imageLayout.visibility = View.VISIBLE
            fabAddImage.visibility = View.GONE
        }
        imgBtnDelete.setOnClickListener {
            imageLayout.visibility = View.GONE
            fabAddImage.visibility = View.VISIBLE
        }
        imgBtnEdit.setOnClickListener {
            launcher.launch(Const.MIME_TYPE_IMAGE)
        }
        fabCheck.setOnClickListener {
            val title = edtTitle.text.toString()
            val note = edtNote.text.toString()
            if (title != "" && note != ""){
                myDBManager?.insertToDB(title, note, tempImageUri)
                findNavController().navigate(R.id.action_addFragment_to_mainFragment)
                Toast.makeText(requireContext(), "Entry added to Data Base", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        myDBManager?.openDB()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        myDBManager?.closeDb()
    }
}