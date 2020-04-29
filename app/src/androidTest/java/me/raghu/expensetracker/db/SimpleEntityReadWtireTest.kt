package me.raghu.expensetracker.db;

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import me.raghu.expensetracker.util.TestUtil
import me.raghu.expensetracker.util.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    private lateinit var expenseDao: ExpenseDao
    private lateinit var db: ExpenseDb

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, ExpenseDb::class.java).build()
        expenseDao = db.expenseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @InternalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking{
        val calendar = Calendar.getInstance();
        calendar.set(29,14,2020)
        val date = calendar.time

        val expense: Expense = TestUtil.createExpense(1,"23","Cash","xyx",date)

        expenseDao.insertExpense(expense)
        val listOfExpense = expenseDao.getExpenseById(1).getOrAwaitValue()

        assertThat(listOfExpense, CoreMatchers.notNullValue())
        assertThat(listOfExpense.id, CoreMatchers.`is`(1))
        assertThat(listOfExpense.expenseAmt, CoreMatchers.`is`("23"))
        assertThat(listOfExpense.expenseType, CoreMatchers.`is`("Cash"))
        assertThat(listOfExpense.remarks, CoreMatchers.`is`("xyx"))

    }
}