package com.example.notebooksqlite.screens

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.example.notebooksqlite.constants.Const
import com.example.notebooksqlite.databinding.FragmentAddBinding
import com.example.notebooksqlite.models.AddViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    private val launcher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()){
            imageUri: Uri? ->
            binding.imgAdded.load(imageUri){
                crossfade(false)
                transformations(CircleCropTransformation())
            }
        }
    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
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
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}