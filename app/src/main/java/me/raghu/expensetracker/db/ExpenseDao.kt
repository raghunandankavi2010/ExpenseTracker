package me.raghu.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import java.util.*

/**
 * Expense Dao for expense
 * https://medium.com/androiddevelopers/room-coroutines-422b786dc4c5
 */
@Dao
interface ExpenseDao {

    // using suspend ensures the code runs off ui thread.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense):Long

    // no need for suspend as it is converted to livedata
    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun fetchExpenses(): DataSource.Factory<Int, Expense>

    // using suspend ensures the code runs off ui thread.
    @Query("DELETE FROM expense WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId:Int)

    // using livedata no need for suspend
    @Query("SELECT id,date,SUM(expense_amount) as expense_amount FROM expense WHERE date BETWEEN :from AND :to GROUP BY date ORDER BY date ASC")
    fun expenseInRange(from: Date, to: Date): LiveData<List<Expense>>

    @Query("SELECT SUM(expense_amount) as total FROM expense where date BETWEEN :startDay AND :endDay")
    fun expenseForCurrentMonth(
        startDay: Date?,
        endDay: Date?
    ): LiveData<Float>

    @Query("UPDATE expense SET expense_type = :expenseType, expense_amount = :expenseAmount, remarks = :remarks, date = :date WHERE id = :id")
    suspend fun updateExpense(id: Int, expenseType: String, expenseAmount: String, remarks: String, date: Date)
}