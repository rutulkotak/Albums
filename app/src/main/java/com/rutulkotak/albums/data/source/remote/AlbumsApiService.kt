package com.rutulkotak.albums.data.source.remote

import com.rutulkotak.albums.data.Album
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AlbumsApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}

object AlbumsApi {
    val retrofitService : AlbumsApiService by lazy { retrofit.create(AlbumsApiService::class.java) }
}