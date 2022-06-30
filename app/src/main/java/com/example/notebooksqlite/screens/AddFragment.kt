package com.example.notebooksqlite.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notebooksqlite.databinding.FragmentAddBinding
import com.example.notebooksqlite.models.AddViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private var launcher: ActivityResultLauncher<Intent>? = null

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                val intentData = Uri.parse(result?.data.toString())
                binding.imgAdded.setImageURI(intentData)
                Log.d("My", intentData.toString())
            }
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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            launcher?.launch(intent)
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