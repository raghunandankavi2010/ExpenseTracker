package me.raghu.expensetracker.ui

interface NavigationHost {

    /** Called by MainNavigationFragment to setup it's toolbar with the navigation controller. */
    fun registerToolbarWithNavigation()
}
