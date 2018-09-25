package br.com.stone.emeraldcomponentsandroid.activities

import android.Manifest
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import br.com.stone.emeraldcomponentsandroid.R
import com.jraska.falcon.FalconSpoonRule
import com.squareup.spoon.SpoonRule
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by diegolucasb on 14/09/18.
 * Copyright (c) Stone Co. All rights reserved.
 * lucas.amaral@stone.com.br
 */
@RunWith(AndroidJUnit4::class)
class SpinnerActivityTest {

    @get:Rule
    val permission: GrantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    @get:Rule
    val spoon = FalconSpoonRule()

    @get:Rule
    val activityRule = ActivityTestRule(SpinnerActivity::class.java)

    @Test
    fun shouldAutoCompleteValidateTypedValue() {
        val autoCompleteView = onView(allOf(
                ViewMatchers.isDescendantOfA(withId(R.id.emeraldAutoComplete)),
                ViewMatchers.isAssignableFrom(EditText::class.java)))

        spoon.screenshot(activityRule.activity, "Starting")

        val query = "100 - Aaaaaaaaa"
        autoCompleteView.perform(click())
        autoCompleteView.perform(replaceText("100"), closeSoftKeyboard())

        spoon.screenshot(activityRule.activity, "Type-100-on-autoComplete")

        onView(withText(query)).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
                .perform(click())

        spoon.screenshot(activityRule.activity, "Clicked-on-suggestion-window")

        autoCompleteView.check(matches(withText(query)))
    }

    @Test
    fun shouldSpinnerMatchTestWhenUserClickInOneOption() {
        onView((withId(R.id.emeraldSpinner)))
                .perform(click())

        spoon.screenshot(activityRule.activity, "Clicked-on-spinner")

        val expectedOption = "Option 2"
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(expectedOption))).perform(click())
        onView((withId(R.id.emeraldSpinner))).check(matches(withSpinnerText(expectedOption)))

        spoon.screenshot(activityRule.activity, "Clicked-on-second-option")
    }
}