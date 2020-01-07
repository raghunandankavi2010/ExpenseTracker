package me.raghu.expensetracker.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.db.ExpenseDao
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    suspend fun insert(expense: Expense): Long {
        var long: Long = 0
        try {
            long = expenseDao.insertExpense(expense)
            Log.i("Value", "" + long)
        } catch (error: Throwable) {
            error.printStackTrace()
        }

        return long
    }

    fun getExpensesForLineChart(startDate: Date, endDate: Date): LiveData<List<Expense>> {
        return expenseDao.expenseInRange(startDate, endDate)
    }

    fun getExpensesForCurrentMonth(startDate: Date, endDate: Date): LiveData<Float> {
        return expenseDao.expenseForCurrentMonth(startDate, endDate)

    }

    suspend fun deleteExpense(id: Int) {
        withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(id)
        }
    }


    suspend fun updateExpense(
        id: Int,
        expenseType: String,
        expenseAmt: String,
        remarks: String,
        date: Date
    ) {
        withContext(Dispatchers.IO) {
            expenseDao.updateExpense(id, expenseType, expenseAmt, remarks, date)
        }
    }
}