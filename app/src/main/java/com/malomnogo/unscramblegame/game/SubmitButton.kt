package com.malomnogo.unscramblegame.game

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.google.android.material.button.MaterialButton

class SubmitButton : MaterialButton {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState().let {
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

    private var isEnable: Boolean = true
    private var visibility = View.VISIBLE

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        isEnable = parcelIn.readByte() == 1.toByte()
        visibility = parcelIn.readInt()
    }

    fun restore(button: SubmitButton) {
        button.visibility = visibility
        button.isEnabled = isEnable
    }

    fun save(button: SubmitButton) {
        visibility = button.visibility
        isEnable = button.isEnabled
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeByte(if (isEnable) 1 else 0)
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