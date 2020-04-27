package me.raghu.expensetracker.db;

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import me.raghu.expensetracker.util.TestUtil
import me.raghu.expensetracker.util.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var expenseDao: ExpenseDao
    private lateinit var db: ExpenseDb


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
        val expense: Expense = TestUtil.createExpense()
        expenseDao.insertExpense(expense)
        val listOfExpense = expenseDao.expenseInRange(Date(), Date()).getOrAwaitValue()
        MatcherAssert.assertThat(listOfExpense, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(listOfExpense[0].expenseAmt, CoreMatchers.`is`("23"))
        MatcherAssert.assertThat(listOfExpense[0].expenseType, CoreMatchers.`is`("Cash"))
        MatcherAssert.assertThat(listOfExpense[0].remarks, CoreMatchers.`is`("xyx"))

    }
}