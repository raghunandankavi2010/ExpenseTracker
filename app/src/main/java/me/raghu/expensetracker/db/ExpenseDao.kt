package me.raghu.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: Expense)

    @Query("SELECT * FROM expense")
    fun fetchExpenses(): LiveData<Expense>
}