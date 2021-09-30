package co.kr.daou.autodistancedriven.feature.driving.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.model.db.repository.DrivingRecordRepository
import co.kr.daou.autodistancedriven.util.ConvertUtil
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import kotlin.concurrent.timer

//코드 개발 이슈 #7
class DrivingService : Service(), KoinComponent{
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback : LocationCallback
    private lateinit var timer: Timer
    private val repository: DrivingRecordRepository by inject()
    private var startTime = Date()
    private var startAddress : String = ""
    private var drivingDistance : Double = 0.0
    private lateinit var drivingTime : String
    private lateinit var recordIDLivedata : LiveData<Int>
    private var recordID = 0
    private val convertUtil = ConvertUtil()
    private var isCheck = false
    //코드 개발 이슈 #1
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        recordIDLivedata = repository.findDrivingRecordIdByLast.asLiveData()
        //코드 개발 이슈 #6
        recordIDLivedata.observeForever {
            recordID = it ?: 1
        }
        startForegroundGPSService()
        startForegroundTimeService()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForegroundService()
    }

    @SuppressLint("MissingPermission")
    private fun startForegroundGPSService() {
        //코드 개발 이슈 #10
        //제로백
        //주유비 M 1원 300M 약 42원
        upDateIsWorking(true)
        insertRecord()
        drivingDistance = 0.0
        var startLocation = Location("StartLocation")
        var beforeLocation = Location("BeforeLocation")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if(location !=null) {
                startTime = Date()
                startAddress =  changePositionToAddress(location)
                startLocation = location
                beforeLocation = startLocation
            }
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                upDateIsWorking(true)
                for (location in locationResult.locations) {
                    if (location != null) {
                        if(isCheck){
                            insertPoint(location)
                            drivingDistance += beforeLocation.distanceTo(location)
                            beforeLocation = location
                            upDateMeterUI(drivingDistance)
                        }
                        if(startLocation.distanceTo(location)>300){
                            beforeLocation = location
                        }
                        else {
                            beforeLocation = startLocation
                            insertPoint(location)
                            upDateMeterUI(drivingDistance)
                        }
                        isCheck = true
                    }
                }
            }
        }
        val locationRequest = LocationRequest.create().apply {
            priority= LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = INTERVAL
        }
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        )
        val notification = DrivingNotification.createNotification(this)
        startForeground (NOTIFICATION_ID, notification)
    }
    private fun startForegroundTimeService(){
        var count : Long =0
        timer = timer(period = 1000){
            count++
            upDateTimeUI(count)
        }
    }
    @SuppressLint("MissingPermission")
    private fun stopForegroundService() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        timer.cancel()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if(location !=null) {
                updateRecord(location)
            }
        }
        upDateIsWorking(false)
        stopForeground(true)
        stopSelf()
    }

    //코드 개발 이슈 #5
    private fun upDateIsWorking(isWorking : Boolean){
        val intent = Intent("IsWorkingReceiver")
        intent.putExtra("isWorking", isWorking)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
    private fun upDateMeterUI(meter: Double) {
        val intent = Intent("MeterReceiver")
        intent.putExtra("meter", convertUtil.convertDoubleToString(meter))
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
    private fun upDateTimeUI(time : Long){
        drivingTime = convertUtil.convertTimeToString(time)
        val intent = Intent("TimeReceiver")
        intent.putExtra("time",drivingTime)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun changePositionToAddress(location: Location) : String{
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude,location.longitude,7)
        return addresses[0].getAddressLine(0).toString()
    }

    //기술 스택 #3-1 Coroutine
    private fun insertRecord(){
        GlobalScope.launch {
            repository.insertRecords(DrivingRecordDto())
        }
    }
    private fun insertPoint(location: Location){
        GlobalScope.launch {
            repository.insertRecordGPSs(DrivingRecordGPSDto(null, recordID,location.latitude,location.longitude))
        }
    }
    private fun updateRecord(location : Location){
        GlobalScope.launch {
            repository.updateRecords(
                DrivingRecordDto(recordID,startTime,Date(),drivingTime,drivingDistance,startAddress,changePositionToAddress(location))
            )
            upDateMeterUI(0.0)
            upDateTimeUI(0)
        }

    }
    companion object {
        const val NOTIFICATION_ID = 10
        const val INTERVAL : Long = 3*1000
    }
}

