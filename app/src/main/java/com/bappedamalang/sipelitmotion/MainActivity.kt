package com.bappedamalang.sipelitmotion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bappedamalang.sipelitmotion.customView.BottomNavigationBehavior
import com.bappedamalang.sipelitmotion.home.HomeFragment
import com.bappedamalang.sipelitmotion.profil.ProfilFragment
import com.bappedamalang.sipelitmotion.search.SearchFragment
import com.bappedamalang.sipelitmotion.upload.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.TextUtils
import com.bappedamalang.sipelitmotion.auth.LoginActivity
import id.flwi.util.ActivityUtil


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var bottomNavigationView: BottomNavigationView? = null
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val uploadFragment = UploadFragment()
    private val profilFragment = ProfilFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.navigationMyProfile -> {
                    loadFragment(profilFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationUpload -> {
                    loadFragment(uploadFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationHome -> {
                    loadFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationSearch -> {
                    loadFragment(searchFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationMenu -> {
                    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
                    drawer.openDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (TextUtils.isEmpty(ActivityUtil.getSharedPreferenceString(this, TOKEN))) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //
        val layoutParams = bottomNavigationView!!.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehavior()

        bottomNavigationView!!.selectedItemId = R.id.navigationHome

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {


        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // method untuk load fragment yang sesuai
    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
            return true
        }
        return false
    }
}

