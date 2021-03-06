package com.example.notebooksqlite.screens

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebooksqlite.MainActivity
import com.example.notebooksqlite.OnRecyclerViewItemClickListener
import com.example.notebooksqlite.R
import com.example.notebooksqlite.adapters.Adapter
import com.example.notebooksqlite.databinding.FragmentMainBinding
import com.example.notebooksqlite.db.DBManager

class MainFragment : Fragment(), OnRecyclerViewItemClickListener {
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val adapter = Adapter(ArrayList(), object : OnRecyclerViewItemClickListener {
        override fun onClick() {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    })

    private var myDBManager: DBManager? = null

    companion object {
        fun newInstance() = MainFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        if (requireContext() != null){
            myDBManager = DBManager(requireContext())
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRc()
        toolBar()
        fabAction()
    }
    private fun toolBar() = with(binding){
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        collapsingTb.setupWithNavController(tbFirst,findNavController(), appBarConfig)
        collapsingTb.title = "Notebook"
    }
    private fun fabAction() = with(binding){
        fabGoToAddFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }
    private fun initRc() = with(binding){
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        rvNotes.adapter = adapter
    }
    private fun fillAdapter(){
        myDBManager?.let { adapter.updateAdapter(it.readDBData()) }
    }

    override fun onResume() {
        super.onResume()
        myDBManager?.openDB()
        fillAdapter()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDBManager?.closeDb()
        _binding = null
    }

    override fun onClick() {
        findNavController().navigate(R.id.action_mainFragment_to_addFragment)
    }
}