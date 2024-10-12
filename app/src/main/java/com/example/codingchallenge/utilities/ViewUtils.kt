package com.example.codingchallenge.utilities

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

object ViewUtils {

    fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, msg, duration).show()
    }

    fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        activity?.showToast(msg, duration)
    }

}