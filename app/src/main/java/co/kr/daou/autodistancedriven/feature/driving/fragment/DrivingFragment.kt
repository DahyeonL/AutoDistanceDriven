package co.kr.daou.autodistancedriven.feature.driving.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import co.kr.daou.autodistancedriven.databinding.FragmentDrivingBinding
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.feature.driving.service.DrivingService
import co.kr.daou.autodistancedriven.util.ActionName
import co.kr.daou.autodistancedriven.util.FragmentName
import org.koin.core.component.KoinComponent

class DrivingFragment : Fragment(),KoinComponent{
    private lateinit var mainActivity: MainActivity
    val meter : MutableLiveData<String> = MutableLiveData()
    val time : MutableLiveData<String> = MutableLiveData()
    val isWorking : MutableLiveData<Boolean> = MutableLiveData(false)
    private val meterReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            meter.value = intent.getStringExtra("meter")
        }
    }
    private val timeReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            time.value = intent.getStringExtra("time")
//            isWorking.value = true
        }
    }
    private val isWorkingReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            isWorking.value = intent.getBooleanExtra("isWorking",false)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(meterReceiver, IntentFilter("MeterReceiver"))
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(timeReceiver, IntentFilter("TimeReceiver"))
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(isWorkingReceiver, IntentFilter("IsWorkingReceiver"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrivingBinding.inflate(layoutInflater)
        binding.buttonDrivingGoList.setOnClickListener {
            mainActivity.openFragment(FragmentName.Home)
        }
        binding.buttonDrivingStart.setOnClickListener {
            if(checkLocationServicesStatus()) {
                val intent = Intent(requireActivity(), DrivingService::class.java).apply {
                    action = ActionName.START_FOREGROUND
                }
                requireActivity().startService(intent)
            }else{
                showDialogForLocationServiceSetting()
            }
        }
        binding.buttonDrivingFinish.setOnClickListener{
            val intent = Intent(requireActivity(),DrivingService::class.java)
            intent.action = ActionName.STOP_FOREGROUND
            requireActivity().stopService(intent)
            isWorking.value = false
        }
        binding.buttonDrivingGoList.setOnClickListener {
            mainActivity.openFragment(FragmentName.DrivingList)
        }
        meter.observe(viewLifecycleOwner, {
            binding.textviewDrivingMeter.text = it.toString()
        })
        time.observe(viewLifecycleOwner, {
            binding.textviewDrivingTime.text = it.toString()
        })
        isWorking.observe(viewLifecycleOwner,{
            when(it){
                true ->{
                    binding.buttonDrivingStart.visibility = View.GONE
                    binding.buttonDrivingFinish.visibility = View.VISIBLE
                }
                false ->{
                    binding.buttonDrivingFinish.visibility = View.GONE
                    binding.buttonDrivingStart.visibility = View.VISIBLE
                }
            }
        })
        return binding.root
    }

    // GPS 켜져있는지 확인
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    // GPS 활성화 Dialog
    private fun showDialogForLocationServiceSetting() {
        val builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(callGPSSettingIntent)
        }
        builder.setNegativeButton("취소"
        ) { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    companion object{
        const val TAG = "DrivingFragment"
        const val ISWORKING= "IsWorking"
    }
}