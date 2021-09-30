package co.kr.daou.autodistancedriven.feature.driving.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.databinding.ItemDrivingListBinding
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import co.kr.daou.autodistancedriven.util.FragmentName
import java.util.*

//코드 개발 이슈 #9
//원인 => 고민 => 결과
class DrivingListRecyclerviewAdapter (inputDateList: List<Date>, inputDrivingList : List<DrivingRecordDto>) : RecyclerView.Adapter<DrivingListRecyclerviewAdapter.DrivingRecyclerviewHolder>(){
    private val dateList : List<Date> = inputDateList

    //데이터를 어떻게 처리 해줄 것인가...?
    private val drivingList : List<DrivingRecordDto> =  inputDrivingList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrivingRecyclerviewHolder {
        return DrivingRecyclerviewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_driving_list,parent,false))
    }

    override fun onBindViewHolder(holder: DrivingRecyclerviewHolder, position: Int) {
        val recordListByDate = drivingList
//        holder.binding.textviewItemDay.text = dateList[position].toString()
        holder.binding.recyclerviewListDrivingListDetail.apply {
//            adapter = DrivingListDetailRecyclerviewAdapter(recordListByDate)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    class DrivingRecyclerviewHolder(val binding : ItemDrivingListBinding) : RecyclerView.ViewHolder(binding.root)
}