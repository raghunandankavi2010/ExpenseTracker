package me.raghu.expensetracker.ui


import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import me.raghu.expensetracker.R
import me.raghu.expensetracker.ui.ReyclerviewCustomMatchers.Companion.withItemCount
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    lateinit var symbol: String

    @Before
    fun setLocale() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context

        val locale: Locale = Locale.getDefault()
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
         configuration.setLocale(locale)


        val mainActivity: MainActivity = mActivityTestRule.activity
        mainActivity.createConfigurationContext(configuration)
        val intent = mainActivity.intent
        mainActivity.finish()
        mainActivity.startActivity(intent)
        val currency: Currency = Currency.getInstance(Locale.getDefault())
        symbol= currency.symbol
    }

    @Test
    fun mainActivityTest() {
        val textView = onView(
            allOf(
                withId(R.id.total_amt_spent), withText("Total amount spent this month"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_account_expenditure_details),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val monthlyIncome = onView(
            allOf(
                withId(R.id.m_income), withText("Monthly Income"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_account_expenditure_details),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        monthlyIncome.check(matches(withText("Monthly Income")))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @Test
    fun checkRecyclerView(){
        onView(withId(R.id.expenseList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.expenseList))
            .check(matches(withItemCount(0)))
    }

    @Test
    fun textViewContains(){
        onView(withId(R.id.monthly_income)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.monthly_income)).check(matches(withText("0 $symbol")))

        onView(withId(R.id.total_amt_spent_value)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.total_amt_spent_value)).check(matches(withText("0.00 $symbol")))

        onView(withId(R.id.exceeded)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.alert)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testFab(){
        onView(withId(R.id.add)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.add)).perform(click())
    }

}
