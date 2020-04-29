package me.raghu.expensetracker.util

import androidx.paging.PagedList
import me.raghu.expensetracker.db.Expense
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*

object TestUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }


    fun createExpenses(id: Int,expenseAmt: String,expenseType: String,remarks: String,date: Date): List<Expense> {
        return (0 until 20).map {
            createExpense(id+it,expenseAmt,expenseType,remarks,date)
        }
    }

    fun createExpense(id: Int,expenseAmt: String,expenseType: String,remarks: String,date: Date) =
        Expense(id = id, expenseAmt = expenseAmt, expenseType = expenseType, remarks = remarks, date = Date())
}

