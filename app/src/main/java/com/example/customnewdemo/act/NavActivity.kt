package com.example.customnewdemo.act

import android.app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Gravity

import android.widget.FrameLayout

import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.FragmentNavigator

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.customnewdemo.R
import com.example.customnewdemo.databinding.ActivityNavBinding
import com.example.customnewdemo.fragment.nav.HomeFragment
import com.example.customnewdemo.fragment.nav.ImgFragment
import com.example.customnewdemo.fragment.nav.NewsFragment
import com.example.customnewdemo.fragment.nav.UserFragment
import com.example.customnewdemo.utils.LogUtils
import com.example.customnewdemo.utils.dip2px
import com.example.customnewdemo.widget.FixFragmentNavigator
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.storm.navdemo.widget.DotView

/***
 * navaigation 的使用.
 */
class NavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 关联 navcontentview 和 底部bottom view
//        NavigationUI.setupWithNavController(binding.navMainBottom,navController

        var navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_host) as NavHostFragment
        // 初始化导航控制器.
        navController = navHostFragment.navController

        val navigatorProvider = navController.navigatorProvider
        val fixFragmentNavigator =
            FixFragmentNavigator(this, navHostFragment.childFragmentManager, navHostFragment.id)
        val defaultFragmentNavigator = FragmentNavigator(this,navHostFragment.childFragmentManager,navHostFragment.id)
//        navigatorProvider.addNavigator(defaultFragmentNavigator)
        val navGraph = initNavGraph(navigatorProvider, defaultFragmentNavigator)

        navController.graph = navGraph


//        NavigationUI.setupWithNavController(binding.navMainBottom, navController)
        setUpRedView()
        binding.navMainBottom.setupWithNavController(navController)

        setUpListener()
    }

    private var navMenuView: BottomNavigationMenuView? = null
    private var redPoint: DotView? = null

    /**
     * 拓展 设置红点
     */
    private fun setUpRedView() {


        for (i in 0..binding.navMainBottom.childCount) {
            val child = binding.navMainBottom.getChildAt(i)
            if (child is BottomNavigationMenuView) {
                navMenuView = child
                break
            }
        }

        navMenuView?.let {

            var params = FrameLayout.LayoutParams(8.dip2px(), 0)
            params.gravity = Gravity.CENTER_HORIZONTAL
            params.leftMargin = 16.dip2px()
            params.topMargin = 4.dip2px()
            var itemView = it.getChildAt(1) as BottomNavigationItemView
            redPoint = DotView(this)
            itemView.addView(redPoint, params)
            redPoint?.show = true
        }

    }

    private fun setUpListener() {

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.homeFragment -> {

                    LogUtils.d(TAG, "homeFragment")

                    val checked = binding.navMainBottom.menu.getItem(0).isChecked;
                    if (!checked) {
                        binding.navMainBottom.menu.getItem(0).isChecked = true;
                    }


                }

                R.id.newsFragment -> {
                    LogUtils.d(TAG, "newsfragment")

                    val checked = binding.navMainBottom.menu.getItem(1).isChecked;
                    if (!checked) {
                        binding.navMainBottom.menu.getItem(1).isChecked = true;
                    }

                }

                R.id.imgFragment -> {
                    LogUtils.d(TAG, "imgFragment")
                    val checked = binding.navMainBottom.menu.getItem(2).isChecked;
                    if (!checked) {
                        binding.navMainBottom.menu.getItem(2).isChecked = true;
                    }

                }

                R.id.userFragment -> {

                    LogUtils.d(TAG, "userragment")
                    val checked = binding.navMainBottom.menu.getItem(3).isChecked;
                    if (!checked) {
                        binding.navMainBottom.menu.getItem(3).isChecked = true;
                    }

                }
            }
        }

        binding.navMainBottom.setOnItemSelectedListener {
//            navController.navigate(it.itemId)
            navController.navigate(it.itemId)

            when (it.itemId) {
                R.id.homeFragment -> {



                }

                R.id.newsFragment -> {
                    redPoint?.show = false



                }

                R.id.imgFragment -> {


                }

                R.id.userFragment -> {


                }
            }

            true
        }


    }


    companion object {
        val TAG = "NavActivity"


        public fun startSelf(activity: Activity) {
            var intent = Intent(activity, NavActivity::class.java)
            activity.startActivity(intent)
        }
    }


    fun initNavGraph(
        navigatorProvider: NavigatorProvider, fragmentNavigator: FragmentNavigator
    ): NavGraph {
        val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))
        val createDestination = fragmentNavigator.createDestination()
        with(createDestination) {
            id = R.id.homeFragment

            HomeFragment::class.java.canonicalName?.let { setClassName(it) }
            navGraph.addDestination(this)
        }


        val createDestination1 = fragmentNavigator.createDestination()
        with(createDestination1) {
            id = R.id.newsFragment
            NewsFragment::class.java.canonicalName?.let { setClassName(it) }
            navGraph.addDestination(this)
        }
        val createDestination2 = fragmentNavigator.createDestination()
        with(createDestination2) {
            id = R.id.imgFragment
             ImgFragment::class.java.canonicalName?.let { setClassName(it) }
            navGraph.addDestination(this)
        }
        val createDestination3 = fragmentNavigator.createDestination()
        with(createDestination3) {
            id = R.id.userFragment
             UserFragment::class.java.canonicalName?.let { setClassName(it) }
            navGraph.addDestination(this)
        }


        navGraph.setStartDestination(createDestination.id)

        return navGraph
    }


    override fun onBackPressed() {

        super.onBackPressed()
    }


}