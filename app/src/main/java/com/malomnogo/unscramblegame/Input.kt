package com.malomnogo.unscramblegame

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import com.malomnogo.unscramblegame.databinding.InputBinding

class Input : FrameLayout {

    val binding: InputBinding

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        binding = InputBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun showError() = with(binding) {
        inputLayout.error = inputLayout.context.getString(R.string.error_message)
        inputLayout.isErrorEnabled = true
    }

    fun clearError() = with(binding) {
        inputLayout.error = ""
        inputLayout.isErrorEnabled = false
    }

    fun hide() = with(binding) {
        inputEditText.setText("")
        inputLayout.visibility = View.INVISIBLE
    }

    fun show() = with(binding) {
        clearError()
        inputEditText.setText("")
        inputLayout.visibility = View.VISIBLE
    }

    fun text() = binding.inputEditText.text.toString()

    fun doAfterTextChanged(action: (Editable?) -> Unit) {
        binding.inputEditText.doAfterTextChanged(
            action
        )
    }


    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = InputSavedState(it)
            state.save(binding)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as InputSavedState
        super.onRestoreInstanceState(restoredState.superState)
        restoredState.restore(binding)
    }


}

class InputSavedState : View.BaseSavedState {

    private var isError: Boolean = false
    private var visibility = View.VISIBLE

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        isError = parcelIn.readByte() == 1.toByte()
        visibility = parcelIn.readInt()
    }

    fun restore(binding: InputBinding) {
        binding.inputLayout.visibility = visibility
        binding.inputLayout.isErrorEnabled = isError
        if (isError)
            binding.inputLayout.error =
                binding.inputLayout.context.getString(R.string.error_message)
    }

    fun save(binding: InputBinding) {
        visibility = binding.inputLayout.visibility
        isError = binding.inputLayout.isErrorEnabled
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeByte(if (isError) 1 else 0)
        out.writeInt(visibility)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<InputSavedState> {
        override fun createFromParcel(parcel: Parcel): InputSavedState =
            InputSavedState(parcel)

        override fun newArray(size: Int): Array<InputSavedState?> =
            arrayOfNulls(size)
    }
}