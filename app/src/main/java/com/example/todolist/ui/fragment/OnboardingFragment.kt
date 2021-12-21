package com.example.todolist.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ix.ibrahim7.todolist.R
import com.example.todolist.ui.Activity.MainActivity
import kotlinx.android.synthetic.main.onboarding_fragment.view.*
import maes.tech.intentanim.CustomIntent

/**
 * A simple [Fragment] subclass.
 */
class OnboardingFragment : Fragment() {


    private val share by lazy {
        requireContext().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    }


    lateinit var root: View
    override fun onResume() {
        super.onResume()
        //checkViewOnBoardingGetStarted()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // requireActivity().bottom_nav.visibility = View.GONE
        //requireActivity().toolbar.visibility = View.GONE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.onboarding_fragment, container, false
        )
        root= binding.getRoot()

        root.apply {
            btn_get_start.startAnimation(
                AnimationUtils.loadAnimation(requireContext(),
                    R.anim.bottom_to_up
                ))
        }

            root.btn_get_start.setOnClickListener {
                val intenttomain = Intent(activity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                startActivity(intenttomain)
                CustomIntent.customType(requireContext(), "fadein-to-fadeout")
                SavePrefsData()
            }



        return root
    }

    private fun SavePrefsData() {
        val Pref = requireActivity().getSharedPreferences("onClick", Context.MODE_PRIVATE)
        val editor = Pref.edit()
        editor.putBoolean("isClicked", true)
        editor.commit()
    }



}
