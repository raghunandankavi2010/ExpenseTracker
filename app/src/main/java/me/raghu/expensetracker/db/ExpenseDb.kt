package me.raghu.expensetracker.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


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
