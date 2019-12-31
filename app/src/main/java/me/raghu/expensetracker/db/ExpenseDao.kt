package me.raghu.expensetracker.db

import androidx.paging.DataSource
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
    fun fetchExpenses(): DataSource.Factory<Int, Expense>

    @Query("DELETE FROM expense WHERE id = :expenseId")
    fun deleteExpense(expenseId:Int)

    @Query("SELECT * FROM expense WHERE date BETWEEN :from AND :to")
    fun expenseInrange(from: Date, to: Date): List<Expense>

    @Query("SELECT SUM(expense_amount) as total FROM expense where date BETWEEN :startDay AND :endDay ORDER BY datetime(date) DESC")
    fun expenseForCurrentMonth(
        startDay: Date?,
        endDay: Date?
    ): Float
}