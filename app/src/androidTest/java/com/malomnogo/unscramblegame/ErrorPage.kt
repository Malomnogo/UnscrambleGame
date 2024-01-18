package com.malomnogo.unscramblegame

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf

class ErrorPage {
    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.inputLayout),
                isAssignableFrom(TextInputLayout::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(InputErrorMatcher("wrong guess")))
    }

}
