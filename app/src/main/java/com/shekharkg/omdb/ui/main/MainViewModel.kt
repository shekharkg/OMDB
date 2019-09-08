package com.shekharkg.omdb.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekharkg.omdb.api.ApiClient
import com.shekharkg.omdb.dao.Movie
import com.shekharkg.omdb.dao.SearchResult
import com.shekharkg.omdb.utils.KEYS.OMDB_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by shekhar on 2019-09-07.
 */
class MainViewModel : ViewModel(), Callback<SearchResult> {

  private var pageNumber = 1

  var isApiCallInProgress: MutableLiveData<Boolean> = MutableLiveData()
  var noResultsFound: MutableLiveData<Boolean> = MutableLiveData()
  var isError: MutableLiveData<String> = MutableLiveData()

  var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()

  fun noInternetConnection() {
    isApiCallInProgress.value = false
    noResultsFound.value = false
    isError.value = "Device is OFFLINE!"
  }

  fun callApi(searchedKey: String) {
    isApiCallInProgress.value = true
    noResultsFound.value = false
    isError.value = null
    ApiClient.getApiInterface().searchMovies(OMDB_API_KEY, searchedKey, pageNumber).enqueue(this)
  }

  override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
    isApiCallInProgress.value = false

    if (response.code() == 200 && response.body() != null) {
      val movies = response.body()
      if (movies != null && !movies.Search.isNullOrEmpty()) {
        moviesList.value = movies.Search
      } else noResultsFound.value = true
    } else
      isError.value = "Something went wrong"
  }

  override fun onFailure(call: Call<SearchResult>, t: Throwable) {
    isApiCallInProgress.value = false
    isError.value = t.toString()
  }

}
