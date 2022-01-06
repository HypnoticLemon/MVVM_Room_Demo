package com.vikrant.simplemvvmroomdemo.Utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikrant.simplemvvmroomdemo.R


class CustomProgressBar {
    companion object{
        private var dialog: Dialog? = null
    }

    fun show(
        context: Context?,
        cancelable: Boolean
    ): Dialog? {
        return show(context!!, cancelable, null)
    }

    @SuppressLint("InflateParams")
    fun show(
        context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ): Dialog? {
        val layoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.progress_bar_layout,null)

        dialog = Dialog(context, R.style.AppTheme)
        dialog!!.setContentView(view)
        dialog!!.setCancelable(cancelable)
        dialog!!.setOnCancelListener(cancelListener)
        dialog!!.show()


        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, height)


        return dialog
    }

    fun getDialog(): Dialog? {
        return dialog
    }
}