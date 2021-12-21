package com.example.todolist.ui.Activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ix.ibrahim7.todolist.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var   appBarConfiguration : AppBarConfiguration

    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration= AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.taskFragment
        ),drawerLayout)


        getTime()

        navigation_view.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)


  /*      findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
*/
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,appBarConfiguration)

    }


    fun getTime(){
        val time = SimpleDateFormat("HH : mm").format(Date())

        val timeNum = time.substring(0, 2).toInt()
        val navigationView =
            findViewById<View>(R.id.navigation_view) as NavigationView
        val hView = navigationView.getHeaderView(0)
        val nav_txt_title = hView.findViewById(R.id.txt_header_title) as TextView

        if (timeNum > 12) {
            nav_txt_title.text ="Good Night"
//           drawerLayout.rootView.txt_header_title.text =""
        } else {
            nav_txt_title.text = "Good Morning"
        }
    }



}
