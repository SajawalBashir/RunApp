package com.androiddevs.runningappyt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.runningappyt.R
import com.androiddevs.runningappyt.other.Const.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint  // see pic android -> s.s -> 116cc
class MainActivity : AppCompatActivity() {
    val TAG = "##### MainActivity.kt line "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val a = MutableLiveData<Int>()
//        a.value = 25
//        //a.postValue(105)
//
//        Timber.d("check line 33 a = ${a.value}")
//
//        a.observe(this, Observer{
//            //a.value = 25     here it will result in infinite looping
//            Timber.d("check line 36 a = ${a.value}")
//        })

        navigateToTrackingFragmentIfNeeded(intent)// dependencies will live only for service lifecycle. Also it restricts to only one instance generation.
        // For activities use @ActivityScoped

        setSupportActionBar(toolbar)  // see pic android -> s.s -> 120
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())  // navHostFragment is defined in activity_main.xml
        bottomNavigationView.setOnNavigationItemReselectedListener {  /* :D  */  }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_tracking_fragment)
        }
    }
}