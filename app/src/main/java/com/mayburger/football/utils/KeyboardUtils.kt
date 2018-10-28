package com.mayburger.football.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.support.v4.act

/**
 * Created by Mayburger on 10/28/18.
 */
open class KeyboardUtils {
    open fun showInputMethod(act: Activity) {
        val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    open fun hideInputMethod(act: Activity) {
        val view = act.currentFocus
        if (view != null) {
            val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}