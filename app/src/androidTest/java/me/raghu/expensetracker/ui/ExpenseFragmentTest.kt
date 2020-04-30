package me.raghu.expensetracker.ui

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import me.raghu.expensetracker.R
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.ui.databinding.FragmentBindingAdapters
import me.raghu.expensetracker.ui.expense.ExpenseViewModel
import me.raghu.expensetracker.util.DataBindingIdlingResourceRule
import me.raghu.expensetracker.util.TestUtil
import me.raghu.expensetracker.util.ViewModelUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*


@RunWith(AndroidJUnit4::class)
class ExpenseFragmentTest {

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private lateinit var viewModel: ExpenseViewModel

    val expenseExceeded: MutableLiveData<Float> = MutableLiveData()
    var expense: MutableLiveData<PagedList<Expense>> = MutableLiveData()


    @Before
    fun init() {
        viewModel = mock(ExpenseViewModel::class.java)
        mockBindingAdapter = mock(FragmentBindingAdapters::class.java)

        Mockito.`when`(viewModel.expense).thenReturn(expense)
        Mockito.`when`(viewModel.expenseExceeded).thenReturn(expenseExceeded)
    }

    @Test
    fun testNavigation() {

        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        TestExpenseFragment.testViewModel = ViewModelUtil.createFor(viewModel)
        TestExpenseFragment.testDataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }

        val scenario = launchFragmentInContainer(
            themeResId = R.style.AppTheme
        ) {
            TestExpenseFragment().also { fragment ->

                // In addition to returning a new instance of our Fragment,
                // get a callback whenever the fragment’s view is created
                // or destroyed so that we can set the NavController
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        dataBindingIdlingResourceRule.monitorFragment(scenario)


        // Verify that performing a click changes the NavController’s state
        expenseExceeded.postValue(0f)
        val calendar = Calendar.getInstance();
        calendar.set(29,14,2020)
        val date = calendar.time
        expense.postValue(TestUtil.mockPagedList(TestUtil.createExpenses(1,"23","Cash","xyx",date)))
        onView(ViewMatchers.withId(R.id.add)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.expenseInput)
    }
}