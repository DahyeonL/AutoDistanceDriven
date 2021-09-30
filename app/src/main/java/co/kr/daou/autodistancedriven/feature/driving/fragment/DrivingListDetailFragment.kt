package co.kr.daou.autodistancedriven.feature.driving.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.databinding.FragmentDrivingListDetailBinding
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.feature.driving.DrivingViewModel
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.util.ConvertUtil
import com.google.android.gms.dynamic.IObjectWrapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class DrivingListDetailFragment : Fragment(), KoinComponent, OnMapReadyCallback {
    private lateinit var mainActivity: MainActivity
    private lateinit var mapView: MapView
    private val drivingViewModel : DrivingViewModel by viewModel()
//    private var recordID: Int? = null
    private lateinit var record : LiveData<DrivingRecordDto>
    private lateinit var gpsList : LiveData<List<DrivingRecordGPSDto>>
    private var gpsLatLngList  = mutableListOf<LatLng>()
    private val convertUtil = ConvertUtil()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            record = bundle.getInt("RECORDID").let { drivingViewModel.getRecordById(it) }
            gpsList = drivingViewModel.getRecordGPSsById(bundle.getInt("RECORDID"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrivingListDetailBinding.inflate(layoutInflater)
//        Toast.makeText(requireContext(),"${arguments?.getInt("RECORDID")}",Toast.LENGTH_SHORT).show()
//        record = recordID?.let { drivingViewModel.getRecordById(it) }!!
        mapView = binding.mapviewDetailMap
        mapView.onCreate(savedInstanceState)
        //Databinding으로 옮기기
        record.observe(viewLifecycleOwner,{
            binding.textviewDetailDrivingMeter.text = convertUtil.convertDoubleToString(it.drivingDistance)
            binding.textviewDetailDrivingTime.text = convertUtil.convertTimeToString(it.drivingTime)
            binding.textviewDetailStartPosition.text = convertUtil.convertStartPointToString(it.startPoint)
            binding.textviewDetailFinishPosition.text = convertUtil.convertFinishPointToString(it.finishPoint)
            binding.textviewDetailStartTime.text = convertUtil.convertStartTimeToString(it.startTime)
        })
//        gpsList = recordID?.let { drivingViewModel.getRecordGPSsById(it) }!!
        gpsList.observe(viewLifecycleOwner,{
            getGPSLatLanList(it)
            mapView.getMapAsync(this)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val size = gpsLatLngList.size
        if(size>0){
            val polyline1 = googleMap.addPolyline(
                PolylineOptions()
                    .clickable(false)
                    .color(R.color.point_dark_pink)
                    .addAll(
                        gpsLatLngList
                    ))
            googleMap.addMarker(
                MarkerOptions()
                    .position(gpsLatLngList[0])
                    .title("StartPoint")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finishlocation))
            )
            googleMap.addMarker(
                MarkerOptions()
                    .position(gpsLatLngList[size-1])
                    .title("FinishPoint")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsLatLngList[0], 15f))
        }
    }

    private fun getGPSLatLanList(drivingRecordGPSList :List<DrivingRecordGPSDto>){
        gpsLatLngList = mutableListOf()
        for (gps in drivingRecordGPSList){
            gpsLatLngList.add(LatLng(gps.latitude,gps.longitude))
        }
    }
}