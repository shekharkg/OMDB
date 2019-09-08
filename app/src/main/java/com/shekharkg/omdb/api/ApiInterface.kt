package com.shekharkg.omdb.api

import com.shekharkg.omdb.dao.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by shekhar on 2019-09-07.
 */
interface ApiInterface {

  @GET("/")
  fun searchMovies(
    @Query("apikey") apiKey: String,
    @Query("s") searchedKey: String,
    @Query("page") pageNumber: Int
  ): Call<SearchResult>

}