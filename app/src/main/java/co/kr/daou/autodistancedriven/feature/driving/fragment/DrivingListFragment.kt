package co.kr.daou.autodistancedriven.feature.driving.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.kr.daou.autodistancedriven.databinding.FragmentDrivingListBinding
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.feature.driving.DrivingViewModel
import co.kr.daou.autodistancedriven.feature.driving.adapter.DrivingListDetailRecyclerviewAdapter
import co.kr.daou.autodistancedriven.feature.driving.adapter.DrivingListRecyclerviewAdapter
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import java.util.*

class DrivingListFragment : Fragment(),KoinComponent{
    private lateinit var mainActivity: MainActivity
    private val drivingViewModel: DrivingViewModel by viewModel()
    private lateinit var dateList : List<Date>
    private lateinit var drivingList : List<DrivingRecordDto>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        for (i : Int in 20..31){
//            dateList.add("08/$i")
//            drivingList.add(DrivingRecordDto(i, Date(),Date(),"",i*30.5,"",""))
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrivingListBinding.inflate(layoutInflater)
//        drivingViewModel.drivingDays.observe(viewLifecycleOwner, { list ->
//            dateList = list
//            drivingViewModel.allRecords.observe(viewLifecycleOwner,{
//                drivingList = it
//                binding.recyclerviewListDrivingList.apply {
////                    adapter = DrivingListRecyclerviewAdapter(dateList,drivingList)
//                    adapter = DrivingListDetailRecyclerviewAdapter(drivingList)
//                    layoutManager = LinearLayoutManager(context)
//                }
//            })
//        })
        drivingViewModel.allRecords.observe(viewLifecycleOwner,{
            drivingList = it
            binding.recyclerviewListDrivingList.apply {
                adapter = DrivingListDetailRecyclerviewAdapter(drivingList,drivingViewModel)
                layoutManager = LinearLayoutManager(context)
            }
        })

//        binding.textviewListAllDistance.text = "10002.5M"


        return binding.root
    }
}