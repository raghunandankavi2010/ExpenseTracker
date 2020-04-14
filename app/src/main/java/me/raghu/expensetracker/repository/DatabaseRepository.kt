package me.raghu.expensetracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.db.ExpenseDao
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  A repo class used for many getting expense from db.
 */

@Singleton
class DatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    // using suspend is enough no need for thread
    suspend fun insert(expense: Expense): Long {
        var long: Long = 0
        try {
            long = expenseDao.insertExpense(expense)
            Timber.i("Inserted Successfully! $long")
        } catch (error: Throwable) {
            error.printStackTrace()
        }

        return long
    }

    fun getExpensesForLineChart(startDate: Date, endDate: Date): LiveData<List<Expense>> {
        return expenseDao.expenseInRange(startDate, endDate).asLiveData()
    }

    fun getExpensesForCurrentMonth(startDate: Date, endDate: Date): LiveData<Float> {
        return expenseDao.expenseForCurrentMonth(startDate, endDate).asLiveData()

    }

    suspend fun deleteExpense(id: Int) {
        expenseDao.deleteExpense(id)
    }

    suspend fun updateExpense(
        id: Int,
        expenseType: String,
        expenseAmt: String,
        remarks: String,
        date: Date
    ) {
        expenseDao.updateExpense(id, expenseType, expenseAmt, remarks, date)
    }
}