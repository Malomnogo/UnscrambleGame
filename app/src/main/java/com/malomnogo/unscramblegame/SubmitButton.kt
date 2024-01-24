package com.malomnogo.unscramblegame

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

class SubmitButton : androidx.appcompat.widget.AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = SubmitButtonSavedState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as SubmitButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        restoredState.restore(this)
    }
}

class SubmitButtonSavedState : View.BaseSavedState {

    private var isEnabled = true
    private var visibility = View.VISIBLE

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        isEnabled = parcelIn.readByte() == 1.toByte()
        visibility = parcelIn.readInt()
    }

    fun restore(button: SubmitButton) {
        button.visibility = visibility
        button.isEnabled = isEnabled
    }

    fun save(button: SubmitButton) {
        visibility = button.visibility
        isEnabled = button.isEnabled
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeByte(if (isEnabled) 1 else 0)
        out.writeInt(visibility)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SubmitButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): SubmitButtonSavedState =
            SubmitButtonSavedState(parcel)

        override fun newArray(size: Int): Array<SubmitButtonSavedState?> =
            arrayOfNulls(size)
    }
}