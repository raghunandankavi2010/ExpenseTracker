package me.raghu.expensetracker.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import me.raghu.expensetracker.testing.OpenForTesting


open class MyDaggerFragment: Fragment() {

    override fun onAttach(context: Context) {
        injectMembers()
        super.onAttach(context)
    }

    protected open fun injectMembers() =
        AndroidSupportInjection.inject(this)
}