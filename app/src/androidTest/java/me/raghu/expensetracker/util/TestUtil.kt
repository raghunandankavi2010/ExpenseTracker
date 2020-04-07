package me.raghu.expensetracker.util

import androidx.paging.PagedList
import me.raghu.expensetracker.db.Expense
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*

object TestUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }


    fun createExpenses(): List<Expense> {
        return (0 until 20).map {
            createExpense()
        }
    }

    fun createExpense() =
        Expense(id = 1, expenseAmt = "23", expenseType = "Cash", remarks = "xyx", date = Date())
}

