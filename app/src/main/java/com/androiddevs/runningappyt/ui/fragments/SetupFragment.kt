package com.androiddevs.runningappyt.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androiddevs.runningappyt.R
import com.androiddevs.runningappyt.other.Const.KEY_FIRST_TIME_TOGGLE
import com.androiddevs.runningappyt.other.Const.KEY_NAME
import com.androiddevs.runningappyt.other.Const.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setup.*
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject    // use like this for primitive datatypes. It will not work if the field is private.
    var isFirstAppOpen = true     // 'lateinit' modifier is not allowed on properties of primitive types

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                    // Pop up to a given destination before navigating. This pops all non-matching destinations
                    // from the back stack until this destination is found.
                .setPopUpTo(R.id.setupFragment, true)   // true to also pop the given destination from the back stack.
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment
                //savedInstanceState,
                //navOptions
            )
        }

        tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()
            if(success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        if(name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()   // you can also use commit() here. apply() is asynchron.. while commit() is synchron..

        val toolbarText = "Let's go, $name!"

        requireActivity().tvToolbarTitle.text = toolbarText    // cool it is. Accessing activitymain view in fragment

        return true
    }

}