package com.gielow.cleanbaseapp.di

import android.app.Application
import com.gielow.cleanbaseapp.BuildConfig
import com.gielow.cleanbaseapp.MainApp
import com.gielow.cleanbaseapp.R
import com.gielow.cleanbaseapp.data.api.CryptoApi
import com.gielow.cleanbaseapp.domain.repository.CryptoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication() = MainApp()

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(application: Application) =  application.getString(R.string.CRYPTO_BASE_URL)

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(api: CryptoApi) = CryptoRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named("BASE_URL") baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi = retrofit.create(CryptoApi::class.java)

}