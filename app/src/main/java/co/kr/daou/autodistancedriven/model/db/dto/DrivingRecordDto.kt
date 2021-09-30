package co.kr.daou.autodistancedriven.model.db.dto
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

//코드 개발 이슈 #2
//기술 스택 #4-1 ROOM
@Entity(tableName = "driving_record")
data class DrivingRecordDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "driving_record_id") val drivingRecordId: Int?,
    @ColumnInfo(name = "start_time") val startTime: Date?,
    @ColumnInfo(name = "finish_time") val finishTime: Date?,
    @ColumnInfo(name = "driving_time") val drivingTime: String?,
    @ColumnInfo(name = "driving_distance") val drivingDistance: Double?, //M 단위 소숫점 1자리까지 표기
    @ColumnInfo(name = "start_point") val startPoint: String?,
    @ColumnInfo(name = "finish_point") val finishPoint: String?
)
{
    @Ignore
    constructor() : this(null, Date(), Date(),"",0.0,"","")
}
