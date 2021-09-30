package co.kr.daou.autodistancedriven.global

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import co.kr.daou.autodistancedriven.di.*
import co.kr.daou.autodistancedriven.feature.driving.service.DrivingNotification
import co.kr.daou.autodistancedriven.model.network.LoginAPI
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrivingApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        //기술 스택 #1-2 Koin
        startKoin {
            //AndroidLogger를 Koin logger로 사용
            //로그 찍을 수 있음
            //에러확인 - androidLogger(Level.ERROR)
            androidLogger()
            //컨테이너는 안드로이드 컴포넌트의 Lifecycle에 맞추어 생성과 파괴가 되야함
            androidContext(this@DrivingApplication)
            modules(listOf(databaseModule,repositoryModule, retrofitModule, sharedPreferenceUtilModule,viewModelsModules))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                DrivingNotification.CHANNEL_ID,
                "Driving Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }

    }
}

