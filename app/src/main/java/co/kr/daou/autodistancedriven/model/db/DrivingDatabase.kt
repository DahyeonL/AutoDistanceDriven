package co.kr.daou.autodistancedriven.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordGPSDto
import co.kr.daou.autodistancedriven.model.db.dto.DrivingRecordDto

@Database(entities = [DrivingRecordDto::class, DrivingRecordGPSDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class DrivingDatabase : RoomDatabase() {
    abstract fun drivingRecordDao() : DrivingRecordDao
}