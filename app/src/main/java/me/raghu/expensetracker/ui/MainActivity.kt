package me.raghu.expensetracker.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.utils.HeightTopWindowInsetsListener
import me.raghu.expensetracker.utils.NoopWindowInsetsListener
import javax.inject.Inject


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), HasAndroidInjector, NavigationHost {


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>
    private lateinit var content: FrameLayout
    private lateinit var navController: NavController
    private lateinit var statusScrim: View

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(root) {
                view, insets ->
            ViewCompat.onApplyWindowInsets(view,insets)

            view.updatePadding(
                left = insets.systemWindowInsetLeft,
                right = insets.systemWindowInsetRight
            )

           WindowInsetsCompat.Builder(insets).setSystemWindowInsets(
                    Insets.of(
                        0, insets.systemWindowInsetTop,
                        0, insets.systemWindowInsetBottom)).build()

        }

        content = findViewById(R.id.content_container)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            }else {
                content.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
        // Make the content ViewGroup ignore insets so that it does not use the default padding
        content.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)

        statusScrim = findViewById(R.id.status_bar_scrim)
        statusScrim.setOnApplyWindowInsetsListener(HeightTopWindowInsetsListener)

        // navController initialization
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


    }

    override fun androidInjector(): DispatchingAndroidInjector<Any?> {
        return androidInjector
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

}

