package co.kr.daou.autodistancedriven.di

import co.kr.daou.autodistancedriven.util.SharedPreferenceUtil
import org.koin.dsl.module

val sharedPreferenceUtilModule = module {
    single {
        SharedPreferenceUtil(get())
    }
}