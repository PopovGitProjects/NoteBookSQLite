package com.example.notebooksqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notebooksqlite.databinding.ActivityMainBinding
import com.example.notebooksqlite.screens.AddFragment

class MainActivity : AppCompatActivity(), OnRecyclerViewItemClickListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, AddFragment.newInstance())
            .commit()
    }
}