package me.raghu.expensetracker.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.raghu.expensetracker.ui.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}