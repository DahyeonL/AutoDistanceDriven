package co.kr.daou.autodistancedriven.model.db

import androidx.room.*
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto
import java.util.*
import kotlinx.coroutines.flow.Flow

//코드 개발 이슈 #3
@Dao
interface DrivingRecordDao {
    //주행기록
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(vararg drivingRecord : DrivingRecordDto)

    @Update
    suspend fun updateRecords(vararg drivingRecord : DrivingRecordDto)

    @Query(
        "SELECT * " +
                "FROM driving_record"
    )
    fun loadDrivingRecordsAll(): Flow<List<DrivingRecordDto>>

    @Query(
        "SELECT * " +
                "FROM driving_record " +
                "WHERE driving_record_id = :drivingRecordId"
    )
    fun findDrivingRecordByDrivingRecordId(drivingRecordId : Int) : Flow<DrivingRecordDto>

    @Query(
        "SELECT strftime('%Y-%m-%d', start_time) AS start_time "+
                "FROM driving_record"
    )
    fun loadDrivingAllDay(): Flow<List<Date>>

    @Query(
        "SELECT driving_record_id " +
                "FROM driving_record " +
                "ORDER BY driving_record_id " +
                "DESC LIMIT 1"
    )
    fun findDrivingRecordIdByLast() : Flow<Int>

    //주행기록 상세
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordGPSs(vararg drivingRecordDetail : DrivingRecordGPSDto)

    @Query(
        "SELECT * " +
                "FROM driving_record_gps " +
                "WHERE driving_record_id = :drivingRecordId"
    )
    fun findDrivingRecordDetailsByDrivingRecordId(drivingRecordId : Int) : Flow<List<DrivingRecordGPSDto>>
}