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

 /*   suspend fun getExpenses(): LiveData<List<Expense>> {
        return withContext(Dispatchers.IO) {
            val data = expenseDao.fetchExpenses()
            Log.i("Expenses List Size", "" + data.value?.size)
            data
        }
    }
*/
    suspend fun getExpensesForCurrentMonth(startDate: Date,endDate: Date): Float {
        return withContext(Dispatchers.IO) {
            val data = expenseDao.expenseForCurrentMonth(startDate,endDate)
            data
        }
    }

    suspend fun deleteExpense(id:Int) {
         withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(id)
        }
    }
}