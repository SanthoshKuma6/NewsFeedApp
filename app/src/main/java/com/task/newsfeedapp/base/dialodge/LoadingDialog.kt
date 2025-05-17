package com.task.newsfeedapp.base.dialodge

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import com.task.newsfeedapp.R

class LoadingDialog(private val context: Context) {

    private var dialog: CustomDialog? = null

    fun stop() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    fun start(title: String = "") {
        if (dialog == null) {
            dialog = CustomDialog(context)
        }
        dialog?.apply {
            setCancelable(false)
            show()
        }
    }

    companion object {
        const val TAG = "LoadingDialog"
        const val MESSAGE_KEY = "message_key"
    }

    @Suppress("DEPRECATION")
    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}
