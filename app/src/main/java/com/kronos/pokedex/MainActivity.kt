package com.kronos.pokedex

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.Menu
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.kronos.core.extensions.binding.activityBinding
import com.kronos.core.util.validatePermission
import com.kronos.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by activityBinding<ActivityMainBinding>(R.layout.activity_main)
    private var grantedAll = false
    private var grantedFullStorage = false
    private var isBackPressedOnce = false
    private var navController:NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            lifecycleOwner = this@MainActivity
            setContentView(root)
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkFullStorageAccess()
        } else {
            grantedAll =
                validatePermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) && validatePermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && validatePermission(
                    this,
                    Manifest.permission.INTERNET
                )
            if (!grantedAll) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                    ),
                    1
                )
            } else {
                init()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkFullStorageAccess() {
        grantedFullStorage = Environment.isExternalStorageManager()

        if (!grantedFullStorage) {
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(com.kronos.resources.R.string.permission_dialog_message))
                .setTitle(getString(com.kronos.resources.R.string.permission_dialog_title))
                .setPositiveButton(com.kronos.resources.R.string.ok) { dialogInterface, _ ->
                    com.kronos.core.util.startActivityForResult(
                        this,
                        Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION,
                        150
                    )
                    dialogInterface.dismiss()
                }
                .setNegativeButton(com.kronos.resources.R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    finish()
                }
                .create()
                .show()
        } else {
            init()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var grantedPermissions = true
        for (grantResult in grantResults) {
            grantedPermissions =
                grantedPermissions and (grantResult == PackageManager.PERMISSION_GRANTED)
        }
        if (grantedPermissions) {
            init()
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 150) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                checkFullStorageAccess()
            }
        }
    }

    private fun init() {
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        if(navController == null)
            navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pokedex,
                R.id.nav_egg_groups,
                R.id.nav_types,
                R.id.nav_abilities,
                R.id.nav_move_list,
                R.id.nav_nature_list,
                R.id.nav_items,
                R.id.nav_categories_items,
                R.id.nav_berries
            ), drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)
        navView.itemIconTintList = null
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        if(navController == null)
            navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController!!.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (navController?.currentDestination?.id  == navController?.graph?.startDestinationId){
            if(isBackPressedOnce){
                finishAffinity()
                exitProcess(0)
            }
            isBackPressedOnce = true
            Toast.makeText(applicationContext,getString(R.string.back_to_exit),Toast.LENGTH_SHORT).show()
            Timer().schedule(
                timerTask {
                    isBackPressedOnce = false
                },
                2000
            )
        }else{
            super.onBackPressed()
        }
    }

}