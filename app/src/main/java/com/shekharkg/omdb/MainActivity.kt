package com.shekharkg.omdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shekharkg.omdb.ui.main.MainFragment

/**
 * Created by shekhar on 2019-09-07.
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
  }

}
