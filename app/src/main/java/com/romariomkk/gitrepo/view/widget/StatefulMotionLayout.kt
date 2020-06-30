package com.romariomkk.gitrepo.view.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout

class StatefulMotionLayout @JvmOverloads constructor(context: Context, attrSet: AttributeSet? = null)
    : MotionLayout(context, attrSet)  {

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(progress, super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            progress = state.progress
            super.onRestoreInstanceState(state.superState)
        } else
            super.onRestoreInstanceState(state)
    }

    class SavedState(val progress: Float, source: Parcelable?) : BaseSavedState(source) {
        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(progress)
        }
    }
}