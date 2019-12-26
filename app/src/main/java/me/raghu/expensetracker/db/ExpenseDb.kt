package me.raghu.expensetracker.db


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        Expense::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDb : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

}
