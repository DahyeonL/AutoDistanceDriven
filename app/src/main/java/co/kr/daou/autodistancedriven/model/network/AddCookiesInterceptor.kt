package co.kr.daou.autodistancedriven.model.network

import co.kr.daou.autodistancedriven.util.SharedPreferenceUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class AddCookiesInterceptor : Interceptor , KoinComponent {
    private val sharedPreferenceUtil : SharedPreferenceUtil by inject()
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        // cookie 가져오기
        val getHeader = sharedPreferenceUtil.getHeader()
        if(getHeader!=null){
                builder.addHeader("cookie", getHeader)
        }
        return chain.proceed(builder.build())
    }
}