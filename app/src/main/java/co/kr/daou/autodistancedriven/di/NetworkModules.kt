package co.kr.daou.autodistancedriven.di

import co.kr.daou.autodistancedriven.global.DrivingApplication
import co.kr.daou.autodistancedriven.model.network.AddCookiesInterceptor
import co.kr.daou.autodistancedriven.model.network.LoginAPI
import co.kr.daou.autodistancedriven.model.network.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofitModule = module {
    single {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://nstaging.daouoffice.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(LoginAPI::class.java)
    }

}