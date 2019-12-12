package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Px
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.mobv.databinding.ActivityMainBinding
import com.example.mobv.databinding.ActivityMessageBinding
import com.example.mobv.fragments.RoomsFragment
import com.example.mobv.fragments.UsersFragment
import com.example.mobv.interfaces.OnFocusListener
import com.example.mobv.model.repository.UserRepository
import com.example.mobv.session.SessionManager
import com.example.mobv.viewModels.MainViewModel
import com.example.mobv.viewModels.MainViewModelFactory
import com.example.mobv.viewModels.RoomMessageViewModel
import com.example.mobv.viewModels.RoomMessageViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.internal.userAgent
import java.lang.Exception
import java.util.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = MainViewModelFactory(this)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.loggedUser = SessionManager.get(this).getSessionData()!!
        binding.mainViewModel = mainViewModel

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(RoomsFragment(), "Miestnosti")
        viewPagerAdapter.addFragment(UsersFragment(), "Kontakty")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                (viewPagerAdapter.getItem(position) as OnFocusListener).onFocus()
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, @Px positionOffsetPixels: Int) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.logout -> {
                mainViewModel.logout()
                startActivity(Intent(this@MainActivity, StartActivity::class.java))
                finish()
                return true
            }
        }

        return false
    }


    internal inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
