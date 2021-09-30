package co.kr.daou.autodistancedriven.model.network

import co.kr.daou.autodistancedriven.util.SharedPreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class ReceivedCookiesInterceptor : Interceptor, KoinComponent {
    private val sharedPreferenceUtil : SharedPreferenceUtil by inject()
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())

        val interceptorCookies = originalResponse.headers["Set-Cookie"]
        //null+조건 체크 라는 조건이 있을 때 사용하기 좋음
        interceptorCookies?.takeIf{ it.isNotEmpty() }?.let{
            val cookie = it
            sharedPreferenceUtil.setHeader(cookie)
        }
        return originalResponse
    }
}