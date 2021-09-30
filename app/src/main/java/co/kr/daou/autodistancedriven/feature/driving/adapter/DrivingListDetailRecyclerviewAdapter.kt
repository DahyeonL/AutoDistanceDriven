package co.kr.daou.autodistancedriven.feature.driving.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.databinding.ItemDrivingListDetailBinding
import co.kr.daou.autodistancedriven.feature.MainActivity
import co.kr.daou.autodistancedriven.feature.driving.DrivingViewModel
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import co.kr.daou.autodistancedriven.util.ConvertUtil

class DrivingListDetailRecyclerviewAdapter(list : List<DrivingRecordDto>, drivingViewModel : DrivingViewModel) : RecyclerView.Adapter<DrivingListDetailRecyclerviewAdapter.DrivingDetailRecyclerviewHolder>() {
    private val recordListByDate: List<DrivingRecordDto> = list
    private lateinit var context : Context
    private val convertUtil = ConvertUtil()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrivingDetailRecyclerviewHolder {
        context = parent.context
        return DrivingDetailRecyclerviewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_driving_list_detail,parent,false))
    }
    override fun onBindViewHolder(holder: DrivingDetailRecyclerviewHolder, position: Int) {
        //코드 개발 이슈 #8-1
        holder.binding.apply {
            textviewItemDrivingMeter.text = convertUtil.convertDoubleToString(recordListByDate[position].drivingDistance)
            textviewItemDrivingTime.text = convertUtil.convertTimeToString(recordListByDate[position].drivingTime)
            textviewItemStartTime.text = convertUtil.convertStartTimeToString(recordListByDate[position].startTime)
            textviewItemFinishTime.text = convertUtil.convertFinishTimeToString(recordListByDate[position].finishTime)
//            holder.binding.drivingRecord = recordListByDate[position]
            executePendingBindings()
        }
        holder.itemView.setOnClickListener {
            val mainActivity = context as MainActivity
            recordListByDate[position].drivingRecordId?.let { it ->
                mainActivity.openDrivingDetailFragment(it)
            }
        }
    }
    override fun getItemCount(): Int {
        return recordListByDate.size
    }
    class DrivingDetailRecyclerviewHolder(val binding : ItemDrivingListDetailBinding) : RecyclerView.ViewHolder(binding.root)
}