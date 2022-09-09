package com.example.notebooksqlite.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebooksqlite.R
import com.example.notebooksqlite.adapters.Adapter
import com.example.notebooksqlite.databinding.FragmentMainBinding
import com.example.notebooksqlite.db.DBManager
import com.example.notebooksqlite.interfaces.OnRecyclerViewItemClickListener
import com.example.notebooksqlite.models.MainViewModel
import com.example.notebooksqlite.models.Model

class MainFragment : Fragment(), OnRecyclerViewItemClickListener {
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val dataModel: MainViewModel by activityViewModels()

    private val adapter = Adapter(ArrayList(), object : OnRecyclerViewItemClickListener {
        override fun onClick(item: Model) {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
            dataModel.data.value = item
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
        if (myDBManager != null){
            initSearchView()
        }

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
    private fun initSearchView() = with(binding){
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("My", "onQueryTextChange() newText: $newText")
                val list = myDBManager!!.readDBData(newText!!)
                Log.d("My", "onQueryTextChange() list: $list")
                adapter.updateAdapter(list)
//                myDBManager?.let { adapter.updateAdapter(it.readDBData(newText)) }
                return true
            }
        })
    }
    private fun initRc() = with(binding){
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        rvNotes.adapter = adapter
    }
    private fun fillAdapter(){
        val list = myDBManager!!.readDBData("")
        Log.d("My", "fillAdapter() list: $list")
        adapter.updateAdapter(list)
        if (list.size > 0){
            binding.tvNoElement.visibility = View.GONE
        }else{
            binding.tvNoElement.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        myDBManager?.openDB()
        Log.d("My", "onResume() started")
        fillAdapter()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDBManager?.closeDb()
        _binding = null
    }

    override fun onClick(item: Model) {
        findNavController().navigate(R.id.action_mainFragment_to_addFragment)
    }
}