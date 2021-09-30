package co.kr.daou.autodistancedriven.feature.driving

import androidx.lifecycle.*
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.model.db.repository.DrivingRecordRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//코드 개발 이슈 #8-2
class DrivingViewModel : ViewModel(), KoinComponent{
    private val repository : DrivingRecordRepository by inject()
    val allRecords = repository.allDrivingRecords.asLiveData()

    fun getRecordById(recordId : Int): LiveData<DrivingRecordDto> {
        return repository.findDrivingRecordByDrivingRecordId(recordId).asLiveData()
    }
    fun getRecordGPSsById(recordId : Int): LiveData<List<DrivingRecordGPSDto>> {
        return repository.findDrivingRecordGPSsByDrivingRecordId(recordId).asLiveData()
    }

}