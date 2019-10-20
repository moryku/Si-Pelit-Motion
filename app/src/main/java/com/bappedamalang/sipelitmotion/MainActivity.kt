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
import android.text.TextUtils
import com.bappedamalang.sipelitmotion.auth.LoginActivity
import com.bappedamalang.sipelitmotion.interfaces.SearchHome
import id.flwi.util.ActivityUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import com.bappedamalang.sipelitmotion.interfaces.SuccessAddKajian
import com.bappedamalang.sipelitmotion.management.MyDocumentFragment


class MainActivity : AppCompatActivity(){

    private var bottomNavigationView: BottomNavigationView? = null
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private var uploadFragment = UploadFragment()
    private val profilFragment = ProfilFragment()
    private val manageFragment = MyDocumentFragment()

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
                R.id.myDocument -> {
                    loadFragment(manageFragment)
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

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SuccessAddKajian) {
         uploadFragment = UploadFragment()
        loadFragment(homeFragment)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SearchHome) {
        loadFragment(searchFragment)
    }
}

