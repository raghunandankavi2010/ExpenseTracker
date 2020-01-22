package me.raghu.expensetracker.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.utils.HeightTopWindowInsetsListener
import me.raghu.expensetracker.utils.NoopWindowInsetsListener
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector, NavigationHost {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var content: FrameLayout
    private lateinit var navController: NavController

    private lateinit var statusScrim: View
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            ViewCompat.onApplyWindowInsets(root,insets)

            view.updatePadding(
                left = insets.systemWindowInsetLeft,
                right = insets.systemWindowInsetRight
            )
            insets.replaceSystemWindowInsets(
                0, insets.systemWindowInsetTop,
                0, insets.systemWindowInsetBottom
            )

            insets
        }

        content = findViewById(R.id.content_container)
        content.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Make the content ViewGroup ignore insets so that it does not use the default padding
        content.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)

        statusScrim = findViewById(R.id.status_bar_scrim)
        statusScrim.setOnApplyWindowInsetsListener(HeightTopWindowInsetsListener)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.expenseFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home)
        }

    }

    override fun androidInjector(): DispatchingAndroidInjector<Any?> {
        return androidInjector
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun registerToolbarWithNavigation() {
        appBarConfiguration = AppBarConfiguration(setOf(R.id.expenseFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home)
        }
    }

}

