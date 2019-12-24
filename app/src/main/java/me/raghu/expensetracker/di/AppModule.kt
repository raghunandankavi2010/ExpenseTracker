package me.raghu.expensetracker.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import me.raghu.expensetracker.db.ExpenseDao
import me.raghu.expensetracker.db.ExpenseDb

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): ExpenseDb {
        return Room
            .databaseBuilder(app, ExpenseDb::class.java, "expense.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: ExpenseDb): ExpenseDao {
        return db.expenseDao()
    }
}