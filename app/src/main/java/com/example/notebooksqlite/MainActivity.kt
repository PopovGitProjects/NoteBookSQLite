package com.example.notebooksqlite

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notebooksqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        registerPermissionListener()
        checkReadExternalPermission()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkReadExternalPermission(){
        when{
            ContextCompat
                .checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast
                    .makeText(this, "Read external storage granted", Toast.LENGTH_SHORT)
                    .show()}
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Toast
                    .makeText(this, "We need your permissions", Toast.LENGTH_SHORT)
                    .show()}
            else -> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    }
    private fun registerPermissionListener(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
                if (it){
                    Toast
                        .makeText(this, "Permission granted", Toast.LENGTH_SHORT)
                        .show()
                }else {
                    Toast
                        .makeText(this, "Permissions denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

}