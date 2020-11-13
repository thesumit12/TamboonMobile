package com.example.tamboonmobile.di

import android.content.Context
import androidx.databinding.library.BuildConfig
import com.example.tamboonmobile.components.Constant
import com.example.tamboonmobile.network.IApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CACHE_SIZE = 10 * 1024 * 1024
private const val BASE_URL = "https://virtserver.swaggerhub.com/chakritw/tamboon-api/1.0.0/"

val networkModule = module {
    single { createOkHttpClient(androidContext()) }
    single { createGsonConverter() }
    single { createRetrofitBuilder(get(), get()) }

    single { createApiService(get(), BASE_URL) }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
    clientBuilder.readTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)

    // Deciding cache size and where to keep it
    val cache = Cache(context.cacheDir, CACHE_SIZE.toLong())
    clientBuilder.cache(cache)
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    return clientBuilder.build()
}

fun createGsonConverter(): Gson = GsonBuilder().setLenient().create()

fun createRetrofitBuilder(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder =
    Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

fun createApiService(builder: Retrofit.Builder, baseUrl: String): IApiService =
    builder.baseUrl(baseUrl).build().create(IApiService::class.java)