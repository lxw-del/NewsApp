package com.permissionx.goodnews.utils

import android.widget.Toast
import com.permissionx.goodnews.App

object ToastUtils {
    fun String.showToast(toastTime: Int = Toast.LENGTH_SHORT) = Toast.makeText(App.context,this,toastTime).show()

    fun Int.showToast(toastTime: Int = Toast.LENGTH_SHORT) = Toast.makeText(App.context,this,toastTime).show()
}