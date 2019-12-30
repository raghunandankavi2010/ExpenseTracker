package me.raghu.expensetracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense):Long

    @Query("SELECT * FROM expense")
    fun fetchExpenses(): List<Expense>

    @Query("DELETE FROM expense WHERE expense_type = :expenseType")
    fun deleteExpense(expenseType:String)

    @Query("SELECT * FROM expense WHERE date BETWEEN :from AND :to")
    fun expenseinrange(from: Date, to: Date): List<Expense>

    @Query("SELECT SUM(expense_amount) as total FROM expense where date BETWEEN :startDay AND :endDay ORDER BY date ASC")
    fun expenseForCurrentMonth(
        startDay: Date?,
        endDay: Date?
    ): Float
}