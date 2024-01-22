package com.malomnogo.unscramblegame

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

class InputErrorMatcher(private val error: String) :
    BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("InputLayoutError $error")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        return if (error == "") {
            item.error.isNullOrEmpty()
        } else
            item.error.toString() == error
    }
}