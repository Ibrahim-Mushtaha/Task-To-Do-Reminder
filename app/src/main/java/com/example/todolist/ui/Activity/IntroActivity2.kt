package com.example.todolist.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ix.ibrahim7.todolist.R
import kotlinx.android.synthetic.main.activity_intro2.*

class IntroActivity2 : AppCompatActivity() {


    lateinit var   appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)


        setSupportActionBar(toolbar_intro)

        toolbar_intro.visibility = View.GONE
        val navController = findNavController(R.id.fragment)
        appBarConfiguration= AppBarConfiguration(setOf(
            R.id.onboardingFragment
        ))


        findViewById<Toolbar>(R.id.toolbar_intro)
            .setupWithNavController(navController,appBarConfiguration)

    }
}