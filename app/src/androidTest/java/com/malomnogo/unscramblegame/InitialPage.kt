package com.malomnogo.unscramblegame

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class InitialPage(private val counter: String,private val score: String,private val shuffledWord: String) {
    fun checkVisible(input:String = "") {
        onView(
            allOf(
                withId(R.id.submitButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )).check(matches(
            not(isEnabled())
        ))
        onView(
            allOf(
                withId(R.id.counterTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText(counter)))
        onView(
            allOf(
                withId(R.id.scoreTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText(score)))
        onView(
            allOf(
                withId(R.id.shuffledWordTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText(shuffledWord)))
        onView(
            allOf(
                withId(R.id.inputLayout),
                isAssignableFrom(TextInputLayout::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(InputErrorMatcher("")))
        onView(
            allOf(
                withId(R.id.inputEditText),
                isAssignableFrom(TextInputEditText::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText(input)))
        onView(
            allOf(
                withId(R.id.skipButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText("skip")))
    }

    fun clickSubmit() {
        onView(
            allOf(
                withId(R.id.submitButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(click())
    }

    fun typeText(word: Char) {
        typeText(word.toString())

    }
    fun typeText(word: String) {
        onView(
            allOf(
                withId(R.id.inputEditText),
                isAssignableFrom(TextInputEditText::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(ViewActions.typeText(word))
    }

    fun replaceText(text: String) {
        onView(
            allOf(
                withId(R.id.inputEditText),
                isAssignableFrom(TextInputEditText::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(ViewActions.replaceText(text))
    }



    fun clickSkip() {
        onView(
            allOf(
                withId(R.id.skipButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(click())
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.counterTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(not(isDisplayed())))
        onView(
            allOf(
                withId(R.id.submitButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )).check(matches(not(isDisplayed())))
        onView(
            allOf(
                withId(R.id.inputLayout),
                isAssignableFrom(TextInputLayout::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(not(isDisplayed())))
    }


}
