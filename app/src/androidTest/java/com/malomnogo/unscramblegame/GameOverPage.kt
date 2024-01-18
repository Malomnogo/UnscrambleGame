package com.malomnogo.unscramblegame

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

class GameOverPage(private val score: String) {
    fun checkVisible() {
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
                withId(R.id.skipButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(withText("restart")))
    }

    fun clickRestart() {
        onView(
            allOf(
                withId(R.id.skipButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(click())
    }

}
