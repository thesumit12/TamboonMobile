package com.example.tamboonmobile.components

import android.content.Context
import android.content.DialogInterface
import com.example.tamboonmobile.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertDialogUtil {

    fun showErrorDialog(context: Context, title: String, msg: String, listener: DialogInterface.OnClickListener) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(title)
            .setMessage(msg)
            .setPositiveButton(R.string.ok) {dialog, _ ->
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
            }
        val dialog = builder.create()
        dialog.show()
    }
}