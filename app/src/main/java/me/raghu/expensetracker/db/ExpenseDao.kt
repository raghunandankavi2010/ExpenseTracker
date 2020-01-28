package me.raghu.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import java.util.*

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense):Long

    @Query("SELECT * FROM expense ORDER BY date DESC")
    suspend fun fetchExpenses(): DataSource.Factory<Int, Expense>

    @Query("DELETE FROM expense WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId:Int)

    @Query("SELECT id,date,SUM(expense_amount) as expense_amount FROM expense WHERE date BETWEEN :from AND :to GROUP BY date ORDER BY date ASC")
    suspend fun expenseInRange(from: Date, to: Date): LiveData<List<Expense>>

    @Query("SELECT SUM(expense_amount) as total FROM expense where date BETWEEN :startDay AND :endDay")
    suspend fun expenseForCurrentMonth(
        startDay: Date?,
        endDay: Date?
    ): LiveData<Float>

    @Query("UPDATE expense SET expense_type = :expenseType, expense_amount = :expenseAmount, remarks = :remarks, date = :date WHERE id = :id")
    suspend fun updateExpense(id: Int, expenseType: String, expenseAmount: String, remarks: String, date: Date)
}