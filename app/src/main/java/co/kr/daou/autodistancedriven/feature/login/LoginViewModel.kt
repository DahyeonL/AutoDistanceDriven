package co.kr.daou.autodistancedriven.feature.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.daou.autodistancedriven.model.network.LoginAPI
import co.kr.daou.autodistancedriven.model.network.User
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(), KoinComponent{
    private val api : LoginAPI by inject()
    val isLogin : MutableLiveData<Boolean> = MutableLiveData(false)

    //기술 스택 #2-2 Retrofit,Okhttp
    fun isLoginCheck() {
        api.alive().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.d("alvie", "onResponse: 성공")
                    isLogin.value = true
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("alvie", "onResponse: 실패")
            }
        })
    }
    fun login(id : String, password : String, deviceId : String){
        val user = User(id,password,"ko",deviceId)
        api.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.d("login", "onResponse: 성공")
                    isLogin.value = true
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("login", "onResponse: 실패")
            }
        })
    }
}