package co.kr.daou.autodistancedriven.model.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "driving_record_gps")
data class DrivingRecordGPSDto(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "driving_record_id") val drivingRecordId: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)