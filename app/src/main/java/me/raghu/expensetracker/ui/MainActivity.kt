package me.raghu.expensetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.toolbar.*
import me.raghu.expensetracker.R
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit  var androidInjector: DispatchingAndroidInjector<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_home)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun androidInjector(): DispatchingAndroidInjector<Any?> {
        return androidInjector
    }
}

