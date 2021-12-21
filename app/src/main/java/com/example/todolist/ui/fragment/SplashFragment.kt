package com.example.todolist.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.todolist.R
import com.example.todolist.ui.Activity.MainActivity
import kotlinx.android.synthetic.main.fragment_splash.view.*
import maes.tech.intentanim.CustomIntent

class SplashFragment : Fragment() {


    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_splash, container, false)

        requireActivity().overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )

        root.tv_logo.startAnimation(
            AnimationUtils.loadAnimation(requireContext(),
                R.anim.splash_in
        ))

        Handler().postDelayed({
            root.tv_logo.startAnimation(
                AnimationUtils.loadAnimation(requireContext(),
                    R.anim.splash_out
            ))
            Handler().postDelayed({
                root.tv_logo.visibility= View.GONE
                checkViewOnBoardingGetStarted()
            },500)
        },900)

        return root
    }

    private fun checkViewOnBoardingGetStarted() {
        if (restorePref()) {
            val intenttomain = Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intenttomain)
            CustomIntent.customType(requireContext(), "fadein-to-fadeout")
        }else{
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment())
        }
    }


    private fun restorePref(): Boolean {
        val Pref = requireActivity().getSharedPreferences("onClick", Context.MODE_PRIVATE)
        val IsIntroActivityOpenedBefor = Pref.getBoolean("isClicked", false)
        return IsIntroActivityOpenedBefor
    }

}