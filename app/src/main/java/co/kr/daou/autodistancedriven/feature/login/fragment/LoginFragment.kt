package co.kr.daou.autodistancedriven.feature.login.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.kr.daou.autodistancedriven.databinding.FragmentLoginBinding
import co.kr.daou.autodistancedriven.feature.login.LoginActivity
import co.kr.daou.autodistancedriven.feature.login.LoginViewModel
import co.kr.daou.autodistancedriven.util.FragmentName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.bind

class LoginFragment : Fragment(), KoinComponent {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.isLoginCheck()
        loginViewModel.isLogin.observe(this, {
            if(it) {loginActivity.openFragment(FragmentName.Home)}
        })
    }

    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.buttonLoginLogin.setOnClickListener {
//            loginViewModel.login(
//                binding.edittextLoginId.text.toString(),
//                binding.edittextLoginPw.text.toString(),
//                Settings.Secure.getString(requireContext().contentResolver,Settings.Secure.ANDROID_ID)
//            )
            loginViewModel.isLogin.value = true
        }
        return binding.root
    }

}