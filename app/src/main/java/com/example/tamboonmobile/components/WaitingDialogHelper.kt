package com.example.tamboonmobile.components

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.tamboonmobile.R

class WaitingDialogHelper {

    private var dlg: AlertDialog? = null
    private val isShowing: Boolean
        get() = dlg?.isShowing == true


    fun show(context: Context, title: Int) {
        hideDialog()
        val body = LayoutInflater.from(context).inflate(R.layout.waiting_large, null, false)
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(body)
            .setCancelable(true)
        dlg = builder.show()
    }

    fun show(context: Context, title: String) {
        hideDialog()
        val body = LayoutInflater.from(context).inflate(R.layout.waiting_large, null, false)
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(body)
            .setCancelable(true)
        dlg = builder.show()
    }


    fun hideDialog() {
        if (isShowing) {
            dlg?.dismiss()
            dlg = null
        }
    }
}