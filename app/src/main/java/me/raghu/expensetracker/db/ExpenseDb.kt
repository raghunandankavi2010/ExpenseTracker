package me.raghu.expensetracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 *  Expense Db used in [@AppModule] to inject db
 */

@Database(
    entities = [
        Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ExpenseDb : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

}
