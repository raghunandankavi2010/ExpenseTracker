package me.raghu.expensetracker.db

import androidx.paging.DataSource
import androidx.room.*
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

    @Query("UPDATE expense SET expense_type = :expenseType, expense_amount = :expenseAmount, remarks = :remarks, date = :date WHERE id = :id")
    fun updateExpense(id: Int, expenseType: String, expenseAmount: String, remarks: String, date: Date)
}