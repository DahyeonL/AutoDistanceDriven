package co.kr.daou.autodistancedriven.feature.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.feature.login.fragment.LoginFragment
import co.kr.daou.autodistancedriven.util.FragmentName

class LoginActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, " 위치 정보 권한이 허용 되었습니다. ")
            openFragment(FragmentName.Login)
        } else {
            Log.d(TAG, " 위치 정보 권한이 거부 되었습니다. ")
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setTitle("위치 권한 허용")
                .setMessage("위치 권한 허용이 필요합니다. 확인을 누르면 설정화면으로 이동합니다.")
                .setPositiveButton("확인") { _, _ ->
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
                    finish()
                }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        when (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PackageManager.PERMISSION_GRANTED-> {
                Log.d(TAG, "onCreate: 위치 정보 권한 허용")
                openFragment(FragmentName.Login)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    fun openFragment(fragmentName : FragmentName){
        val transaction = supportFragmentManager.beginTransaction()
        when(fragmentName){
            FragmentName.Home -> {
                val intent = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            FragmentName.Login -> {
                transaction.replace(R.id.frame_layout_login_layout, LoginFragment())
                    .addToBackStack(null)
            }
        }
        transaction.commit()
    }
    companion object{
        const val TAG ="LoginActivity"
    }
}