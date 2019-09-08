package com.shekharkg.omdb.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * Created by shekhar on 2019-09-07.
 */

object ApiClient {

  private const val BASE_URL = "http://www.omdbapi.com"
  private var retrofit: Retrofit? = null
  private var TIMEOUT: Long = 5

  private val gson = GsonBuilder()
    .setLenient()
    .create()


  private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)

  private fun getClient(): Retrofit {
    if (retrofit == null) {
      retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build()
    }
    return retrofit!!
  }

  fun getApiInterface(): ApiInterface = getClient().create(ApiInterface::class.java)


}