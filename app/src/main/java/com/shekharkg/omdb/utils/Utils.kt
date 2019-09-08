package com.shekharkg.omdb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by shekhar on 2019-09-07.
 */

object Utils {

  fun isNetworkConnected(context: Context?): Boolean {
    val connectivityManager =
      context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }


  fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
      view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}