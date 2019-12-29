package me.raghu.expensetracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.repository.DatabaseRepository
import javax.inject.Inject

class ExpenseViewModel
@Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {


    val expenses: LiveData<List<Expense>> = liveData {
        val data = databaseRepository.getExpenses()
        emit(data)
    }

}
