package co.kr.daou.autodistancedriven.di

import co.kr.daou.autodistancedriven.feature.driving.DrivingViewModel
import co.kr.daou.autodistancedriven.feature.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {
    viewModel{
        LoginViewModel()
    }
    viewModel {
        DrivingViewModel()
    }
}
