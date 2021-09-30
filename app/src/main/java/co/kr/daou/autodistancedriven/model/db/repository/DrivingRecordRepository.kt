package co.kr.daou.autodistancedriven.model.db.repository

import co.kr.daou.autodistancedriven.model.db.DrivingRecordDao
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import kotlinx.coroutines.flow.Flow
import java.util.*


class DrivingRecordRepository(private val dao: DrivingRecordDao) {
    //기술 스택 #1-3 Koin
    //주행기록
    suspend fun insertRecords(drivingRecord : DrivingRecordDto) {
        dao.insertRecords(drivingRecord)
    }
    suspend fun updateRecords(drivingRecord : DrivingRecordDto) {
        dao.updateRecords(drivingRecord)
    }
    val allDrivingRecords : Flow<List<DrivingRecordDto>> = dao.loadDrivingRecordsAll()
    fun findDrivingRecordByDrivingRecordId(drivingRecordId : Int):Flow<DrivingRecordDto>{
            return dao.findDrivingRecordByDrivingRecordId(drivingRecordId)
    }
    val loadDrivingAllDay :  Flow<List<Date>> = dao.loadDrivingAllDay()
    val findDrivingRecordIdByLast : Flow<Int> = dao.findDrivingRecordIdByLast()
//    val findDrivingRecordIdByLast = 0
    //주행기록 상세
    suspend fun insertRecordGPSs(drivingRecordGPS : DrivingRecordGPSDto){
        dao.insertRecordGPSs(drivingRecordGPS)
    }
    fun findDrivingRecordGPSsByDrivingRecordId(drivingRecordId : Int) : Flow<List<DrivingRecordGPSDto>>{
        return dao.findDrivingRecordDetailsByDrivingRecordId(drivingRecordId)
    }
}