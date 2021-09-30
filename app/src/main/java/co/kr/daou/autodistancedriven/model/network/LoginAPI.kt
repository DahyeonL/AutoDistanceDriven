package co.kr.daou.autodistancedriven.model.network
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//기술 스택 #2-1 Retrofit,Okhttp
interface LoginAPI {
    @POST("/api/login")
    fun login(@Body user: User) : Call<ResponseBody>

    @GET("/api/alive")
    fun alive() : Call<ResponseBody>
}